package com.easy.eightfivehundred.resell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PostActivity2 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner spinner;
    Button postButton;
    EditText priceEditText, locationEditText;
    TextView summaryTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);
        spinner = findViewById(R.id.condition_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_condition, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String brand = getIntent().getStringExtra("brand");
        postButton = findViewById(R.id.post_button);
        postButton.setOnClickListener(this);
        summaryTextView = findViewById(R.id.summary_text_view);
        summaryTextView.setText(title + "\n" + description + "\n" + brand + "\n");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
