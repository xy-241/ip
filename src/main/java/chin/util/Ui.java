package chin.util;

import java.util.Scanner;

/**
 * Handles all console I/O for the text-mode Chin loop. Reads commands via
 * a {@link Scanner} and prints framed messages to {@code stdout}.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    /** Prints the initial greeting frame with Chin's signature voice. */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(" Yo. Chin here.");
        System.out.println(" Feed me your to-dos, deadlines, or events.");
        System.out.println(" I'll keep them tidy while you go grab a coffee.");
        System.out.println(LINE);
    }

    /** Prints the farewell frame with Chin's send-off. */
    public void showGoodbye() {
        System.out.println(LINE);
        System.out.println(" Catch you later. Don't forget the deadlines.");
        System.out.println(LINE);
    }

    /** Prints the horizontal separator line. */
    public void showLine() {
        System.out.println(LINE);
    }

    /** Reads the next command line, returning {@code bye} on EOF. */
    public String readCommand() {
        return scanner.hasNextLine() ? scanner.nextLine() : "bye";
    }

    /** Prints a top-level message with one space of left padding. */
    public void show(String msg) {
        System.out.println(" " + msg);
    }

    /** Prints a nested/indented message. */
    public void showIndented(String msg) {
        System.out.println("   " + msg);
    }

    /** Prints an error message using the same style as {@link #show(String)}. */
    public void showError(String msg) {
        System.out.println(" " + msg);
    }
}
