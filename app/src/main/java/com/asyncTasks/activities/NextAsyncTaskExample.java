package com.asyncTasks.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asyncTasks.R;
import com.asyncTasks.interfaces.TaskListener;

import org.w3c.dom.Text;

public class NextAsyncTaskExample extends AppCompatActivity implements TaskListener, View.OnClickListener {

    private ProgressDialog progressDialog;
    private TextView txtUpdate;
    private String strButtonEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_async_task_example);

        txtUpdate = findViewById(R.id.txtUpdate);
        Button btnNextAsyncTask = findViewById(R.id.btnNextAsyncTask);
        Button btnLock = findViewById(R.id.btnLock);
        Button btnUnLock = findViewById(R.id.btnUnLock);
        btnNextAsyncTask.setOnClickListener(this);
        btnLock.setOnClickListener(this);
        btnUnLock.setOnClickListener(this);
    }

    @Override
    public void onTaskStarted() {
        progressDialog = ProgressDialog.show(NextAsyncTaskExample.this, "Loading", "Please wait a moment!");
        txtUpdate.setText("Executing onPreExecute()");
    }

    @Override
    public void onTaskFinished(String result) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        txtUpdate.setText("Task Completed");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNextAsyncTask) {
            new StartAsyncTaskTwice(this).execute();
        } else if (v.getId() == R.id.btnLock) {
            strButtonEvent = "btnLock";
            showDialogForLockAndUnLock();
        } else if (v.getId() == R.id.btnUnLock) {
            strButtonEvent = "btnUnLock";
            showDialogForLockAndUnLock();
        }
    }

    class StartAsyncTaskTwice extends AsyncTask<String, Integer, String> {
        private final TaskListener listener;

        public StartAsyncTaskTwice(TaskListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            listener.onTaskStarted();
        }

        @Override
        protected String doInBackground(String... params) {
            txtUpdate.setText("Executing doInBackground()");

            for (int i = 1; i <= 10; i++) {
                Log.e("StartAsyncTaskTwice", "AsyncTask is working: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "All Done!";
        }

        @Override
        protected void onPostExecute(String result) {
            listener.onTaskFinished(result);
            Log.e("StartAsyncTaskTwice ", " onPostExecute");
        }
    }

    private void lockScreenOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void unlockScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public void showDialogForLockAndUnLock() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure to Lock The Screen Orientation")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (strButtonEvent.equalsIgnoreCase("btnLock")) {
                            lockScreenOrientation();
                        } else if (strButtonEvent.equalsIgnoreCase("btnUnLock")) {
                            unlockScreenOrientation();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

}
