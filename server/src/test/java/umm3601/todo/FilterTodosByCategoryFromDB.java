package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.TodoDatabase filterTodosByCategory and listTodos with _category_ query
 * parameters
 */
public class FilterTodosByCategoryFromDB {

  @Test
  public void filterTodosByCategory() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] softwareDesignTodos = db.filterTodosByCategory(allTodos, "software design");
    assertEquals(74, softwareDesignTodos.length, "Incorrect number of todos for category 'software design'");

    Todo[] groceriesTodos = db.filterTodosByCategory(allTodos, "Groceries");
    assertEquals(76, groceriesTodos.length, "Incorrect number of todos for category 'Groceries'");

    Todo[] writingTodos = db.filterTodosByCategory(allTodos, "writing");
    assertEquals(0, writingTodos.length, "Incorrect number of todos for category 'writing'");
  }

  @Test
  public void listTodosWithCategoryFilter() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("category", Arrays.asList(new String[] { "Software design" }));
    Todo[] softwareDesignTodos = db.listTodos(queryParams);
    assertEquals(74, softwareDesignTodos.length, "Incorrect number of todos for category 'Software design'");

    queryParams.put("category", Arrays.asList(new String[] { "Groceries" }));
    Todo[] groceriesTodos = db.listTodos(queryParams);
    assertEquals(76, groceriesTodos.length, "Incorrect number of todos for category 'Groceries'");

    queryParams.put("category", Arrays.asList(new String[] { "Writing" }));
    Todo[] writingTodos = db.listTodos(queryParams);
    assertEquals(0, writingTodos.length, "Incorrect number of todos for category 'Writing'");
  }
}
