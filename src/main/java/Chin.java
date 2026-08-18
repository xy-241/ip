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
            try {
                handle(input);
            } catch (ChinException e) {
                System.out.println(" " + e.getMessage());
            }
            System.out.println(LINE);
        }

        System.out.println(LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    private static void handle(String input) throws ChinException {
        if (input.equals("list")) {
            for (int i = 0; i < count; i++) {
                System.out.println(" " + (i + 1) + "." + tasks[i]);
            }
            return;
        }
        if (input.startsWith("mark ")) {
            int idx = parseIndex(input.substring(5));
            tasks[idx].mark();
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks[idx]);
            return;
        }
        if (input.startsWith("unmark ")) {
            int idx = parseIndex(input.substring(7));
            tasks[idx].unmark();
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks[idx]);
            return;
        }
        Task t = parseNewTask(input);
        tasks[count++] = t;
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + t);
        System.out.println(" Now you have " + count + " tasks in the list.");
    }

    private static int parseIndex(String s) throws ChinException {
        try {
            int idx = Integer.parseInt(s.trim()) - 1;
            if (idx < 0 || idx >= count) {
                throw new ChinException("OOPS!!! That task number is out of range.");
            }
            return idx;
        } catch (NumberFormatException e) {
            throw new ChinException("OOPS!!! Task number must be an integer.");
        }
    }

    private static Task parseNewTask(String input) throws ChinException {
        if (input.equals("todo") || input.startsWith("todo ")) {
            String desc = input.length() > 4 ? input.substring(5).trim() : "";
            if (desc.isEmpty()) {
                throw new ChinException("OOPS!!! The description of a todo cannot be empty.");
            }
            return new Todo(desc);
        }
        if (input.equals("deadline") || input.startsWith("deadline ")) {
            String body = input.length() > 8 ? input.substring(9) : "";
            int i = body.indexOf(" /by ");
            if (i < 0 || body.substring(0, i).trim().isEmpty() || body.substring(i + 5).trim().isEmpty()) {
                throw new ChinException("OOPS!!! A deadline needs a description and ' /by <when>'.");
            }
            return new Deadline(body.substring(0, i).trim(), body.substring(i + 5).trim());
        }
        if (input.equals("event") || input.startsWith("event ")) {
            String body = input.length() > 5 ? input.substring(6) : "";
            int f = body.indexOf(" /from ");
            int t = body.indexOf(" /to ");
            if (f < 0 || t < 0 || t < f
                    || body.substring(0, f).trim().isEmpty()
                    || body.substring(f + 7, t).trim().isEmpty()
                    || body.substring(t + 5).trim().isEmpty()) {
                throw new ChinException("OOPS!!! An event needs a description, ' /from <start>' and ' /to <end>'.");
            }
            return new Event(body.substring(0, f).trim(),
                    body.substring(f + 7, t).trim(),
                    body.substring(t + 5).trim());
        }
        throw new ChinException("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}
