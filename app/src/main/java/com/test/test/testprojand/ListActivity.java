package com.test.test.testprojand;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.test.test.testprojand.RecyclerViewPackage.ModelRecyclerView;
import com.test.test.testprojand.RecyclerViewPackage.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.test.test.testprojand.CallApi.ThisPopularSearch;

public class ListActivity extends AppCompatActivity {

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    ArrayList<ModelRecyclerView> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Utils.SetActionBarDetail(ListActivity.this, "Articles " + GlobData.strArticles.substring(0, 1).toUpperCase() + GlobData.strArticles.substring(1) );

        SetRecyclerSetting();

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (!recyclerView.canScrollVertically(1)){
                    ThisPopularSearch(GlobData.strArticles, ListActivity.this, 30, ListActivity.this);
                }
            }
        });

    }

    public void SetRecyclerSetting() {
        recyclerView = findViewById(R.id.recycView);

        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        layoutManager = new LinearLayoutManager(ListActivity.this);
        list = GlobData.modelRecyclerViews;
        onNotifyDataChange(GlobData.modelRecyclerViews);
    }

    public void onNotifyDataChange(List<ModelRecyclerView> thisList) {
        System.out.println("call Me");
        recyclerViewAdapter = new RecyclerViewAdapter(ListActivity.this, thisList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onBackPressed() {
        Utils.ChangeActivityWithFinish(ListActivity.this, MainActivity.class);
    }



}
