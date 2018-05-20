package com.example.madcat.androidviewmodelexample;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SimpleViewModel model;

    Button addButton;
    Button clearButton;
    EditText newText;
    EditText viewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        clearButton = findViewById(R.id.clearButton);
        newText = findViewById(R.id.newText);
        viewText = findViewById(R.id.viewText);

        addButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);

        model = ViewModelProviders.of(this).get(SimpleViewModel.class);
        LiveData<String> data = model.getData();
        LiveData<String> statusData = model.getStatusData();

        data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                StringBuilder buffer = new StringBuilder();

                buffer.append(viewText.getText().toString());
                buffer.append(s).append("\n");

                viewText.setText(buffer.toString());
            }
        });

        statusData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                newText.setText(s);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.addButton:
                model.loadData(Integer.parseInt(newText.getText().toString()));
                newText.setText("");
                break;
            case R.id.clearButton:
                viewText.setText("");
                newText.setText("");
                break;
        }
    }
}
