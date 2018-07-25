package com.asyncTasks.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.asyncTasks.R;

public class MultipleAsyncTaskActivity extends AppCompatActivity {

    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;
    private StartMyAsyncTask startMyAsyncTask1, startMyAsyncTask2, startMyAsyncTask3, startMyAsyncTask4, startMyAsyncTask5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_async_task);

        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar3 = findViewById(R.id.progressBar3);
        progressBar4 = findViewById(R.id.progressBar4);
        progressBar5 = findViewById(R.id.progressBar5);

        Button btnStartAsyncTask = findViewById(R.id.btnStartAsyncTask);

        btnStartAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyAsyncTask1 = new StartMyAsyncTask(progressBar1);
                startMyAsyncTask1.execute();

                startMyAsyncTask2 = new StartMyAsyncTask(progressBar2);
                startMyAsyncTask2.execute();

                startMyAsyncTask3 = new StartMyAsyncTask(progressBar3);
                startMyAsyncTask3.execute();

                startMyAsyncTask4 = new StartMyAsyncTask(progressBar4);
                startMyAsyncTask4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                startMyAsyncTask5 = new StartMyAsyncTask(progressBar5);
                startMyAsyncTask5.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        Button btnGoToNextAsyncTask = findViewById(R.id.btnGoToNextAsyncTask);
        btnGoToNextAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MultipleAsyncTaskActivity.this, NextAsyncTaskExample.class));
            }
        });

    }


    class StartMyAsyncTask extends AsyncTask<Void, Integer, Void> {
        ProgressBar myProgressBar;

        public StartMyAsyncTask(ProgressBar target) {
            myProgressBar = target;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 100; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            myProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
