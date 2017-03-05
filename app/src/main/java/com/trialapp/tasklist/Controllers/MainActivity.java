package com.trialapp.tasklist.Controllers;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.trialapp.tasklist.Models.Task;
import com.trialapp.tasklist.Models.TaskList;
import com.trialapp.tasklist.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createTaskActivity = new Intent(MainActivity.this, CreateTask.class);
                startActivity(createTaskActivity);
            }
        });
        ArrayList<Task> taskList = new ArrayList<>();
        RealmResults<Task> tasks = realm.where(Task.class).findAll();

        for (Task t: tasks){
            taskList.add(t);
        }

        TaskList.taskList = taskList;

        ListView taskView = (ListView) findViewById(R.id.taskListView);
        final ArrayAdapter<Task> arrayAdapter = new ArrayAdapter<Task>(this,
                android.R.layout.simple_list_item_1, taskList);
        taskView.setAdapter(arrayAdapter);

        taskView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("View/Modify")
                        .setMessage(TaskList.taskList.get(pos).getTaskDescription())
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getBaseContext(), EditTask.class);
                                i.putExtra("position", pos);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Task temp = TaskList.taskList.get(pos);

                                final RealmResults<Task> x = realm.where(Task.class).equalTo("taskName",
                                        temp.getTaskName()).equalTo("taskDescription",
                                        temp.getTaskDescription()).findAll();
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        x.deleteFirstFromRealm();
                                    }
                                });
                                TaskList.taskList.remove(pos);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        });
    }
    @Override
    protected void onDestroy(){
        realm.close();
        super.onDestroy();
    }

}
