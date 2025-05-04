package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.UserAPI;
import com.qacart.todo.models.User;
import io.restassured.response.Response;

public class UserSteps {
    public static User generateUser(){
        Faker faker=new Faker();
        String firstname=faker.name().firstName();
        String lastName=faker.name().lastName();
        String email=faker.internet().emailAddress();
        String password="123456789";
        return new User(firstname,lastName,email,password);

    }
    public static User getRegisteredUser(){
        User user=generateUser();
        UserAPI.register(user);
        return user;
    }
    public static String getUserAccessToken(){
        User user=generateUser();
        Response response=UserAPI.register(user);
        return response.body().path("access_token");
    }
}
