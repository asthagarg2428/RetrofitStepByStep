package astha.fetchdatademo;

import astha.fetchdatademo.model.DataModel;
import astha.fetchdatademo.model.UserListModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by root on 6/5/18.
 */

public interface ClientInterface {

    @GET("/api/users?page=2")
    Call<UserListModel> fetchUserList();

    @GET("/api/users/{id}")
    Call<DataModel> fetchUserDetail(@Path("id") String id);
}


