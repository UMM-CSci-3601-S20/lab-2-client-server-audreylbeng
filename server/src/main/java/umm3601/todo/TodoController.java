package umm3601.todo;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class TodoController {

    private TodoDatabase database;

    /**
     * Construct a controller for todos.
     * <p>
     * This loads the "database" of todo info from a JSON file and stores that
     * internally so that (subsets of) todos can be returned in response to
     * requests.
     *
     * @param database the `Database` containing todo data
     */
    public TodoController(TodoDatabase database) {
        this.database = database;
    }

    /**
    * Get the single todo specified by the `id` parameter in the request.
    *
    * @param ctx a Javalin HTTP context
    */
   public void getTodo(Context ctx) {

   }

   /**
    * Get a JSON response with a list of all the todos in the "database".
    *
    * @param ctx a Javalin HTTP context
    */
   public void getTodos(Context ctx) {

   }

}