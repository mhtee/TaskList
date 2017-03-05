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

/**
 * Created by Muhammad on 3/4/2017.
 */

public class CreateTask extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);
    }

    public void addTaskToList(View view){
        Realm realm = Realm.getDefaultInstance();
        EditText taskName = (EditText) findViewById(R.id.taskName);
        EditText taskDescription = (EditText) findViewById(R.id.taskDescription);
        final Task task = new Task(taskName.getText().toString(), taskDescription.getText().toString());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Task t = realm.copyToRealm(task);
            }
        });
        //TaskList.taskList.add(task);
        realm.close();
        startActivity(new Intent(getBaseContext(), MainActivity.class));
    }


}
