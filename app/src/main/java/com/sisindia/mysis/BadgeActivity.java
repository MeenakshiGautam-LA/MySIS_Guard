package com.sisindia.mysis;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Adapter.BadgesAdapters;
import com.sisindia.mysis.listener.RecyclerItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class BadgeActivity extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener {

   // BadgesAdapter badgesAdapter;
    BadgesAdapters badgesAdapters;
    List<String> list = new ArrayList<>();
    TextView toolbar_title;
    LinearLayout toolbar_title_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.badge_collpase_sreen);

        setContentView(R.layout.badge_collpase_sreen);
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.setExpanded(true);
        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        CollapsingToolbarLayout ctl = findViewById(R.id.toolbar_layout);
        ctl.setTitle("60+ Badges");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ctl.setCollapsedTitleTextColor(getColor(R.color.purple));
        }


        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title_linear = findViewById(R.id.toolbar_title_linear);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    toolbar_title_linear.setVisibility(View.GONE);
                    Log.e("tag>>>", "scroll range is zero.... ");
                } else if (isShow) {
                    isShow = false;
                    toolbar_title_linear.setVisibility(View.VISIBLE);
                    Log.e("tag>>>", "scroll range is not zero.... ");
                }
            }
        });
        list.add("ankit");
        list.add("manoj");
        list.add("harish");
        list.add("vikash");
        list.add("lokendra");
        list.add("prabhat");
        list.add("sunil");
        list.add("100080");
        list.add("dhiraj");
        list.add("abcd");
        list.add("pqrs");
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        badgesAdapters = new BadgesAdapters(this, list);
        recyclerView.setAdapter(badgesAdapters);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
        badgesAdapters.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.e("log>>>>", "position of recycler view...     " + position);
        Intent intent = new Intent(BadgeActivity.this, PunctualActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }


}
