package com.example.a623j;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private TextView text;
    ArrayList<Data> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button save = findViewById(R.id.save);
        text = findViewById(R.id.text);
        LoadData();
        save.setOnClickListener(view ->
                saveData(email.getText().toString(), password.getText().toString()));
    }

    private void saveData(String name, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        data.add(new Data(name, password));
        String json = gson.toJson(data);
        editor.putString("data", json);
        editor.apply();
        LoadData();
//        clearAll();
    }

    @SuppressLint("SetTextI18n")
    private void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("data", null);
        Type type = new TypeToken<ArrayList<Data>>() {
        }.getType();
        data = gson.fromJson(json, type);
        if (data == null) {
            data = new ArrayList<>();
            text.setText("");
        } else {
            for (int i = 0; i < data.size(); i++) {
                text.setText(text.getText().toString() + "\n" + data.get(i).email + " ");
                text.setText(text.getText().toString() + "\n" + data.get(i).password + "\n");
            }
        }
    }

    public void clearAll() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}