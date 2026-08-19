import java.util.ArrayList;
import java.util.Scanner;

public class Chin {
    private static final String LINE = "____________________________________________________________";
    private static final ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Chin");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (Command.fromInput(input) == Command.BYE) {
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
        Command cmd = Command.fromInput(input);
        switch (cmd) {
        case LIST:
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
            return;
        case MARK: {
            int idx = parseIndex(input.substring(5));
            tasks.get(idx).mark();
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks.get(idx));
            return;
        }
        case UNMARK: {
            int idx = parseIndex(input.substring(7));
            tasks.get(idx).unmark();
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks.get(idx));
            return;
        }
        case DELETE: {
            int idx = parseIndex(input.substring(7));
            Task removed = tasks.remove(idx);
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removed);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            return;
        }
        case TODO:
        case DEADLINE:
        case EVENT: {
            Task t = parseNewTask(cmd, input);
            tasks.add(t);
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + t);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            return;
        }
        default:
            throw new ChinException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    private static int parseIndex(String s) throws ChinException {
        try {
            int idx = Integer.parseInt(s.trim()) - 1;
            if (idx < 0 || idx >= tasks.size()) {
                throw new ChinException("OOPS!!! That task number is out of range.");
            }
            return idx;
        } catch (NumberFormatException e) {
            throw new ChinException("OOPS!!! Task number must be an integer.");
        }
    }

    private static Task parseNewTask(Command cmd, String input) throws ChinException {
        switch (cmd) {
        case TODO: {
            String desc = input.length() > 4 ? input.substring(5).trim() : "";
            if (desc.isEmpty()) {
                throw new ChinException("OOPS!!! The description of a todo cannot be empty.");
            }
            return new Todo(desc);
        }
        case DEADLINE: {
            String body = input.length() > 8 ? input.substring(9) : "";
            int i = body.indexOf(" /by ");
            if (i < 0 || body.substring(0, i).trim().isEmpty() || body.substring(i + 5).trim().isEmpty()) {
                throw new ChinException("OOPS!!! A deadline needs a description and ' /by <when>'.");
            }
            return new Deadline(body.substring(0, i).trim(), body.substring(i + 5).trim());
        }
        case EVENT: {
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
        default:
            throw new ChinException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
