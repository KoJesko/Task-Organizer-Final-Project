import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task> {
    private String description;
    private LocalDateTime deadline;
    private int priority;
    private String category;
    private boolean completed;

    public Task(String description, LocalDateTime deadline, int priority, String category) {
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5.");
        }
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return priority == task.priority &&
               Objects.equals(description, task.description) &&
               Objects.equals(deadline, task.deadline) &&
               Objects.equals(category, task.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, deadline, priority, category);
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
               ", category='" + category + '\'' +
               ", completed=" + completed +
               '}';
    }
}
