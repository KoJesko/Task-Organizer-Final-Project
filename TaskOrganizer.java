// TaskOrganizer.java

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class TaskOrganizer {
    private final ArrayList<Task> tasks;
    private final HashMap<String, ArrayList<Task>> categoryMap;
    private final PriorityQueue<Task> priorityQueue;

    public TaskOrganizer() {
        tasks = new ArrayList<>();
        categoryMap = new HashMap<>();
        priorityQueue = new PriorityQueue<>();
    }

    public void addTask(String description, LocalDateTime deadline,
            int priority, String category) {
        Task task = new Task(description, deadline, priority, category);
        tasks.add(task);
        priorityQueue.add(task);

        categoryMap.putIfAbsent(category, new ArrayList<>());
        categoryMap.get(category).add(task);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
        priorityQueue.remove(task);
        categoryMap.get(task.getCategory()).remove(task);
    }

    public ArrayList<Task> getTasksByCategory(String category) {
        return categoryMap.getOrDefault(category, new ArrayList<>());
    }

    public Task getNextPriorityTask() {
        return priorityQueue.peek();
    }

    public double getCompletionRate() {
        if (tasks.isEmpty())
            return 0.0;
        long completedCount = tasks.stream()
                .filter(Task::isCompleted)
                .count();
        return (double) completedCount / tasks.size() * 100;
    }
}