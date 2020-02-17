package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database listTodos with _age_ and _company_ query
 * parameters
 */
public class FilterTodosByCombinedFiltersFromDB {

  @Test
  public void listTodosWithCombinedFilters() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("owner", Arrays.asList(new String[] { "Blanche" }));
    Todo[] blancheTodos = db.listTodos(queryParams);
    assertEquals(43, blancheTodos.length, "Incorrect number of todos for owner 'Blanche'");

    queryParams.clear();
    queryParams.put("status", Arrays.asList(new String[] { "complete" }));
    Todo[] statusCompleteTodos = db.listTodos(queryParams);
    assertEquals(143, statusCompleteTodos.length, "Incorrect number of complete todos");

    queryParams.clear();
    queryParams.put("owner", Arrays.asList(new String[] { "Blanche" }));
    queryParams.put("status", Arrays.asList(new String[] { "complete" }));
    Todo[] blancheCompleteTodos = db.listTodos(queryParams);
    assertEquals(22, blancheCompleteTodos.length, "Incorrect number of todos with owner Blanche and status complete");
  }
}
