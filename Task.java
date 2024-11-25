// Task.java

import java.time.LocalDateTime;

public class Task implements Comparable<Task> {
    private final String description;
    private final LocalDateTime deadline;
    private final int priority; // 1 (highest) to 5 (lowest)
    private final boolean completed;
    private final String category;

    public Task(String description, LocalDateTime deadline, int priority, String category) {
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.completed = false;
    }

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }
}