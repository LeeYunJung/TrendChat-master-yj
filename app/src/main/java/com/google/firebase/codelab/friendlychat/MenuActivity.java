package com.google.firebase.codelab.friendlychat;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import android.widget.Button;
import android.widget.EditText;


public class MenuActivity extends AppCompatActivity {

    static public int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Assign fields
        Button mButton1 = (Button) findViewById(R.id.button1);
        Button mButton2 = (Button) findViewById(R.id.button2);
        Button mButton3 = (Button) findViewById(R.id.button3);
        Button mButton4 = (Button) findViewById(R.id.button4);
        Button mButton5 = (Button) findViewById(R.id.button5);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board10sActivity.class );
                age = 10;
                startActivity(intent);
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board10sActivity.class );
                age = 20;
                startActivity(intent);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board10sActivity.class );
                age = 30;
                startActivity(intent);
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board10sActivity.class );
                age = 40;
                startActivity(intent);
            }
        });

        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board10sActivity.class );
                age = 50;
                startActivity(intent);
            }
        });
    }

}
