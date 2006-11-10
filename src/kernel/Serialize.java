package coolcase.kernel;

import java.io.*;
import java.lang.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.sql.*;

public class Serialize {
	
	// This is the connection that the instance refers to
	protected org.postgresql.Connection conn;

	// This is the table name
	protected String tableName;

	// This is the class name
	protected String className;

	// This is the Class for this serialized object
	protected Class ourClass;

	/*
	 * This creates an instance that can be used to serialize or deserialize
	 * a Java object from a PostgreSQL table.
	 */
	public Serialize( org.postgresql.Connection c, String type ) throws SQLException {
		try {
			conn = c;
			DriverManager.println( "Serialize: initializing instance for type: " + type );
			tableName = toPostgreSQL( type );
			className = type;
			ourClass = Class.forName( className );
		} catch ( ClassNotFoundException cnfe ) {
			DriverManager.println("Serialize: " + className + " java class not found");
			throw new SQLException( "Serialize: " + className + " java class not found" );
		}

		// Second check, the type must be a table
		boolean status = false;
		ResultSet rs = conn.ExecSQL( "select typname from pg_type,pg_class where typname=relname and typname='" + tableName + "'" );
		if ( rs != null ) {
			if ( rs.next() ) {
				status = true;
				DriverManager.println("Serialize: " + tableName + " table found");
			}
			rs.close();
		}
		// This should never occur, as org.postgresql has it's own internal checks
		if ( !status ) {
			DriverManager.println( "Serialize: " + tableName + " table not found" );
			throw new SQLException( "Serialize: " + tableName + " table not found" );
		}
		// Finally cache the fields within the table
	}

	/*
	 * Constructor when Object is passed in
	 */
	public Serialize( org.postgresql.Connection c, Object o ) throws SQLException {
		this(c, o.getClass().getName());
	}

	/*
	 * Constructor when Class is passed in
	 */
	public Serialize(org.postgresql.Connection c, Class cls) throws SQLException {
		this( c, cls.getName() );
	}

	/*
	 * This fetches an object from a table, given it's OID
	 * @param oid The oid of the object
	 * @return Object relating to oid
	 * @exception SQLException on error
	 */
	public Object fetch(int oid) throws SQLException {
		try {
			DriverManager.println("Serialize.fetch: " + "attempting to instantiate object of type: " + ourClass.getName() );
			Object obj = ourClass.newInstance();
			DriverManager.println("Serialize.fetch: " + "instantiated object of type: " + ourClass.getName() );

			// NB: we use java.lang.reflect here to prevent confusion with
			// the org.postgresql.Field

			// used getFields to get only public fields. We have no way to set values
			// for other declarations. Maybe look for setFieldName() methods?
			java.lang.reflect.Field f[] = ourClass.getFields();
			boolean hasOID = false;
			int oidFIELD = -1;

			StringBuffer sb = new StringBuffer("select");
			char sep = ' ';
			// build a select for the fields. Look for the oid field to use in the where
			for (int i = 0;i < f.length;i++) {
				String n = f[i].getName();
				if (n.equals("oid")) {
					hasOID = true;
					oidFIELD = i;
				}
				sb.append(sep);
				sb.append(n);
				sep = ',';
			}
			sb.append(" from ");
			sb.append(tableName);
			sb.append(" where oid=");
			sb.append(oid);

			DriverManager.println("Serialize.fetch: " + sb.toString());
			ResultSet rs = conn.ExecSQL(sb.toString());

			if (rs != null) {
				if (rs.next()) {
					for (int i = 0;i < f.length;i++) {
						if ( !Modifier.isFinal(f[i].getModifiers()) ) {
							if ( f[i].getType().getName().equals("short") )
								f[i].setShort(obj, rs.getShort(i + 1));
							else if ( f[i].getType().getName().equals("char") )
								f[i].setChar(obj, rs.getString(i + 1).toCharArray()[0]);
							else if ( f[i].getType().getName().equals("byte"))
								f[i].setByte(obj, rs.getByte(i + 1));
							else if ( f[i].getType().getName().equals("boolean") ) {
								// booleans come out of pgsql as a t or an f
								if ( rs.getString(i + 1).equals("t") )
									f[i].setBoolean(obj, true);
								else
									f[i].setBoolean(obj, false);
							}
							else
								f[i].set(obj, rs.getObject(i + 1));
						}
					}
				}
				rs.close();
			} else
				throw new SQLException("postgresql.unexpected");
			return obj;
		} catch (IllegalAccessException iae) {
			throw new SQLException(iae.toString());
		} catch (InstantiationException ie) {
			throw new SQLException(ie.toString());
		}
	}

