package astha.fetchdatademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fetchSingleUser(View view)
    {
        startActivity(new Intent(FirstActivity.this,ShowUserDetailActivity.class));
    }
    public void fetchAllUsers(View view)
    {
        startActivity(new Intent(FirstActivity.this,ShowAllUsersActivity.class));

    }
}
