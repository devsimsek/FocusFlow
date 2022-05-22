public class Utils {

    public static boolean debug = true;

    public static void log(String message) {
        if (debug) {
            System.out.println("FocusFlow INFO: " + message);
        }
    }

    public static void log(String message, String flag) {
        if (debug) {
            System.out.println("FocusFlow " + flag + ": " + message);
        }
    }

    public static void log(String message, boolean newLine) {
        if (debug) {
            if (newLine)
                System.out.println("FocusFlow INFO: " + message);
            else
                System.out.print("FocusFlow INFO: " + message);
        }
    }

    public static void log(String message, String flag, boolean newLine) {
        if (debug) {
            if (newLine)
                System.out.println("FocusFlow " + flag + ": " + message);
            else
                System.out.print("FocusFlow " + flag + ": " + message);
        }
    }

}
