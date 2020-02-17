package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.TodoDatabase filterTodosByOwner and listTodos with _owner_ query
 * parameters
 */
public class FilterTodosByOwnerFromDB {

  @Test
  public void filterTodosByOwner() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] blancheTodos = db.filterTodosByOwner(allTodos, "Blanche");
    assertEquals(43, blancheTodos.length, "Incorrect number of todos for owner 'Blanche'");

    Todo[] fryTodos = db.filterTodosByOwner(allTodos, "Fry");
    assertEquals(61, fryTodos.length, "Incorrect number of todos for owner 'Fry'");

    Todo[] audreyTodos = db.filterTodosByOwner(allTodos, "Audrey");
    assertEquals(0, audreyTodos.length, "Incorrect number of todos for owner 'Audrey'");
  }

  @Test
  public void listTodosWithOwnerFilter() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("owner", Arrays.asList(new String[] { "Blanche" }));
    Todo[] blancheTodos = db.listTodos(queryParams);
    assertEquals(43, blancheTodos.length, "Incorrect number of todos for owner 'Blanche'");

    queryParams.put("owner", Arrays.asList(new String[] { "Fry" }));
    Todo[] fryTodos = db.listTodos(queryParams);
    assertEquals(61, fryTodos.length, "Incorrect number of todos for owner 'Fry'");

    queryParams.put("owner", Arrays.asList(new String[] { "Audrey" }));
    Todo[] audreyTodos = db.listTodos(queryParams);
    assertEquals(0, audreyTodos.length, "Incorrect number of todos for owner 'Audrey'");
  }
}