	/*
	* This stores an object into a table, returning it's OID.<p>
	 *
	 * If the object has an int called OID, and it is > 0, then
	 * that value is used for the OID, and the table will be updated.
	 * If the value of OID is 0, then a new row will be created, and the
	 * value of OID will be set in the object. This enables an object's
	 * value in the database to be updateable.
	 *
	 * If the object has no int called OID, then the object is stored. However
	 * if the object is later retrieved, amended and stored again, it's new
	 * state will be appended to the table, and will not overwrite the old
	 * entries.
	 *
	 * @param o Object to store (must implement Serializable)
	 * @return oid of stored object
	 * @exception SQLException on error
	 */
	public long storeObject(Object o) throws SQLException {
		try {
			// don't save private fields since we would not be able to fetch them
			java.lang.reflect.Field f[] = ourClass.getFields();

			boolean hasOID = false;
			int oidFIELD = -1;
			boolean update = false;

			// Find out if we have an oid value
			for (int i = 0;i < f.length;i++) {
				String n = f[i].getName();
				if ( n.equals( "oid" ) ) {
					hasOID = true;
					oidFIELD = i;
					// Do update if oid != 0
					update = f[i].getInt(o) > 0;
				}
			}

			StringBuffer sb = new StringBuffer(update ? "update " + tableName + " set" : "insert into " + tableName + " ");
			char sep = update ? ' ' : '(';
			for (int i = 0;i < f.length;i++) {
				String n = f[i].getName();
				// oid cannot be updated!
				if ( n.equals("oid") )
					continue;
				sb.append(sep);
				sep = ',';
				sb.append(n);
				if (update) {
					sb.append('=');
					// handle unset values
					if (f[i].get(o) == null)
						sb.append("null");
					else if ( f[i].getType().getName().equals("java.lang.String") || f[i].getType().getName().equals("char") ) {
						sb.append('\'');
						// don't allow single qoutes or newlines in the string
						sb.append(fixString(f[i].get(o).toString()));
						sb.append('\'');
					} else
						sb.append(f[i].get(o).toString());
				}
			}

			if (update)
				sb.append(" where oid = " + f[oidFIELD].getInt(o) );

			if (!update) {
				sb.append(") values ");
				sep = '(';
				for (int i = 0;i < f.length;i++) {
					String n = f[i].getName();
					// oid cannot be set!
					if ( n.equals("oid") )
						continue;
					sb.append(sep);
					sep = ',';
					// handle unset values
					if (f[i].get(o) == null)
						sb.append("null");
					else if ( f[i].getType().getName().equals("java.lang.String") || f[i].getType().getName().equals("char")) {
						sb.append('\'');
						// don't allow single quotes or newlines in the string
						sb.append(fixString(f[i].get(o).toString()));
						sb.append('\'');
					} else
						sb.append(f[i].get(o).toString());
				}
				sb.append(')');
			}

			DriverManager.println("Serialize.store: " + sb.toString() );
			org.postgresql.ResultSet rs = (org.postgresql.ResultSet) conn.ExecSQL(sb.toString());

			// fetch the OID for returning
			if (update) {
				// object has oid already, so return it
				if (rs != null)
					rs.close();
				return f[oidFIELD].getInt(o);
			} else {
				// new record inserted has new oid; rs should be not null
				long newOID = ((org.postgresql.ResultSet)rs).getLastOID();
				rs.close();
				// update the java object's oid field if it has the oid field
				if (hasOID)
					f[oidFIELD].setLong(o, newOID);
				// new object stored, return newly inserted oid
				return newOID;
			}

		} catch (IllegalAccessException iae) {
			throw new SQLException( iae.toString() );
		}
	}

	/*
	* Escape literal single quote and backslashes embedded in strings/chars.
	* Otherwise, postgres will bomb on the single quote and remove the
	* the backslashes.
	*/
	private String fixString(String s)
	{
		int idx = -1;

		// handle null
		if (s == null)
			return "";

		// if the string has single quotes in it escape them as ''
		if ((idx = s.indexOf("'")) > -1)
		{
			StringBuffer buf = new StringBuffer();
			StringTokenizer tok = new StringTokenizer(s, "'");
			// handle quote as 1St charater
			if (idx > 0)
				buf.append(tok.nextToken());

			while (tok.hasMoreTokens())
				buf.append("''").append(tok.nextToken());

			s = buf.toString();
		}

		// if the string has backslashes in it escape them them as \\
		if ((idx = s.indexOf("\\")) > -1) {
			StringBuffer buf = new StringBuffer();
			StringTokenizer tok = new StringTokenizer(s, "\\");
			if (idx > 0)
				buf.append(tok.nextToken());

			while (tok.hasMoreTokens())
				buf.append("\\\\").append(tok.nextToken());

			s = buf.toString();
		}

		return s;
	}

