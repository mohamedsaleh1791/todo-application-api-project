package com.qacart.todo.base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Spec {
    public static String getEnv(){
        String env= System.getProperty("env","Production");
        String baseURL="";
        switch(env){
            case "Production":
                baseURL="https://qacart-todo.herokuapp.com";
                break;
            case "Local":
                baseURL="localhaost.com";
                break;
             default:
                 System.out.println("Test");
        }
        return baseURL;
    }

    public static RequestSpecification getRequestSpec(){
        RequestSpecification reqspec= given()
                .baseUri(getEnv())
                .contentType(ContentType.JSON)
                .log().all();
        return reqspec;
    }
}
