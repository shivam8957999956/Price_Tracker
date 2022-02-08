package com.shivam.webscrapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    String pri="";
    String img = "";
    String tit="";
    int Length=0;

    ArrayList<AmazonSearchResult>amazonSearchResults;
    ArrayList<SliderData> sliderDataArrayList;
    SliderView sliderView;
    SliderAdapter sliderAdapter;


    RecyclerView recyclerView;
    AmazonAdapter amazonAdapter;

    EditText url;
    TextView price;
    ImageView image;
    private final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36";
    private ImageView getBtn;
    private TextView result;
    String URL="https://www.amazon.in/s?k=phones&ref=nb_sb_noss_1";

    String newUrl1="&page=1&qid=1622572241&ref=sr_pg_1";
    String newUrl2="https://www.amazon.in/s?k=phones";
//    String newUrl3="https://www.amazon.in/s/query?k=mobile&page={}&qid=1604103880&ref=sr_pg_{}'.format(page_number, page_number)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        sharedPreferences=getSharedPreferences("urlTAG", Context.MODE_PRIVATE);
//
//        recyclerView=findViewById(R.id.list);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//         sliderView = findViewById(R.id.slider);
//        sliderDataArrayList=new ArrayList<>();
//        amazonSearchResults=new ArrayList<>();
//        url=findViewById(R.id.url);
//        image=findViewById(R.id.image);
//        price=findViewById(R.id.price);
//
//

//        result = (TextView) findViewById(R.id.result);
//        getBtn = (ImageView) findViewById(R.id.getBtn);

////        load();
////
//        getBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                getWebsite();
//                Toast.makeText(MainActivity.this, "Yo", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

//    private void load() {
//        new Thread(new Runnable() {
//            private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36";
//            String urlMain="https://www.amazon.in/";
//
//
//            @Override
//            public void run() {
//                final StringBuilder builder = new StringBuilder();
//
//                try {
//                    Document doc = Jsoup.connect(urlMain).
//                            userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
//                            .cookie("auth", "token")
//                            .timeout(10000).get();
//
//                    Elements main=doc.getElementsByClass("a-link-normal aok-inline-block");
//                    String h="";
//                    for (Element t:main){
//
////                        sliderDataArrayList.add(new SliderData(f  ));
//                        h=t.html()+"\n-------\n";
//                    }
//                    builder.append(h).append("\n");
//
//
//                } catch (IOException e) {
//                    builder.append("Error : ").append(e.getMessage()).append("\n");
//                }
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        result.setText(builder);
//                        sliderAdapter=new SliderAdapter(MainActivity.this, sliderDataArrayList);
//                        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//
//                        // below method is used to
//                        // setadapter to sliderview.
//                        sliderView.setSliderAdapter(sliderAdapter);
//
//                        // below method is use to set
//                        // scroll time in seconds.
//                        sliderView.setScrollTimeInSec(3);
//
//                        // to set it scrollable automatically
//                        // we use below method.
//                        sliderView.setAutoCycle(true);
//
//
//                        sliderView.startAutoCycle();
//                    }
//                });
//            }
//        }).start();
//    }
//    private void getWebsite() {
//
//        String t=url.getText().toString().trim();
//        if(!t.isEmpty())
//            URL="https://www.amazon.in/s?k="+t+"&ref=nb_sb_noss_1";
//        amazonSearchResults.clear();
//
//
//        new Thread(new Runnable() {
//            private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36";
//
//
//            @Override
//            public void run() {
//                final StringBuilder builder = new StringBuilder();
//
//                try {
//                    Document doc = Jsoup.connect(URL).
//                            userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
//                            .cookie("auth", "token")
//                            .timeout(10000).get();
//
//                    Elements img=doc.getElementsByClass("a-link-normal s-no-outline");
//                    for (Element d:img){
//                        Elements f=d.getElementsByClass("s-image");
//
//                        AmazonSearchResult amazonSearchResult=new AmazonSearchResult();
//                        amazonSearchResult.setImage(f.attr("src"));
//                        amazonSearchResult.setTitle(f.attr("alt"));
//                        amazonSearchResult.setLink(d.attr("href"));
//                        amazonSearchResults.add(amazonSearchResult);
//                        Length++;
//                    }
//                    int k=0;
////                    Elements title=doc.getElementsByClass("a-size-medium a-color-base a-text-normal");
////                    for(Element t:title){
//////                        if(k<Length)
////                        amazonSearchResults.get(k++).setTitle(t.text());
////
////                    }
////                    if(k==0) {
////                        Elements title1 = doc.getElementsByClass("a-size-base-plus a-color-base a-text-normal");
////                        for (Element t : title1) {
//////                            if (k < Length)
////                                amazonSearchResults.get(k++).setTitle(t.text());
////
////                        }
////                    }
//
//
//                    int k1=0;
//                    Elements price=doc.getElementsByClass("a-price-whole");
//                    for(Element t:price){
//                        if(k1<Length)
//                        amazonSearchResults.get(k1++).setPrice(t.text());
//                    }
//
//
//
//
//
//
//                    builder.append(amazonSearchResults.get(0).getTitle()).append("\n");
//                } catch (IOException e) {
//                    builder.append("Error : ").append(e.getMessage()).append("\n");
//                }
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        result.setText("Total results: "+ String.valueOf(Length));
//                        amazonAdapter=new AmazonAdapter(getApplicationContext(),amazonSearchResults);
//                        recyclerView.setAdapter(amazonAdapter);
//                        amazonAdapter.notifyDataSetChanged();
//                        amazonAdapter.setOnItemClickListener(new AmazonAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(int position) {
//                                Intent intent=new Intent(getApplicationContext(),ProductDetails.class);
//                                intent.putExtra("link",amazonSearchResults.get(position).getLink());
//                                SharedPreferences.Editor editor=sharedPreferences.edit();
//                                editor.putString("url",amazonSearchResults.get(position).getLink());
//                                editor.commit();
//                                startActivity(intent);
//                            }
//                        });
//
//
////                        price.setText(pri);
////                        Glide.with(MainActivity.this).load(img).into(image);
//
//
//                    }
//                });
//            }
//        }).start();
//    }
}