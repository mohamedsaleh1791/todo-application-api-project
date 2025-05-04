package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserAPI;
import com.qacart.todo.data.ErrorMessage;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest

{
    @Test(description = "Should Be Able To Register")
    public void shouldBeAbleToRegister(){
        User user= UserSteps.generateUser();
       Response response= UserAPI.register(user);

       User returnedUser=response.body().as(User.class);

        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnedUser.getFirstName(),equalTo(user.getFirstName()));

    }
    @Test(description = "Should Not Be Able To Register With The Same Request")
    public void shouldNotBeAbleToRegisterWithTheSameRequest() {
        User user=UserSteps.getRegisteredUser();
        Response response= UserAPI.register(user);

        Error returnedError=response.body().as(Error.class);
                assertThat(response.statusCode(),equalTo(400));
                assertThat(returnedError.getMessage(),equalTo(ErrorMessage.EMAIL_IS_ALREADY_REGISTERED));

    }
    @Test(description = "Should Be Able To Login With Valid Username And Password")
    public void shouldBeAbleToLoginWithValidUsernameAndPassword(){
        User user=UserSteps.getRegisteredUser();
        User loginData=new User(user.getEmail(), user.getPassword());

        Response response= UserAPI.login(loginData);
        User returnedUser=response.body().as(User.class);
                assertThat(response.statusCode(),equalTo(200));
                assertThat(returnedUser.getFirstName(),equalTo(user.getFirstName()));
                assertThat(returnedUser.getAccessToken(),not(equalTo(null)));

    }
    @Test(description = "Should Not Be Able To Login If Password Is Not Correct")
    public void shouldNotBeAbleToLoginIfPasswordIsNotCorrect(){
        User user=UserSteps.getRegisteredUser();
        User loginData=new User(user.getEmail(), "Wrong Password");

        Response response= UserAPI.login(loginData);
        Error returnedError=response.body().as(Error.class);
            assertThat(response.statusCode(),equalTo(401));
            assertThat(returnedError.getMessage(),equalTo(ErrorMessage.EMAIL_OR_PASSOWRD_IS_WRONG));

    }
}
