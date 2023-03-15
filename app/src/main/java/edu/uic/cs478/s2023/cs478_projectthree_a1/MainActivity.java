package edu.uic.cs478.s2023.cs478_projectthree_a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final String PROJECT_PERM = "edu.uic.cs478.spring23.mp3";

    Button nycButton;
    Button orlandoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[]{PROJECT_PERM}, 1);

        View.OnClickListener clickBroadcast = v -> {
            String btnTitle = ((Button) v).getText().toString();
            Toast.makeText(MainActivity.this, "Clicked " + btnTitle + " Button", Toast.LENGTH_SHORT).show();
            Intent broadcast = new Intent();
            broadcast.setAction(PROJECT_PERM);
            broadcast.putExtra("sentCity", btnTitle);
            sendBroadcast(broadcast, PROJECT_PERM);
        };

        nycButton = findViewById(R.id.nycButton);
        orlandoButton = findViewById(R.id.orlandoButton);
        nycButton.setOnClickListener(clickBroadcast);
        orlandoButton.setOnClickListener(clickBroadcast);
    }

}