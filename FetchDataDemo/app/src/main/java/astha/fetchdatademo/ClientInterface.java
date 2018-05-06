package astha.fetchdatademo;

import astha.fetchdatademo.model.DataModel;
import astha.fetchdatademo.model.UserListModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by root on 6/5/18.
 */

public interface ClientInterface {

    @GET("/api/users?page={pageNo}")
    Call<UserListModel> fetchUserList(@Path("pageNo") int pathNo);

    @GET("/api/users/{id}")
    Call<DataModel> fetchUserDetail(@Path("id") String id);
}


