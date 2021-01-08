package com.sisindia.mysis;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Adapter.LanguageAdapter;

import java.util.ArrayList;
import java.util.List;


public class LanguageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<String> langList=new ArrayList<String>();
     LanguageAdapter languageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        recyclerView=findViewById(R.id.language_rv);

        langList.add("English");
        langList.add("Hindi");
        langList.add("Marathi");
        //langList.add();
          RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        languageAdapter = new LanguageAdapter(this, langList);
        recyclerView.setAdapter(languageAdapter);

        //faq_recycle_view.addOnItemTouchListener(new RecyclerItemClickListener(this, this));

        languageAdapter.notifyDataSetChanged();



    }
}
