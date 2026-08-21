import java.util.Scanner;

public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner sc = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Chin");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
    }

    public void showGoodbye() {
        System.out.println(LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public String readCommand() {
        return sc.hasNextLine() ? sc.nextLine() : "bye";
    }

    public void show(String msg) {
        System.out.println(" " + msg);
    }

    public void showIndented(String msg) {
        System.out.println("   " + msg);
    }

    public void showError(String msg) {
        System.out.println(" " + msg);
    }
}
