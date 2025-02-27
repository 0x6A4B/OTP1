package util;

/**
 * This class provides a mechanism for logging and tracing system behavior.
 *
 * @author mestari opettajat / our sensei
 */
public class Trace {

	/**
	 * Enum representing the trace levels: INFO, WARNING (WAR), ERROR (ERR).
	 */
	public enum Level{INFO, WAR, ERR}

	/**
	 * The current trace level.
	 */
	private static Level traceLevel;

	/**
	 * Sets the current trace level to the specified value.
	 *
	 * @param lvl the new trace level
	 */
	public static void setTraceLevel(Level lvl){
		traceLevel = lvl;
	}

	/**
	 * Prints a message to the console if the trace level is at or above the current trace level.
	 *
	 * @param lvl the trace level of the message (INFO, WAR, ERR)
	 * @param txt the message to print
	 */
	public static void out(Level lvl, String txt){
		if (lvl.ordinal() >= traceLevel.ordinal()){
			System.out.println(txt);
		}
	}

}