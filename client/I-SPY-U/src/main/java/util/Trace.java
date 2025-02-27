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
	public enum Level{DEV, INFO, WAR, ERR}

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
		String color = "";
		color = switch (lvl){
			case Level.ERR:
				yield AnsiColor.RED.getColor();
			case Level.WAR:
				yield AnsiColor.BLUE.getColor();
			case Level.DEV:
				yield AnsiColor.GREEN.getColor();
			default:
				yield "";
		};

		if (lvl.ordinal() >= traceLevel.ordinal()){
			System.out.println(color + txt + AnsiColor.RESET);
		}
	}

}