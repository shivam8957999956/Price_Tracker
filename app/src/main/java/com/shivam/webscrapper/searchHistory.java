package com.shivam.webscrapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class searchHistory extends AppCompatActivity {

    ArrayList<SearchHistoryHelperClass> arrayList;
    RecyclerView recyclerView;
    SearchHistoryAdapter searchHistoryAdapter;

    ImageView imageView;
    ArrayList<Integer> l;
    ListView listView;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);
        listView=findViewById(R.id.list);
        recyclerView=findViewById(R.id.recycler_view);
        imageView=findViewById(R.id.imageBIn);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<>();
        l=new ArrayList<Integer>();
        db=new DbManager(this);
        Cursor data=db.populateListview();
        ArrayList<String> theList =new ArrayList<>();

        if (data.getCount()==0){
            return;
        }
        else
        {
            arrayList.clear();
            while(data.moveToNext()){
                SearchHistoryHelperClass searchHistoryHelperClass=new SearchHistoryHelperClass();
                theList.add(data.getString(1));
                searchHistoryHelperClass.setTitle(data.getString(1));
                searchHistoryHelperClass.setUrl(data.getString(2));
                searchHistoryHelperClass.setImage(data.getString(3));
                searchHistoryHelperClass.setTime(data.getString(4));
                searchHistoryHelperClass.setId(data.getInt(0));
                arrayList.add(searchHistoryHelperClass);
                l.add(data.getInt(0));
            }
            searchHistoryAdapter=new SearchHistoryAdapter(getApplicationContext(),arrayList);
            recyclerView.setAdapter(searchHistoryAdapter);
            searchHistoryAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            Collections.reverse(arrayList);
            Collections.reverse(l);
            searchHistoryAdapter.setOnItemClickListener(new SearchHistoryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    String Url=arrayList.get(position).getUrl();
                    SharedPreferences sharedPreferences=getSharedPreferences("SearchUrl", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("SearchUrl",Url);
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(),ProductDetails.class));
                }
            });

            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    Toast.makeText(searchHistory.this, "on Move", Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    //Remove swiped item from list and notify the RecyclerView
                    int position = viewHolder.getAdapterPosition();
//                    Toast.makeText(searchHistory.this, String.valueOf(l.get(position)), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(searchHistory.this,  db.deleteRow(l.get(position)), Toast.LENGTH_SHORT).show();
                    String g=db.deleteRow(l.get(position));
                    arrayList.remove(position);
                    l.remove(position);

                    searchHistoryAdapter.notifyDataSetChanged();
                    LayoutInflater inflater = getLayoutInflater();
                    View toastLayout = inflater.inflate(R.layout.content_custom_toast, (ViewGroup) findViewById(R.id.llCustom));
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(toastLayout);
                    toast.setGravity(Gravity.BOTTOM,0,0);
                    toast.show();

                }
            };

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);

        }

    }

    public void delete(View view) {
        imageView.setVisibility(View.VISIBLE);
        arrayList.clear();
        searchHistoryAdapter.notifyDataSetChanged();
        Toast.makeText(this, db.delete(), Toast.LENGTH_SHORT).show();
    }
}