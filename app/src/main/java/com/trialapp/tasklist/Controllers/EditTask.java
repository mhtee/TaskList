package com.trialapp.tasklist.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.trialapp.tasklist.Models.Task;
import com.trialapp.tasklist.Models.TaskList;
import com.trialapp.tasklist.R;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Muhammad on 3/4/2017.
 */

public class EditTask extends Activity {

    Intent receiver;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        receiver = getIntent();
        position = receiver.getIntExtra("position", 0);
        EditText tName = (EditText) findViewById(R.id.taskName);
        EditText tDescription = (EditText) findViewById(R.id.taskDescription);

        tName.setText(TaskList.taskList.get(position).getTaskName());
        tDescription.setText(TaskList.taskList.get(position).getTaskDescription());

    }

    public void editTask(View view){
        Realm realm = Realm.getDefaultInstance();

        Task temp = TaskList.taskList.get(position);
        final Task x = realm.where(Task.class).equalTo("taskName", temp.getTaskName())
                .equalTo("taskDescription", temp.getTaskDescription()).findFirst();

        EditText taskName = (EditText) findViewById(R.id.taskName);
        EditText taskDescription = (EditText) findViewById(R.id.taskDescription);
        final String tName = taskName.getText().toString();
        final String tDes = taskDescription.getText().toString();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                x.changeTaskName(tName);
                x.changeTaskDescription(tDes);
            }
        });
        realm.close();
        //TaskList.taskList.get(position).changeTaskName(taskName.getText().toString());
        //TaskList.taskList.get(position).changeTaskDescription(taskDescription.getText().toString());
        startActivity(new Intent(getBaseContext(), MainActivity.class));
    }


}
