package com.qacart.todo.apis;

import com.qacart.todo.base.Spec;
import com.qacart.todo.data.Route;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoAPI {
    public static Response addTodo(Todo todo, String token){
        return given()
                .spec(Spec.getRequestSpec())
                .body(todo)
                .auth().oauth2(token)
                .when().post(Route.TASKS_ROUTE)
                .then().log().all().extract().response();
    }
    public static Response getTodo(String token,String task_id){
        return given()
                .spec(Spec.getRequestSpec())
                .auth().oauth2(token)
                .when().get(Route.TASKS_ROUTE+task_id)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response deleteTodo(String token,String task_id){
        return given()
                .spec(Spec.getRequestSpec())
                .auth().oauth2(token)
                .when().delete(Route.TASKS_ROUTE+task_id)
                .then()
                .log().all()
                .extract().response();
    }
}
