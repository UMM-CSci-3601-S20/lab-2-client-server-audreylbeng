package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
public class FilterTodosByStatusFromDB {

  @Test
  public void filterTodosByStatus() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] statusCompleteTodos = db.filterTodosByStatus(allTodos, true);
    assertEquals(143, statusCompleteTodos.length, "Incorrect number of todos with status 27");

    Todo[] statusIncompleteTodos = db.filterTodosByStatus(allTodos, false);
    assertEquals(157, statusIncompleteTodos.length, "Incorrect number of todos with status 33");
  }

  @Test
  public void listTodosWithStatusFilter() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("status", Arrays.asList(new String[] { "complete" }));
    Todo[] statusCompleteTodos = db.listTodos(queryParams);
    assertEquals(143, statusCompleteTodos.length, "Incorrect number of complete todos");

    queryParams.put("status", Arrays.asList(new String[] { "incomplete" }));
    Todo[] statusIncompleteTodos = db.listTodos(queryParams);
    assertEquals(157, statusIncompleteTodos.length, "Incorrect number of incomplete todos");
  }
}
