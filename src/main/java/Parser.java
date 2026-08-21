public class Parser {
    public static Task parseNewTask(Command cmd, String input) throws ChinException {
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

    public static int parseIndex(String s, int size) throws ChinException {
        try {
            int idx = Integer.parseInt(s.trim()) - 1;
            if (idx < 0 || idx >= size) {
                throw new ChinException("OOPS!!! That task number is out of range.");
            }
            return idx;
        } catch (NumberFormatException e) {
            throw new ChinException("OOPS!!! Task number must be an integer.");
        }
    }
}
