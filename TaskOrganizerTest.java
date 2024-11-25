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
        organizer.addTask("Complete assignment", deadline, 1, "School");
        organizer.addTask("Buy groceries", deadline, 3, "Personal");
    
        // Retrieve the task from the organizer to ensure it's the same instance
        Task taskToDelete = organizer.getTasksByCategory("School").get(0);
    
        // Act: Delete the task
        organizer.deleteTask(taskToDelete);
    
        // Assert: Verify the task was removed
        assertEquals(1, organizer.getTasks().size());
        assertTrue(organizer.getTasksByCategory("School").isEmpty());
        assertFalse(organizer.getCategoryMap().containsKey("School"));
        assertEquals(1, organizer.getTasksByCategory("Personal").size());
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