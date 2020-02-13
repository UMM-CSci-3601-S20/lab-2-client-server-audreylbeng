package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.Todo.Database listTodo functionality
 */
public class FullTodoListFromDB {

  @Test
  public void totalTodoCount() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    assertEquals(300, allTodos.length, "Incorrect total number of Todos");
  }

  @Test
  public void firstTodoInFullList() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo firstTodo = allTodos[0];
    assertEquals(firstTodo.owner, "Blanche", "Wrong owner on first todo.");
    assertEquals(firstTodo.status, false, "Wrong status on first todo.");
    assertEquals(firstTodo.body, "In sunt ex non tempor cillum commodo amet incididunt anim qui commodo quis. Cillum non labore ex sint esse.",
                "Wrong body on first todo.");
    assertEquals(firstTodo.category, "software design", "Wrong category on first todo.");
  }
}