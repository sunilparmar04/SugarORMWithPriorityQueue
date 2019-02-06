package sunil.sugarormwithpriorityqueue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import sunil.sugarormwithpriorityqueue.db.UserDetails;
import sunil.sugarormwithpriorityqueue.services.SaveDataService;

public class MainActivity extends AppCompatActivity {
    ArrayList<UserDetails> userDetailsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.savedDataTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDetailsArrayList.clear();
                saveUserDetails();
            }
        });
    }

    private void saveUserDetails() {
        Log.v("download_executing", "saved details call");
        for (int i = 0; i < 100; i++) {
            UserDetails userDetails = new UserDetails();
            userDetails.setId(new Random().nextLong());
            userDetails.setName("sunil_" + new Random().nextInt());
            userDetailsArrayList.add(userDetails);
        }
        Log.v("download_executing", "saved details End");

        if (userDetailsArrayList.size() > 0) {
            startService(new Intent(MainActivity.this, SaveDataService.class).putParcelableArrayListExtra("userDetails", userDetailsArrayList));

        }
    }
}
