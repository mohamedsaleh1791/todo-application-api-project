package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.TodoAPI;
import com.qacart.todo.models.Todo;
import io.restassured.response.Response;

public class TodoSteps {
    public static Todo generateTodo(){
        Faker faker=new Faker();
        String item=faker.book().title();
        boolean isCompleted=false;
        return new Todo(isCompleted,item);

    }
    public static String getTodoID(Todo todo,String accessToken){

        Response response=TodoAPI.addTodo(todo,accessToken);
        return response.body().path("_id");

    }
}
