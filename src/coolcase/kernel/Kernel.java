package coolcase.kernel;

import java.io.*;
import java.util.*;

/**
 * The microkernel implements the minimum support needed for a CORBA application. It does
 * not provides the facilities needed to exchange messages between objects, this CORBA does very
 * well. However, there isn't a portable way to save the objects states in CORBA (there is the
 * implementation repository and persistence service, but that isn't available at all the ORBs, actually
 * almost no ORB has a persistence service, who can tells a good one either).
 * Another thing done is to make it easier to find objects through the Name Service. It's known that
 * CORBA has good services for that (Naming and Trading service), but having a few functions to make
 * it easier to use this services is an excellent idea.
 * <BR>
 * Although we are using just one memory and communication manager, there is no restriction into 
 * the microkernel design (restriction that cannot be override).
 */
public interface Kernel extends ObjectManager, ServiceManager {


}
