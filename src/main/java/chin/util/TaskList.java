package chin.util;

import java.util.ArrayList;
import java.util.List;

import chin.task.Task;

/**
 * Ordered, mutable list of {@link Task} entries with lookup and search helpers.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /** Creates a task list initialised from {@code initial}. */
    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial);
    }

    /** Returns the number of tasks in this list. */
    public int size() {
        return tasks.size();
    }

    /** Returns the task at the given zero-based index. */
    public Task get(int i) {
        return tasks.get(i);
    }

    /** Appends a task to the end of the list. */
    public void add(Task t) {
        tasks.add(t);
    }

    /** Removes and returns the task at the given zero-based index. */
    public Task remove(int i) {
        return tasks.remove(i);
    }

    /** Returns the backing list as a read-through view for callers like storage. */
    public List<Task> asList() {
        return tasks;
    }

    /** Returns tasks whose string form contains {@code keyword} (case-insensitive). */
    public List<Task> find(String keyword) {
        List<Task> matches = new ArrayList<>();
        String needle = keyword.toLowerCase();
        for (Task t : tasks) {
            if (t.toString().toLowerCase().contains(needle)) {
                matches.add(t);
            }
        }
        return matches;
    }
}
