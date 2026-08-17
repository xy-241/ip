import java.util.Scanner;

public class Chin {
    private static final String LINE = "____________________________________________________________";
    private static final String[] tasks = new String[100];
    private static int count = 0;

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Chin");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                break;
            }
            System.out.println(LINE);
            if (input.equals("list")) {
                for (int i = 0; i < count; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[count++] = input;
                System.out.println(" added: " + input);
            }
            System.out.println(LINE);
        }

        System.out.println(LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }
}
