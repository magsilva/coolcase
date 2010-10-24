package coolcase.casetool.tool;

import java.awt.geom.Point2D;

import coolcase.casetool.figure.AssociationFigure;

import coolcase.veryhot.DrawingPanel;
import coolcase.veryhot.Figure;
import coolcase.veryhot.LineFigure;
import coolcase.veryhot.Locator;
import coolcase.veryhot.tool.LineTool;

public class AssociationTool extends LineTool {

	public AssociationTool(DrawingPanel canvas) {
		super(canvas);
	}

	protected Figure newFigure(Point2D p, Figure f) {
		return null;
	}

	/**
	 * Creates the line. In fact, not a line already, just the starting point.
	 * As we click, we create new points and than we will have a line.
	 */
	protected LineFigure createLine(Locator begin) {
		return new AssociationFigure(begin);
	}

	/**
	 * Method called when the line is created and the first point will be added.
	 * This way we can check for errors easier (throwing RuntimeExceptions, for
	 * example).
	 */
	protected Locator getBeginLocator(Point2D p) {
		Figure f = canvas.getFigureAt(p);
		try {
			Point2D connectingPoint = f.getConnectionPoint();
			return new Locator(connectingPoint, f);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Method called when the last point will be added. This way we can check
	 * for errors easier (throwing RuntimeExceptions, for example).
	 */
	protected Locator getEndLocator(Point2D p) {
		Figure f = canvas.getFigureAt(p);
		try {
			Point2D connectingPoint = f.getConnectionPoint();
			return new Locator(connectingPoint, f);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Method called when the line is created and points are added (but not
	 * first neither last line's point). This way we can check for errors easier
	 * (throwing RuntimeExceptions, for example).
	 */
	protected Locator getBetweenLocator(Point2D p) {
		return new Locator(p, null);
	}
}
