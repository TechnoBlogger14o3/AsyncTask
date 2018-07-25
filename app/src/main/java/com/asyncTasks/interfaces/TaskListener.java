package com.asyncTasks.interfaces;

public interface TaskListener {
    void onTaskStarted();
 
    void onTaskFinished(String result);
}