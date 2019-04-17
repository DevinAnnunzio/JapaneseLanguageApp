package com.example.android.japaneselanguageapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Set;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set content to use the activity_main.xml layout file*/
        setContentView(R.layout.activity_main);

        /* Find the View that shows the numbers category*/
        TextView numbers = findViewById(R.id.numbers);

        /* Set a click listener on that View*/
        numbers.setOnClickListener(new View.OnClickListener() {
            /* The code in this method will execute when the numbers View is clicked*/
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }
        });

        /* Find the View that shows the family category*/
        TextView family = findViewById(R.id.family);

        /* Set a click listener on that View*/
        family.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be execute when the family View is clicked
            @Override
            public void onClick(View view) {
                Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyIntent);
            }
        });

        /* Find the View that shows the colors category*/
        TextView colors = findViewById(R.id.colors);

        /* Set a click listener on that View*/
        colors.setOnClickListener(new View.OnClickListener() {
            /* The code in this method will be execute when the colors View is clicked*/
            @Override
            public void onClick(View view) {
                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(colorsIntent);
            }
        });

        /* Find the View that shows the phrases category*/
        TextView phrases = findViewById(R.id.phrases);

        /*Set a click listener on that View*/
        phrases.setOnClickListener(new View.OnClickListener() {
            /*The code in this method will be execute when the phrases View is clicked*/
            @Override
            public void onClick(View view) {
                final Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesIntent);

            }
        });

    }
}