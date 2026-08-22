package chin;

import java.nio.file.Path;

import chin.task.Task;
import chin.util.ChinException;
import chin.util.Command;
import chin.util.Parser;
import chin.util.Storage;
import chin.util.TaskList;
import chin.util.Ui;

public class Chin {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    public Chin(Path dataPath) {
        this.ui = new Ui();
        this.storage = new Storage(dataPath);
        this.tasks = new TaskList(storage.load());
    }

    public void run() {
        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();
            Command cmd = Command.fromInput(input);
            if (cmd == Command.BYE) {
                break;
            }
            ui.showLine();
            try {
                handle(cmd, input);
                storage.save(tasks.asList());
            } catch (ChinException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
        ui.showGoodbye();
    }

    private void handle(Command cmd, String input) throws ChinException {
        switch (cmd) {
        case LIST:
            for (int i = 0; i < tasks.size(); i++) {
                ui.show((i + 1) + "." + tasks.get(i));
            }
            return;
        case MARK: {
            int idx = Parser.parseIndex(input.substring(5), tasks.size());
            tasks.get(idx).mark();
            ui.show("Nice! I've marked this task as done:");
            ui.showIndented(tasks.get(idx).toString());
            return;
        }
        case UNMARK: {
            int idx = Parser.parseIndex(input.substring(7), tasks.size());
            tasks.get(idx).unmark();
            ui.show("OK, I've marked this task as not done yet:");
            ui.showIndented(tasks.get(idx).toString());
            return;
        }
        case DELETE: {
            int idx = Parser.parseIndex(input.substring(7), tasks.size());
            Task removed = tasks.remove(idx);
            ui.show("Noted. I've removed this task:");
            ui.showIndented(removed.toString());
            ui.show("Now you have " + tasks.size() + " tasks in the list.");
            return;
        }
        case TODO:
        case DEADLINE:
        case EVENT: {
            Task t = Parser.parseNewTask(cmd, input);
            tasks.add(t);
            ui.show("Got it. I've added this task:");
            ui.showIndented(t.toString());
            ui.show("Now you have " + tasks.size() + " tasks in the list.");
            return;
        }
        default:
            throw new ChinException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    public static void main(String[] args) {
        new Chin(Path.of("data", "chin.txt")).run();
    }
}
