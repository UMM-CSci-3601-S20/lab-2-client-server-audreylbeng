package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    queryParams.put("category", Arrays.asList(new String[] { "Homework" }));
    Todo[] homeworkTodos = db.listTodos(queryParams);
    assertEquals(79, homeworkTodos.length, "Incorrect number of todos for category 'Homework'");

    queryParams.clear();
    queryParams.put("owner", Arrays.asList(new String[] { "Blanche" }));
    queryParams.put("status", Arrays.asList(new String[] { "complete" }));
    queryParams.put("category", Arrays.asList(new String[] { "Homework" }));

    //test filters
    Todo[] blancheCompleteHomeworkTodos = db.listTodos(queryParams);
    assertEquals(5, blancheCompleteHomeworkTodos.length, "Incorrect number of todos with owner Blanche, status complete and category homework");

    //test ordering of body
    Random r = new Random();
    int n = r.nextInt(blancheCompleteHomeworkTodos.length);
    Todo[] sortedBody = db.orderBy(blancheCompleteHomeworkTodos, "body");
    String lastBody = sortedBody[0].body;

    for(int i = 1; i<blancheCompleteHomeworkTodos.length; i += n){
        assertEquals(true, lastBody.compareTo(sortedBody[i].body) <= 0, "Bodies are not sorted correctly");
        lastBody = sortedBody[i].body;
    }
  }
}
