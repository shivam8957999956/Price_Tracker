package com.shivam.webscrapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.shadow.ShadowRenderer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Track extends AppCompatActivity {
    TextView text,targetPrice;
    ArrayList<TrackModel> arrayList;
    SharedPreferences sharedPreferences,urlPref;
    String URL="";
    RecyclerView recyclerView;
    String urlTag="StatusSchedule";
    TextView title;
    ImageView image;

    TrackModelAdapter trackModelAdapter;
    String tit="",pri="",img="",rat="",target="",currVal="";
    TextView targetP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        title=findViewById(R.id.title);
        image=findViewById(R.id.image);
//        targetP=findViewById(R.id.target);
        targetPrice=findViewById(R.id.targetPrice);


        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences=getApplicationContext().getSharedPreferences("Track", Context.MODE_PRIVATE);



        String st=sharedPreferences.getString("Key","");
        currVal=sharedPreferences.getString("CurrVal","");
        if(st.isEmpty()){
            arrayList=new ArrayList<>();
        }
        else{
            Gson gsonk=new Gson();
            Type type=new TypeToken<ArrayList<TrackModel>>(){}.getType();
            ArrayList<TrackModel> arrayList1=gsonk.fromJson(st,type);
            arrayList=arrayList1;
            Collections.reverse(arrayList);
        }
        urlPref=getApplicationContext().getSharedPreferences(urlTag, Context.MODE_PRIVATE);
        URL=urlPref.getString("URL","");
        tit=urlPref.getString("title","");
        img=urlPref.getString("image","");
        pri=urlPref.getString("price","");
        target=urlPref.getString("target","");
        if(!URL.isEmpty() && !tit.isEmpty() && !img.isEmpty() && !pri.isEmpty()){
            title.setText(tit);

            Picasso.get().load(img).into(image);
            trackModelAdapter=new TrackModelAdapter(getApplicationContext(),arrayList,currVal);
            recyclerView.setAdapter(trackModelAdapter);
            trackModelAdapter.notifyDataSetChanged();
            targetPrice.setText("Target Price ₹"+currVal);
        }

    }

    public void BuyNow(View view) {
//        Toast.makeText(ProductDetails.this, , Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(URL); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);
    }
    public void shareToOthers(View view) {

        String ShareUrl = "Hey, Check out this product at amazon \n " + URL;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, ShareUrl);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent, "Share via");
        startActivity(sendIntent);
    }
}