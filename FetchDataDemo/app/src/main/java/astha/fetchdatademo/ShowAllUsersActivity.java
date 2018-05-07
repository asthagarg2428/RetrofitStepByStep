package astha.fetchdatademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import astha.fetchdatademo.model.UserListModel;
import astha.fetchdatademo.model.UserModel;
import astha.fetchdatademo.utility.Macro;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowAllUsersActivity extends AppCompatActivity {

    private RecyclerView allUsersRecyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_users);
        allUsersRecyclerView = (RecyclerView) findViewById(R.id.all_users_recycler_view);

        allUsersRecyclerView.setLayoutManager(new LinearLayoutManager(ShowAllUsersActivity.this));
        allUsersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fetchAllUsers();
    }


    private void fetchAllUsers()
    {

        HttpLoggingInterceptor httpLoggingInterceptor  = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(Macro.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build());

        Retrofit retrofit = retrofitBuilder.build();
        ClientInterface clientInterface = retrofit.create(ClientInterface.class);

        Call<UserListModel> call = clientInterface.fetchUserList();
        call.enqueue(new Callback<UserListModel>() {
            @Override
            public void onResponse(Call<UserListModel> call, Response<UserListModel> response) {
                if(response != null)
                {
                    UserListModel userListModel =  response.body();
                    if(userListModel != null && userListModel.getData() != null)
                    {
                        UserModel userModelArray[] = userListModel.getData();
                        if(userModelArray.length > 0)
                        {
                            MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(userModelArray,ShowAllUsersActivity.this);
                            allUsersRecyclerView.setAdapter(adapter);
                            return;
                        }
                    }
                    Toast.makeText(ShowAllUsersActivity.this,"Error occured :(",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserListModel> call, Throwable t) {
                Toast.makeText(ShowAllUsersActivity.this,"Error occured :(",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
