import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TaskOrganizer {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final HashMap<String, ArrayList<Task>> categoryMap = new HashMap<>();
    private final PriorityQueue<Task> priorityQueue = new PriorityQueue<>();

    public TaskOrganizer() {
    }

    public void addTask(String description, LocalDateTime deadline, int priority, String category) {
        Task task = new Task(description, deadline, priority, category);
        this.tasks.add(task);
        this.priorityQueue.add(task);
        this.categoryMap.putIfAbsent(category, new ArrayList<>());
        this.categoryMap.get(category).add(task);
    }

    public void deleteTask(Task task) {
        // Remove the task from the main task list
        this.tasks.remove(task);
        // Remove the task from the priority queue
        this.priorityQueue.remove(task);
        // Retrieve the tasks for the specific category
        ArrayList<Task> categoryTasks = this.categoryMap.get(task.getCategory());
        if (categoryTasks != null) {
            // Remove the task from the category list
            categoryTasks.remove(task);
            // If the category list becomes empty, remove the category entry
            if (categoryTasks.isEmpty()) {
                this.categoryMap.remove(task.getCategory());
            }
        }
    }
    

    public ArrayList<Task> getTasksByCategory(String category) {
        return this.categoryMap.getOrDefault(category, new ArrayList<>());
    }

    public Task getNextPriorityTask() {
        return this.priorityQueue.peek();
    }

    public double getCompletionRate() {
        if (this.tasks.isEmpty()) {
            return 0.0;
        } else {
            long completedCount = this.tasks.stream().filter(Task::isCompleted).count();
            return (double) completedCount / this.tasks.size() * 100.0;
        }
    }
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks); // Return a copy to prevent external modification
    }

    public HashMap<String, ArrayList<Task>> getCategoryMap() {
        return new HashMap<>(categoryMap); // Return a copy to prevent external modification
    }
    public static void main(String[] args) {
        TaskOrganizer organizer = new TaskOrganizer();
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks by Category");
            System.out.println("3. View Next Priority Task");
            System.out.println("4. View Completion Rate");
            System.out.println("5. Delete Task");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter task description:");
                    String description = scanner.nextLine();
                    LocalDateTime deadline = null;
                    while (deadline == null) {
                        System.out.println("Enter deadline (yyyy-MM-dd HH:mm):");
                        String deadlineStr = scanner.nextLine();
                        try {
                            deadline = LocalDateTime.parse(deadlineStr, formatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }
                    int priority = 0;
                    while (priority < 1 || priority > 5) {
                        System.out.println("Enter priority (1-5):");
                        if (scanner.hasNextInt()) {
                            priority = scanner.nextInt();
                            if (priority < 1 || priority > 5) {
                                System.out.println("Invalid priority. Please enter a number between 1 and 5.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a number between 1 and 5.");
                            scanner.next(); // Consume invalid input
                        }
                    }
                    scanner.nextLine();  // Consume newline
                    System.out.println("Enter category:");
                    String category = scanner.nextLine();
                    organizer.addTask(description, deadline, priority, category);
                }
                case 2 -> {
                    System.out.println("Enter category:");
                    String cat = scanner.nextLine();
                    ArrayList<Task> tasksByCategory = organizer.getTasksByCategory(cat);
                    System.out.println("Tasks in category " + cat + ": " + tasksByCategory);
                }
                case 3 -> {
                    Task nextTask = organizer.getNextPriorityTask();
                    System.out.println("Next priority task: " + (nextTask != null ? nextTask.getDescription() : "None"));
                    //break;
                }
                case 4 -> System.out.println("Completion rate: " + organizer.getCompletionRate() + "%");
                case 5 -> {
                    System.out.println("Enter task description to delete:");
                    String descriptionToDelete = scanner.nextLine();
                    Task taskToDelete = null;
                    for (Task task : organizer.tasks) {
                        if (task.getDescription().equals(descriptionToDelete)) {
                            taskToDelete = task;
                            break;
                        }
                    }
                    if (taskToDelete != null) {
                        organizer.deleteTask(taskToDelete);
                        System.out.println("Task deleted successfully.");
                    } else {
                        System.out.println("Task not found.");
                    }
                }
                case 6 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}