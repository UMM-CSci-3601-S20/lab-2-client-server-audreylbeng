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

        //Limit
        if (queryParams.containsKey("limit")){
          int returnLength = Integer.parseInt(queryParams.get("limit").get(0));
          if (returnLength > filteredTodos.length){
            returnLength = filteredTodos.length;
          }
          filteredTodos = Arrays.copyOfRange(filteredTodos, 0, returnLength);
        }

        //Filter Category
        if (queryParams.containsKey("contains")) {
          String targetBody = queryParams.get("contains").get(0);
          filteredTodos = searchBody(filteredTodos, targetBody);

        //orderBy
        if(queryParams.containsKey("orderBy")){
          String targetAttribute = queryParams.get("orderBy").get(0);
          filteredTodos = orderBy(filteredTodos, targetAttribute);
        }

        //Filter Owner
        if (queryParams.containsKey("owner")) {
          String targetOwner = queryParams.get("owner").get(0);
          filteredTodos = filterTodosByOwner(filteredTodos, targetOwner);
        }

        //Filter Category
        if (queryParams.containsKey("category")) {
          String targetCategory = queryParams.get("category").get(0);
          filteredTodos = filterTodosByCategory(filteredTodos, targetCategory);
        }

        return filteredTodos;

    }

    /**
     *
     * @param todos         the list of todos to order
     * @param targetAttribute attribute by which to order
     * @return an array of all the todos in order that have the target attribute
     */
    public Todo[] orderBy(Todo[] todos, String targetAttribute) {
      switch(targetAttribute){

        case "owner":
          return Arrays.stream(todos).sorted((Todo t1, Todo t2) -> t1.owner.compareTo(t2.owner)).toArray(Todo[]::new);

        case "status":
          return Arrays.stream(todos).sorted((Todo t1, Todo t2) -> Boolean.compare(t1.status,t2.status)).toArray(Todo[]::new);

        case "body":
          return Arrays.stream(todos).sorted((Todo t1, Todo t2) -> t1.body.compareTo(t2.body)).toArray(Todo[]::new);

        case "category":
          return Arrays.stream(todos).sorted((Todo t1, Todo t2) -> t1.category.compareTo(t2.category)).toArray(Todo[]::new);

        default:
          throw new BadRequestResponse(targetAttribute + " is not a attribute of Todo");

      }
    }

    /**
     * Get an array of all the todos having the target owner.
     *
     * @param todos         the list of todos to filter by owner
     * @param targetOwner the target owner to look for
     * @return an array of all the todos from the given list that have the target
     *         owner
     */
    public Todo[] filterTodosByOwner(Todo[] todos, String targetOwner) {
      return Arrays.stream(todos).filter(x -> x.owner.equalsIgnoreCase(targetOwner)).toArray(Todo[]::new);
    }

    /**
     *
     * Get an array of all the todos having the target status.
     *
     * @param todos     the list of todos to filter by status
     * @param targetStatus the target status to look for
     * @return an array of all the todos from the given list that have the target
     *         status
     */
    public Todo[] filterTodosByStatus(Todo[] todos, boolean targetStatus) {
      return Arrays.stream(todos).filter(x -> x.status == targetStatus).toArray(Todo[]::new);
    }

    /**
     *
     * Get an array of all the todos having the target category.
     *
     * @param todos     the list of todos to filter by category
     * @param targetCategory the target category to look for
     * @return an array of all the todos from the given list that have the target
     *         category
     */
    public Todo[] filterTodosByCategory(Todo[] todos, String targetCategory) {
      return Arrays.stream(todos).filter(x -> x.category.equalsIgnoreCase(targetCategory)).toArray(Todo[]::new);
    }

    /**
     *
     * Get an array of all the todos containing the target body.
     *
     * @param todos     the list of todos to search by body
     * @param targetBody the target body to look for
     * @return an array of all the todos from the given list that have the target
     *         body
     */
    public Todo[] searchBody(Todo[] todos, String targetString) {
      return Arrays.stream(todos).filter(x -> x.body.toLowerCase().contains(targetString.toLowerCase())).toArray(Todo[]::new);
    }

}
