import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskCreation() {
        LocalDateTime deadline = LocalDateTime.of(2023, 10, 15, 23, 59);
        Task task = new Task("Complete assignment", deadline, 1, "School");
        assertEquals("Complete assignment", task.getDescription());
        assertEquals(deadline, task.getDeadline());
        assertEquals(1, task.getPriority());
        assertEquals("School", task.getCategory());
        assertFalse(task.isCompleted());
    }

    @Test
    public void testSetDeadline() {
        Task task = new Task("Complete assignment", LocalDateTime.now(), 1, "School");
        LocalDateTime newDeadline = LocalDateTime.of(2023, 10, 20, 23, 59);
        task.setDeadline(newDeadline);
        assertEquals(newDeadline, task.getDeadline());
    }

    @Test
    public void testSetPriority() {
        Task task = new Task("Complete assignment", LocalDateTime.now(), 1, "School");
        task.setPriority(3);
        assertEquals(3, task.getPriority());
    }

    @Test
    public void testSetCompleted() {
        Task task = new Task("Complete assignment", LocalDateTime.now(), 1, "School");
        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }

    @Test
    public void testCompareTo() {
        Task task1 = new Task("Task 1", LocalDateTime.now(), 1, "Category");
        Task task2 = new Task("Task 2", LocalDateTime.now(), 2, "Category");
        assertTrue(task1.compareTo(task2) < 0);
    }
}