	/*
	 * This method is not used by the driver, but it creates a table, given
	 * a Serializable Java Object. It should be used before serializing any
	 * objects.
	 * @param c Connection to database
	 * @param o Object to base table on
	 * @exception SQLException on error
	 */
	public static void create(org.postgresql.Connection con, Object o) throws SQLException {
		create( con, o.getClass() );
	}

	/*
	 * This method is not used by the driver, but it creates a table, given
	 * a Serializable Java Object. It should be used before serializing any
	 * objects.
	 * @param c Connection to database
	 * @param o Class to base table on
	 * @exception SQLException on error
	 */
	public static void create(org.postgresql.Connection con, Class c) throws SQLException
	{
		if ( c.isInterface() ) {
			throw new SQLException( "postgresql.serial.interface" );
		}

		// See if the table exists
		String tableName = toPostgreSQL( c.getName() );

		ResultSet rs = con.ExecSQL("select relname from pg_class where relname = '" + tableName + "'");
		if ( rs.next() ) {
			DriverManager.println("Serialize.create: table " + tableName + " exists, skipping");
			rs.close();
			return;
		}

		// else table not found, so create it
		DriverManager.println("Serialize.create: table " + tableName + " not found, creating" );
		// No entries returned, so the table doesn't exist

		StringBuffer sb = new StringBuffer("create table ");
		sb.append(tableName);
		char sep = '(';

		// java.lang.reflect.Field[] fields = c.getDeclaredFields();
		// Only store public fields, another limitation!
		java.lang.reflect.Field[] fields = c.getFields();
		for ( int i = 0;i < fields.length;i++ ) {
			Class type = fields[i].getType();
			// oid is a special field
			if ( !fields[i].getName().equals("oid") ) {
				sb.append(sep);
				sb.append(fields[i].getName());
				sb.append(' ');
				sep = ',';

				if ( type.isArray() ) {
					// array handling
				} else {
					// convert the java type to org.postgresql, recursing if a class is found
					String n = type.getName();
					int j = 0;
					for (;j < tp.length && !tp[j][0].equals(n); j++);
					if (j < tp.length) {
						sb.append(tp[j][1]);
					} else {
						create(con, type);
						sb.append(toPostgreSQL(n));
					}
				}
			}
		}
		sb.append(")");

		// Now create the table
		DriverManager.println("Serialize.create: " + sb );
		con.ExecSQL(sb.toString());
	}

	// This is used to translate between Java primitives and PostgreSQL types.
	private static final String tp[][] = {
											 {"boolean", "bool"},
											 {"double", "float8"},
											 {"float", "float4"},
											 {"int", "int4"},
											 {"long", "int8"},
											 {"short", "int2"},
											 {"java.lang.String", "text"},
											 {"java.lang.Integer", "int4"},
											 {"java.lang.Float", "float4"},
											 {"java.lang.Double", "float8"},
											 {"java.lang.Short", "int2"},
											 {"char", "char"},
											 {"byte", "int2"}
										 };

	/*
	* This converts a Java Class name to a org.postgresql table, by replacing . with _<p>
	*
	* Because of this, a Class name may not have _ in the name. Another limitation is that
	* the entire class name (including packages) cannot be longer than 32 characters (a limit
	* forced by PostgreSQL).
	*
	* @param name Class name
	* @return PostgreSQL table name
	* @exception SQLException on error
	*/
	public static String toPostgreSQL(String name) throws SQLException
	{
		name = name.toLowerCase();

		if ( name.indexOf("_") > -1 ) {
			throw new SQLException( "postgresql.serial.underscore" );
		}

		// Postgres table names can only be 32 character long. Reserve 1 char, so allow only
		// up to 31 chars. If the full class name with package is too long then just use the
		// class name. If the class name is too long throw an exception.
		if ( name.length() > 31 ) {
			name = name.substring( name.lastIndexOf( "." ) + 1 );
			if ( name.length() > 31 ) {
				throw new SQLException( "postgresql.serial.namelength " + name +  new Integer(name.length()));
			}
		}
		return name.replace('.', '_');
	}


	/*
	* This converts a org.postgresql table to a Java Class name, by replacing _ with .<p>
	*
	* @param name PostgreSQL table name
	* @return Class name
	*/
	public static String toClassName( String name ) {
		name = name.toLowerCase();
		return name.replace('_', '.');
	}
}
