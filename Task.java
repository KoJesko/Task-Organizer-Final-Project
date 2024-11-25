import java.time.LocalDateTime;

public class Task implements Comparable<Task> {
    private final String description;
    private LocalDateTime deadline;
    private int priority;
    private boolean completed;
    private final String category;

    public Task(String description, LocalDateTime deadline, int priority, String category) {
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.completed = false;
    }

    // Getters and setters
    public String getDescription() { return description; }
    public LocalDateTime getDeadline() { return deadline; }
    public int getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
    public String getCategory() { return category; }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", deadline=" + deadline +
                ", priority=" + priority +
                ", completed=" + completed +
                ", category='" + category + '\'' +
                '}';
    }
}