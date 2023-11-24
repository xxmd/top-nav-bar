package io.github.xxmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button navBtn = findViewById(R.id.btn_profile);
        navBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MimeActivity.class);
            startActivity(intent);
        });
    }
}