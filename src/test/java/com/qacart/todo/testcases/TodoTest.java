package com.qacart.todo.testcases;
import com.qacart.todo.apis.TodoAPI;
import com.qacart.todo.data.ErrorMessage;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class TodoTest {
    @Test(description = "Should Be Able To Add Todo")
    public void shouldBeAbleToAddTodo(){

        String token= UserSteps.getUserAccessToken();
        Todo todo= TodoSteps.generateTodo();
        Response response= TodoAPI.addTodo(todo,token);
        Todo returnedTodo=response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnedTodo.getItem(),equalTo(todo.getItem()));
    }
    @Test(description = "Should Be Not Able To Add Todo If IsCompleted Is Missing")
    public void shouldBeNotAbleToAddTodoIfIsCompletedIsMissing(){
        Todo todo=new Todo("learn sds");
        String token= UserSteps.getUserAccessToken();
        Response response= TodoAPI.addTodo(todo,token);
        Error returedError=response.body().as(Error.class);
                assertThat(response.statusCode(),equalTo(400));
                assertThat(returedError.getMessage(),equalTo(ErrorMessage.ISCOMPLETED_IS_REQUIRED));
    }
    @Test(description = "Should Be Able To Get Todo With ID")
    public void shouldBeAbleToGetTodoWithID(){
        Todo todo=TodoSteps.generateTodo();
        String token= UserSteps.getUserAccessToken();
        String task_id=TodoSteps.getTodoID(todo,token);
        Response response= TodoAPI.getTodo(token,task_id);
        Todo returnedTodo=response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getId(),equalTo(task_id));
    }
    @Test(description = "Should Be Able To Delete Todo With ID")
    public void shouldBeAbleToDeleteTodoWithID(){
        Todo todo=TodoSteps.generateTodo();
        String token= UserSteps.getUserAccessToken();
        String task_id=TodoSteps.getTodoID(todo,token);
        Response response=TodoAPI.deleteTodo(token,task_id);
        Todo returnedTodo=response.body().as(Todo.class);
                assertThat(response.statusCode(),equalTo(200));
                assertThat(returnedTodo.getId(),equalTo(task_id));
    }
}
