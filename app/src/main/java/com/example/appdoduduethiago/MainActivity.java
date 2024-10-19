package com.example.appdoduduethiago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdoduduethiago.HistoryAdapter;
import com.example.appdoduduethiago.Tarefa;
import com.example.appdoduduethiago.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Tarefa> listaTarefa;
    private HistoryAdapter historico;
    private RecyclerView recyclerView;
    private EditText titleInput, descriptionInput;
    private Button addButton;

    private static final String PREFS_NAME = "task_prefs";
    private static final String TASK_LIST_KEY = "task_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaTarefa = loadTasks();
        historico = new HistoryAdapter(listaTarefa, this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(historico);

        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String description = descriptionInput.getText().toString();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                Tarefa task = new Tarefa(title, description, false, currentDate);

                listaTarefa.add(task);
                historico.notifyDataSetChanged();
                saveTasks();

                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listaTarefa);
        editor.putString(TASK_LIST_KEY, json);
        for (int i = 0; i < listaTarefa.size(); i++) {
            editor.putBoolean(
                    "task_" + i + "_completed", listaTarefa.get(i).isCompleto()
            );
        }
        editor.apply();
    }

    private ArrayList<Tarefa> loadTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(TASK_LIST_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Tarefa>>() {}.getType();
        ArrayList<Tarefa> taskList = gson.fromJson(json, type);

        if (listaTarefa == null) {
            listaTarefa = new ArrayList<>();
        }
        for (int i = 0; i < taskList.size(); i++) {
            boolean isCompleto = sharedPreferences.getBoolean(
                    "task_" + i + "_completed", false
            );

            taskList.get(i).setCompletado(isCompleto);
        }
        return taskList;
    }
}