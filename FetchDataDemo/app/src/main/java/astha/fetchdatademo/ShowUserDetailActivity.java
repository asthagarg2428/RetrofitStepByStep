package astha.fetchdatademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import astha.fetchdatademo.model.DataModel;
import astha.fetchdatademo.model.UserModel;
import astha.fetchdatademo.utility.Macro;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowUserDetailActivity extends AppCompatActivity {

    private TextView lastNameTV, firstNameTV;
    private ImageView avatarIV;
    private EditText userIdET;
    private UserModel userObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_detail);

        lastNameTV = (TextView) findViewById(R.id.last_name_TV);
        userIdET = (EditText) findViewById(R.id.user_id_ET);
        firstNameTV = (TextView) findViewById(R.id.first_name_TV);
        avatarIV = (ImageView) findViewById(R.id.avatar_IV);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void fetchUserDetails(String id) {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClientBuilder.addInterceptor(interceptor);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Macro.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build());

        Retrofit retrofit = builder.build();

        ClientInterface clientInterface = retrofit.create(ClientInterface.class);
        Call<DataModel> dataModelCall = clientInterface.fetchUserDetail(id);
        dataModelCall.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if(response != null && response.body() != null && response.body().getData() != null)
                {
                    userObject = response.body().getData();
                    displayUserDetails();
                }
                else
                {
                    Toast.makeText(ShowUserDetailActivity.this, "Error Occured :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ShowUserDetailActivity.this, "Error Occured :(", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void displayUserDetails() {
        if(userObject != null)
        {

            firstNameTV.setText(userObject.getFirst_name());
            lastNameTV.setText(userObject.getLast_name());
            Glide.with(ShowUserDetailActivity.this).
                    load(userObject.getAvatar()).
                    into(avatarIV);
        }
    }

    public void fetchUser(View view)
    {
        if(userIdET.getText().toString().trim().length() == 0)
        {
            Toast.makeText(ShowUserDetailActivity.this, "Enter userId", Toast.LENGTH_SHORT).show();
            return;
        }
        fetchUserDetails(userIdET.getText().toString().trim());
    }
}
