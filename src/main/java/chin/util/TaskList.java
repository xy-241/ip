package chin.util;

import java.util.ArrayList;
import java.util.List;

import chin.task.Task;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial);
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int i) {
        return tasks.get(i);
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task remove(int i) {
        return tasks.remove(i);
    }

    public List<Task> asList() {
        return tasks;
    }

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
