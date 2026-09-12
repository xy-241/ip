package chin;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.stream.IntStream;

import chin.task.Task;
import chin.util.ChinException;
import chin.util.Command;
import chin.util.Parser;
import chin.util.Storage;
import chin.util.TaskList;
import chin.util.Ui;

/**
 * Entry point of the Chin chatbot. Wires together {@link Ui}, {@link Storage},
 * and {@link TaskList}, then runs the command loop until the user exits.
 */
public class Chin {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Creates a new Chin instance backed by the given data file.
     *
     * @param dataPath path to the persistent task file
     */
    public Chin(Path dataPath) {
        this.ui = new Ui();
        this.storage = new Storage(dataPath);
        this.tasks = new TaskList(storage.load());
    }

    /**
     * Runs the interactive read-eval loop until the user issues {@code bye}.
     */
    public void run() {
        ui.showWelcome();
        while (true) {
            String userInput = ui.readCommand();
            Command cmd = Command.fromInput(userInput);
            if (cmd == Command.BYE) {
                break;
            }
            ui.showLine();
            try {
                executeCommand(cmd, userInput);
                storage.save(tasks.asList());
            } catch (ChinException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
        ui.showGoodbye();
    }

    /**
     * Processes a single input line and returns the response as a string,
     * without printing to stdout. Used by the JavaFX GUI in {@link Main}.
     */
    public String getResponse(String userInput) {
        Command cmd = Command.fromInput(userInput);
        if (cmd == Command.BYE) {
            return "Catch you later. Don't forget the deadlines.";
        }
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(buf));
        try {
            try {
                executeCommand(cmd, userInput);
                storage.save(tasks.asList());
            } catch (ChinException e) {
                System.out.println(" " + e.getMessage());
            }
        } finally {
            System.setOut(originalOut);
        }
        return buf.toString().stripTrailing();
    }

    /** Returns the argument portion of {@code input} after the given command prefix, safely. */
    private static String stripCommand(String input, String prefix) {
        int cut = prefix.length();
        return input.length() > cut ? input.substring(cut + 1).trim() : "";
    }

    private void executeCommand(Command cmd, String userInput) throws ChinException {
        if (cmd == Command.UNKNOWN && userInput.trim().isEmpty()) {
            throw new ChinException("OOPS!!! Empty input. Try `list`, `help`, or `todo <task>`.");
        }
        switch (cmd) {
        case LIST:
            IntStream.range(0, tasks.size())
                    .forEach(i -> ui.show((i + 1) + "." + tasks.get(i)));
            return;
        case MARK: {
            int idx = Parser.parseIndex(stripCommand(userInput, "mark"), tasks.size());
            tasks.get(idx).mark();
            ui.show("Boom. Marked as done:");
            ui.showIndented(tasks.get(idx).toString());
            return;
        }
        case UNMARK: {
            int idx = Parser.parseIndex(stripCommand(userInput, "unmark"), tasks.size());
            tasks.get(idx).unmark();
            ui.show("Alright, back on your plate:");
            ui.showIndented(tasks.get(idx).toString());
            return;
        }
        case DELETE: {
            int idx = Parser.parseIndex(stripCommand(userInput, "delete"), tasks.size());
            Task removed = tasks.remove(idx);
            ui.show("Poof. Gone:");
            ui.showIndented(removed.toString());
            ui.show("You now have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " left.");
            return;
        }
        case SORT: {
            tasks.sortByDeadline();
            ui.show("Sorted tasks by deadline (earliest first, non-deadlines last).");
            for (int i = 0; i < tasks.size(); i++) {
                ui.show((i + 1) + "." + tasks.get(i));
            }
            return;
        }
        case FIND: {
            String keyword = stripCommand(userInput, "find");
            if (keyword.isEmpty()) {
                throw new ChinException("OOPS!!! Please give a keyword to find, e.g. `find book`.");
            }
            java.util.List<Task> matches = tasks.find(keyword);
            if (matches.isEmpty()) {
                ui.show("No matching tasks found.");
            } else {
                ui.show("Here are the matching tasks in your list:");
                IntStream.range(0, matches.size())
                        .forEach(i -> ui.show((i + 1) + "." + matches.get(i)));
            }
            return;
        }
        case TODO:
            // Fallthrough
        case DEADLINE:
            // Fallthrough
        case EVENT: {
            Task task = Parser.parseNewTask(cmd, userInput);
            tasks.add(task);
            ui.show("Locked in. Added to the pile:");
            ui.showIndented(task.toString());
            ui.show("You now have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " on the list.");
            return;
        }
        default:
            throw new ChinException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        new Chin(Path.of("data", "chin.txt")).run();
    }
}
