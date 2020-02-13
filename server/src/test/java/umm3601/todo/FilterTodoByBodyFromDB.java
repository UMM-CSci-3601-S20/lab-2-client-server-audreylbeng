package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import umm3601.Server;


public class FilterTodoByBodyFromDB {

    @Test
    public void FilterBySint() throws IOException {
        TodoDatabase db = new TodoDatabase("/todos.json");
        Map<String, List<String>> queryParams = new HashMap<>();

        queryParams.put("contains", Arrays.asList(new String[] {"sint"}));
        Todo[] sintTodos = db.listTodos(queryParams);

        assertEquals(97, sintTodos.length, "Wrong number of todos returned.");
        assertEquals("58895985a22c04e761776d54", sintTodos[0]._id, "First todo returned was incorrect.");
        for(Todo t: sintTodos){
            assertTrue(t.body.contains("sint"), "One of the returned todos doesn't contain \"sint\".");
        }
    }

    @Test
    public void FilterByNisi() throws IOException {
        TodoDatabase db = new TodoDatabase("/todos.json");
        Map<String, List<String>> queryParams = new HashMap<>();

        queryParams.put("contains", Arrays.asList(new String[] {"nisi"}));
        Todo[] nisiTodos = db.listTodos(queryParams);

        assertEquals(97, nisiTodos.length, "Wrong number of todos returned.");
        assertEquals("58895985186754887e0381f5", nisiTodos[0]._id, "First todo returned was incorrect.");
        for(Todo t: nisiTodos){
            assertTrue(t.body.contains("nisi"), "One of the returned todos doesn't contain \"nisi\".");
        }
    }

    @Test
    public void FilterGarbage() throws IOException {
        TodoDatabase db = new TodoDatabase("/todos.json");
        Map<String, List<String>> queryParams = new HashMap<>();

        queryParams.put("contains", Arrays.asList(new String[] {"garbage"}));
        Todo[] garbageTodos = db.listTodos(queryParams);

        assertEquals(0, garbageTodos.length, "Incorrectly retrieved a todo for a filter not in the database.");

    }
}
