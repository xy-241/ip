import java.util.Scanner;

public class Chin {
    private static final String LINE = "____________________________________________________________";
    private static final Task[] tasks = new Task[100];
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
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int idx = Integer.parseInt(input.substring(5)) - 1;
                tasks[idx].mark();
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[idx]);
            } else if (input.startsWith("unmark ")) {
                int idx = Integer.parseInt(input.substring(7)) - 1;
                tasks[idx].unmark();
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[idx]);
            } else {
                Task t = parseNewTask(input);
                tasks[count++] = t;
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + t);
                System.out.println(" Now you have " + count + " tasks in the list.");
            }
            System.out.println(LINE);
        }

        System.out.println(LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    private static Task parseNewTask(String input) {
        if (input.startsWith("todo ")) {
            return new Todo(input.substring(5));
        } else if (input.startsWith("deadline ")) {
            String body = input.substring(9);
            int i = body.indexOf(" /by ");
            return new Deadline(body.substring(0, i), body.substring(i + 5));
        } else if (input.startsWith("event ")) {
            String body = input.substring(6);
            int f = body.indexOf(" /from ");
            int t = body.indexOf(" /to ");
            return new Event(body.substring(0, f), body.substring(f + 7, t), body.substring(t + 5));
        }
        return new Task(input);
    }
}
