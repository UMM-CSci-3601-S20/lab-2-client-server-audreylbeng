package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.user.Database filterUsersByAge and listUsers with _age_ query
 * parameters
 */
public class OrderByAttributeFromDB {

  @Test
  public void orderBy() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Random r = new Random();
    int n = r.nextInt(allTodos.length);

    Todo[] sortedOwner = db.orderBy(allTodos, "owner");
    Todo[] sortedStatus = db.orderBy(allTodos, "status");
    Todo[] sortedBody = db.orderBy(allTodos, "body");
    Todo[] sortedCategory = db.orderBy(allTodos, "category");

    String lastOwner = sortedOwner[0].owner;
    boolean lastStatus = sortedStatus[0].status;
    String lastBody = sortedBody[0].body;
    String lastCategory = sortedCategory[0].category;

    for(int i = 1; i<allTodos.length; i += n){
        assertEquals(true, lastOwner.compareTo(sortedOwner[i].owner) <= 0, "Owners are not sorted correctly");
        lastOwner = sortedOwner[i].owner;
        assertEquals(true, Boolean.compare(lastStatus, sortedStatus[i].status) <= 0, "Status is not sorted correctly");
        lastStatus = sortedStatus[i].status;
        assertEquals(true, lastBody.compareTo(sortedBody[i].body) <= 0, "Bodies are not sorted correctly");
        lastBody = sortedBody[i].body;
        assertEquals(true, lastCategory.compareTo(sortedCategory[i].category) <= 0, "Categories are not sorted correctly");
        lastCategory = sortedCategory[i].category;
    }
  }

}
