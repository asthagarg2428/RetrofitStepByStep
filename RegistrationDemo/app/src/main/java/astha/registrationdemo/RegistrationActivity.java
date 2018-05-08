package astha.registrationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import astha.registrationdemo.model.LinkedInUserModel;
import astha.registrationdemo.service.ApiClientInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    EditText nameET, jobET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        nameET = (EditText) findViewById(R.id.name_ET);
        jobET = (EditText) findViewById(R.id.job_ET);
    }

    public void handleRegistration(View view)
    {
        if(nameET.getText().toString().trim().length() == 0 || jobET.getText().toString().trim().length() == 0)
        {
            Toast.makeText(RegistrationActivity.this,"Kindly fill details",Toast.LENGTH_SHORT).show();
            return;
        }
        String name = nameET.getText().toString();
        String job = jobET.getText().toString();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClientInterface clientInterface = retrofit.create(ApiClientInterface.class);
        LinkedInUserModel userModel = new LinkedInUserModel();
        userModel.setName(name);
        userModel.setJob(job);
        Call<LinkedInUserModel> linkedInUserModelCall = clientInterface.registerUser(userModel);
        linkedInUserModelCall.enqueue(new Callback<LinkedInUserModel>() {
            @Override
            public void onResponse(Call<LinkedInUserModel> call, Response<LinkedInUserModel> response) {
                if(response != null && response.body() != null)
                {
                    LinkedInUserModel model = response.body();
                    Toast.makeText(RegistrationActivity.this,"Success id = "+model.getId()+ "at "+model.getCreatedAt(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RegistrationActivity.this,"Error Occured :(",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LinkedInUserModel> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this,"Error Occured :(",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
