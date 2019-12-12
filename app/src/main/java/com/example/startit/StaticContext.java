package com.example.startit;
import android.app.Application;
import android.content.Context;

public class StaticContext extends Application{
    private static Context context;
    @Override
    public void onCreate(){
        super.onCreate();
        StaticContext.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return StaticContext.context;
    }
}