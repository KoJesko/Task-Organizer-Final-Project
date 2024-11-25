import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TaskOrganizerTest {
    private TaskOrganizer organizer;

    @BeforeEach
    public void setUp() {
        organizer = new TaskOrganizer();
    }

    @Test
    public void testAddTask() {
        LocalDateTime deadline = LocalDateTime.of(2023, 10, 15, 23, 59);
        organizer.addTask("Complete assignment", deadline, 1, "School");
        ArrayList<Task> tasks = organizer.getTasksByCategory("School");
        assertEquals(1, tasks.size());
        assertEquals("Complete assignment", tasks.get(0).getDescription());
    }

    @Test
    public void testGetTasksByCategory() {
        LocalDateTime deadline = LocalDateTime.of(2023, 10, 15, 23, 59);
        organizer.addTask("Complete assignment", deadline, 1, "School");
        organizer.addTask("Buy groceries", deadline, 3, "Personal");
        ArrayList<Task> schoolTasks = organizer.getTasksByCategory("School");
        ArrayList<Task> personalTasks = organizer.getTasksByCategory("Personal");
        assertEquals(1, schoolTasks.size());
        assertEquals(1, personalTasks.size());
    }


    @Test
    public void testDeleteTask() {
        // Arrange: Add tasks to the organizer
        LocalDateTime deadline = LocalDateTime.of(2023, 10, 15, 23, 59);
        Task task1 = new Task("Complete assignment", deadline, 1, "School");
        Task task2 = new Task("Buy groceries", deadline, 3, "Personal");

        organizer.addTask(task1.getDescription(), task1.getDeadline(), task1.getPriority(), task1.getCategory());
        organizer.addTask(task2.getDescription(), task2.getDeadline(), task2.getPriority(), task2.getCategory());

        // Assert preconditions: Ensure tasks are added
        assertEquals(2, organizer.getTasks().size());
        assertEquals(1, organizer.getTasksByCategory("School").size());
        assertEquals(1, organizer.getTasksByCategory("Personal").size());
        assertNotNull(organizer.getNextPriorityTask());

        // Act: Delete a task
        organizer.deleteTask(task1);

        // Assert postconditions: Ensure the task is deleted from all data structures
        assertEquals(1, organizer.getTasks().size()); // Only one task remains in the main list
        assertTrue(organizer.getTasksByCategory("School").isEmpty()); // "School" category should be empty
        assertEquals(1, organizer.getTasksByCategory("Personal").size()); // "Personal" category remains unaffected
        assertNotEquals(task1, organizer.getNextPriorityTask()); // Priority queue no longer contains task1

        // Cleanup: Check if category is removed from categoryMap when empty
        assertFalse(organizer.getCategoryMap().containsKey("School"));
    }

    @Test
    public void testGetNextPriorityTask() {
        LocalDateTime deadline = LocalDateTime.of(2023, 10, 15, 23, 59);
        organizer.addTask("Complete assignment", deadline, 1, "School");
        organizer.addTask("Buy groceries", deadline, 3, "Personal");
        Task nextTask = organizer.getNextPriorityTask();
        assertEquals("Complete assignment", nextTask.getDescription());
    }

    @Test
    public void testGetCompletionRate() {
        LocalDateTime deadline = LocalDateTime.of(2023, 10, 15, 23, 59);
        organizer.addTask("Complete assignment", deadline, 1, "School");
        organizer.addTask("Buy groceries", deadline, 3, "Personal");
        Task task = organizer.getTasksByCategory("School").get(0);
        task.setCompleted(true);
        assertEquals(50.0, organizer.getCompletionRate());
    }
}