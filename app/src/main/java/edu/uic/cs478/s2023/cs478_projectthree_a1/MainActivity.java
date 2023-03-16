package edu.uic.cs478.s2023.cs478_projectthree_a1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final String PROJECT_PERM = "edu.uic.cs478.spring23.mp3";
    final int CITY_REQUEST_CODE = 1;

    Button nycButton;
    Button orlandoButton;
    String selectedCity;
    Boolean verifiedPermGrant = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnClickListener clickBroadcast = v -> {
            selectedCity = ((Button) v).getText().toString();
            if (ContextCompat.checkSelfPermission(MainActivity.this, PROJECT_PERM) == PackageManager.PERMISSION_GRANTED) {
                shootBroadcast();
                Toast.makeText(this, "You have already have the broadcast permission.", Toast.LENGTH_LONG).show();
            } else {
                requestProjectPermission();
            }
//            Toast.makeText(MainActivity.this, "Clicked " + selectedCity + " Button", Toast.LENGTH_SHORT).show();
        };

        nycButton = findViewById(R.id.nycButton);
        orlandoButton = findViewById(R.id.orlandoButton);
        nycButton.setOnClickListener(clickBroadcast);
        orlandoButton.setOnClickListener(clickBroadcast);
    }

    private void requestProjectPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, PROJECT_PERM)) {
            AlertDialog.Builder permAlert = new AlertDialog.Builder(this);
            permAlert.setTitle("Permission Required!");
            permAlert.setMessage("This permission is required in order for App One to connect to App Two!");
            permAlert.setPositiveButton("Ok", (dialogInterface, i) ->ActivityCompat.requestPermissions(
                    MainActivity.this, new String[]{PROJECT_PERM}, CITY_REQUEST_CODE));
            permAlert.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            permAlert.create();
            permAlert.show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{PROJECT_PERM}, CITY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CITY_REQUEST_CODE) {
//            :D
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                shootBroadcast();
                Toast.makeText(this, "Permission " + PROJECT_PERM + " has now been granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission " + PROJECT_PERM + " request was denied.", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void shootBroadcast() {
        Toast.makeText(this, "Now sending Broadcasting Intent...", Toast.LENGTH_SHORT).show();
        Intent broadcast = new Intent(PROJECT_PERM);
        broadcast.putExtra("sentCity", selectedCity);
        broadcast.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(broadcast);
    }
}