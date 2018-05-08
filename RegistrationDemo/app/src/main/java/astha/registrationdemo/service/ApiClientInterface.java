package astha.registrationdemo.service;

import astha.registrationdemo.model.LinkedInUserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by root on 8/5/18.
 */

public interface ApiClientInterface {

    @POST("/api/users")
    Call<LinkedInUserModel> registerUser(@Body LinkedInUserModel userModel);

}
