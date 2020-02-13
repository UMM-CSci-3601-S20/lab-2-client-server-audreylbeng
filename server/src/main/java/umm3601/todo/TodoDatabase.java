package umm3601.todo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import io.javalin.http.BadRequestResponse;

public class TodoDatabase {

    private Todo[] allTodos;

    public TodoDatabase(String todoDataFile) throws IOException {
      Gson gson = new Gson();
      InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(todoDataFile));
      allTodos = gson.fromJson(reader, Todo[].class);
    }

    public int size() {
      return allTodos.length;
    }

    /**
     * Get the single todo specified by the given ID. Return `null` if there is no
     * todo with that ID.
     *
     * @param id the ID of the desired todo
     * @return the todo with the given ID, or null if there is no todo with that ID
     */
    public Todo getTodo(String id) {
        return Arrays.stream(allTodos).filter(x -> x._id.equals(id)).findFirst().orElse(null);
    }

    /**
     * Get an array of all the todos satisfying the queries in the params.
     *
     * @param queryParams map of key-value pairs for the query
     * @return an array of all the todos matching the given criteria
     */
    public Todo[] listTodos(Map<String, List<String>> queryParams) {

        Todo[] filteredTodos = allTodos;

        //insert filters here
        if (queryParams.containsKey("status")) {
          String tStatus = queryParams.get("status").get(0);
          boolean targetStatus;
            if(tStatus.equals("complete")) targetStatus = true;
            else if(tStatus.equals("incomplete")) targetStatus = false;
            else throw new BadRequestResponse("Specified status '" + tStatus + "' is neither 'complete' or 'incomplete'");
            filteredTodos = filterTodosByStatus(filteredTodos, targetStatus);
        }

        return filteredTodos;
    }

    /**
     * Get an array of all the todos having the target age.
     *
     * @param todos     the list of todos to filter by age
     * @param targetAge the target age to look for
     * @return an array of all the todos from the given list that have the target
     *         age
     */
    public Todo[] filterTodosByStatus(Todo[] todos, boolean targetStatus) {
      return Arrays.stream(todos).filter(x -> x.status == targetStatus).toArray(Todo[]::new);
    }

}
