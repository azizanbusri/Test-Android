package com.test.test.testprojand;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Utils {
    public static void SetActionBarDetail(Context context, String strTitle) {

        ((AppCompatActivity)context).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.abs_layout, null);
        TextView textView = view.findViewById(R.id.tvTitle);
        textView.setText(strTitle);
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        textView.setLayoutParams(params);
        view.setLayoutParams(params);
        ((AppCompatActivity)context).getSupportActionBar().setCustomView(view);
    }

    public static void ChangeActivityWithFinish(Context context, Class<?> activityClass) {
        context.startActivity(new Intent(context, activityClass));
        ((Activity) context).finish();
    }

}
