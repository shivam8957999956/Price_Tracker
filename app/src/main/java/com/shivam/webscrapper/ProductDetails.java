package com.shivam.webscrapper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.view.View.GONE;

public class ProductDetails<noprofit> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //topdeal related content;
    ArrayList<TopDealsHelperClass> arrayListTopDeal;
    RecyclerView recyclerViewTopDeals;
    topDealAdapter DealAdapter;
    LinearLayout newMid;
    //
    TextView noPoduct;

    String LoadingUrl = "";

    public static final String TAG_MY_WORK = "mywork";
    public final static String key = "key_stats";
    public final static String URLKEY = "urlKey";
    TextView newrating;
    //main content
    static final float END_SCALE = 0.7f;
    RelativeLayout contentView;

    ImageView saveItem;


    int reverse = 0;
    RatingBar rating;
    int TIMER = 20;
    ScrollView scrollView;
    SharedPreferences sharedPreferences, notiShareference, urlShard, sharedPreferencesTrack;
    String sharedTag = "StatusSchedule", current = "", notitTag = "Notifcation", doneOrNot = "", urltag = "urlTAG", trackTag = "Track";

    ArrayList<String> arrayList;
    String URL = "https://www.amazon.in", tit = "", pri = "", img = "", rat = "";
    TextView title, price, status, midheader;
    ImageView image, timer, menu;

    ProgressBar progressBar;
    RecyclerView recyclerView;
    ProductDescriptionAdapter productDescriptionAdapter;
    ArrayList<ProductDescription> arrayListPr;
    Button scheduleP;
    LinearLayout main;
    EditText customUrl, schedulePrice, schedulePrice2;
    Button done, done2;
    Button searchBtn;
    ImageView geBTN, share;
    RelativeLayout header_io2, header_io22;

    //drawer layout
    NavigationView navigationView;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        newMid=findViewById(R.id.new_mid);
        arrayListTopDeal = new ArrayList<>();
        recyclerViewTopDeals=findViewById(R.id.recycler_deals);
        recyclerViewTopDeals.setHasFixedSize(true);
        recyclerViewTopDeals.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);
        rating = findViewById(R.id.rating);
        customUrl = findViewById(R.id.url);
        geBTN = findViewById(R.id.getBtn);
        searchBtn = findViewById(R.id.searchBtn);
        main = findViewById(R.id.main);
        progressBar = findViewById(R.id.progress_circular_bar);
        midheader = findViewById(R.id.mid_header);
        scrollView = findViewById(R.id.scrollView);
        menu = findViewById(R.id.menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        contentView = findViewById(R.id.contentView);
        share = findViewById(R.id.share);
        newrating = findViewById(R.id.newRating);


        noPoduct = findViewById(R.id.noProduct);

        done = findViewById(R.id.done);
        done2 = findViewById(R.id.done2);
        schedulePrice = findViewById(R.id.schedulePrice);
        schedulePrice2 = findViewById(R.id.schedulePrice2);
        scheduleP = findViewById(R.id.schedule);
        header_io2 = findViewById(R.id.header_io2);
        header_io22 = findViewById(R.id.header_io22);
        status = findViewById(R.id.status);
        timer = findViewById(R.id.timer);

        arrayList = new ArrayList<>();

        arrayList.add("tag2");
        arrayList.add("tag3");
        arrayList.add("tag4");
        arrayList.add("tag5");

        status.setSelected(true);


        sharedPreferences = getSharedPreferences(sharedTag, Context.MODE_PRIVATE);
        urlShard = getSharedPreferences(urltag, Context.MODE_PRIVATE);
        notiShareference = getSharedPreferences(notitTag, Context.MODE_PRIVATE);
        sharedPreferencesTrack = getSharedPreferences(trackTag, Context.MODE_PRIVATE);
        doneOrNot = notiShareference.getString("statusNoti", "");
        if (doneOrNot.isEmpty()) {
            WorkManager.getInstance().cancelAllWorkByTag(TAG_MY_WORK);
        }

        current = sharedPreferences.getString("Status", "");

        if (!current.isEmpty()) {
            status.setText(current);
            status.setVisibility(View.VISIBLE);
        }
        if (doneOrNot.equals("DONE")) {
            WorkManager.getInstance().cancelAllWorkByTag(TAG_MY_WORK);
            status.setText(current + " (completed) ");
        } else {

            status.setText(current + "    (Target Not Achieved) ");

        }


        arrayListPr = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        Intent i = getIntent();
        URL += i.getStringExtra("link");


        scheduleP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                header_io2.setVisibility(View.VISIBLE);
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedulePrice.getText().toString().isEmpty()) {
                    Toast.makeText(ProductDetails.this, "Enter valid Price in Rs..", Toast.LENGTH_SHORT).show();
                } else {

                    WorkManager.getInstance(ProductDetails.this).cancelAllWorkByTag(TAG_MY_WORK);

                    scheldule();


                    header_io2.setVisibility(GONE);
                    Toast.makeText(ProductDetails.this, "Thanks for scheduling\nMake sure notification is turned on", Toast.LENGTH_SHORT).show();
                    status.setVisibility(View.VISIBLE);
                    int te = 40;
                    String newTit = "";
                    if (te < tit.length()) {
                        newTit = tit.substring(0, 41) + ".....";
                    } else
                        newTit = tit;
                    String textN = "Reminder Scheduled for : " + newTit + " with initial prize " + pri + " ---> for price below     ₹" + schedulePrice.getText().toString();
                    status.setText(textN);

                    SharedPreferences.Editor editor1 = sharedPreferencesTrack.edit();
                    editor1.putString("Key", "");
                    editor1.apply();
                    String conts = schedulePrice.getText().toString().trim();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Status", textN);
                    editor.putString("URL", URL);
                    editor.putString("title", tit);
                    editor.putString("image", img);
                    editor.putString("target", conts);
                    editor.putString("price", pri);
                    editor.commit();
                }
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentUrl = sharedPreferences.getString("URL", "");
                URL = currentUrl;
                scheduleP.setText("Change Schedule");
                load();
            }
        });

        geBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                main.setVisibility(View.VISIBLE);
                if (customUrl.getText().toString().isEmpty()) {
                    customUrl.setError("fill this first");
                } else {
                    if (customUrl.getText().toString().indexOf("www.amazon") > -1) {
                        URL = customUrl.getText().toString().trim();
                        progressBar.setVisibility(View.VISIBLE);
                        load();
                    } else {
                        customUrl.setError("not amazon url");
                    }
                }
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductDetails.this, "Copy the Url of product return back.", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("http://www.amazon.in"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);


                startActivity(intent);

            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reverse == 0) {
                    header_io22.setVisibility(View.VISIBLE);
                    reverse = 1;
                } else {
                    header_io22.setVisibility(GONE);
                    reverse = 0;


                }


            }
        });
        done2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt = schedulePrice2.getText().toString();
                if (!rt.isEmpty()) {
                    TIMER = Integer.valueOf(rt);
                }
                header_io22.setVisibility(GONE);
            }
        });

        navigationDrawer();

        //loading Search Url
        loadingForSearchResult();


        //creating DataBase
        new DbManager(this);
        //loading deals
        leading2();

    }

    private void leading2() {
        LoadingUrl = "https://www.amazon.in/";
        new Thread(new Runnable() {
            private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36";

            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect(LoadingUrl).
                            userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
                            .cookie("auth", "token")
                            .timeout(10000).get();

//                    Element element=doc.getElementById("pageContent");
//                      Element e=doc.getElementById("gw-desktop-herotator");
//                      Elements e1=doc.getElementsByClass("a-row a-carousel-controls a-carousel-row a-carousel-has-buttons a-carousel-overlay-buttons a-carousel-rounded-buttons");
////                      Elements e3=doc.getElementsByClass("a-carousel-card");
////                      for (Element e4:e3) {
////                          Elements e5=e4.getElementsByClass("a-link-normal aok-inline-block");
////                          builder.append(e5.attr("href") + "\n---------------\n");
////                      }
//
                    Elements e7 = doc.getElementsByClass("feed-carousel-card _deals-shoveler-v2_style_dealCard__1HqkZ");
                    int i = 0;
                    for (Element e8 : e7) {

                        if (i < 7) {
                            Elements e9 = e8.getElementsByClass("a-link-normal");
                            TopDealsHelperClass topDealsHelperClass = new TopDealsHelperClass();
                            topDealsHelperClass.setImage(e9.select("img").attr("src"));
                            topDealsHelperClass.setTitle(e9.select("img").attr("alt"));
                            topDealsHelperClass.setURL("https://www.amazon.in" + e9.attr("href"));
                            Elements e10 = e8.getElementsByClass("dealPrice");

                            topDealsHelperClass.setPrice(e10.text());
                            arrayListTopDeal.add(topDealsHelperClass);
                            builder.append(topDealsHelperClass.getImage() + "\n------\n" + topDealsHelperClass.getPrice() + "\n/*/*/*/*/*/*/*//\n");
                        }
                        i++;
                    }

//                        Elements r1=doc.getElementsByClass("DealItem-module__dealItem_1ALQj-8DFdBjwNoMaYwM4P DealItem-module__withBorders_1vfm_VB561gxRAAodv2Uwa");
//                        for(Element r:r1)
//                        {
//                            builder1.append(r.html());
//                        }


                } catch (IOException e) {
//                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DealAdapter=new topDealAdapter(getApplicationContext(),arrayListTopDeal);
                        recyclerViewTopDeals.setAdapter(DealAdapter);
                        DealAdapter.notifyDataSetChanged();
                        DealAdapter.setOnItemClickListener(new topDealAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Uri uri = Uri.parse(arrayListTopDeal.get(position).getURL()); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                                startActivity(intent);
                            }
                        });
//                        midheader.setText(builder);

                    }
                });
            }
        }).start();


    }

    private void loadingForSearchResult() {
        SharedPreferences sharedPreferences = getSharedPreferences("SearchUrl", Context.MODE_PRIVATE);

        String SearchUrl = sharedPreferences.getString("SearchUrl", "");

        if (!SearchUrl.isEmpty()) {
            URL = SearchUrl;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("SearchUrl", "");
            editor.commit();
            load();

        }


    }

    private void database() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a ' at' dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());
        DbManager db = new DbManager(this);
        String res = db.addHistory(tit, URL, img, currentDateandTime);

    }


    private void scheldule() {
        String conts = schedulePrice.getText().toString().trim();
        Data data = new Data.Builder().putString(key, conts)
                .putString(URLKEY, URL).build();
        PeriodicWorkRequest request1 = new PeriodicWorkRequest.Builder(MyWorker.class, TIMER, TimeUnit.MINUTES).
                setInputData(data).addTag(TAG_MY_WORK).build();

        SharedPreferences.Editor editor = notiShareference.edit();
        editor.putString("statusNoti", "NOTDONE");
        editor.commit();

        WorkManager.getInstance().enqueue(request1);


        WorkManager.getInstance().getWorkInfoByIdLiveData(request1.getId()).observe(ProductDetails.this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                String res = workInfo.getState().toString();

                price.setText(res);

            }
        });

    }

    private void load() {
        newMid.setVisibility(GONE);
        recyclerViewTopDeals.setVisibility(GONE);
        scrollView.setVisibility(GONE);
        midheader.setVisibility(GONE);
//        final TypeWriter tw = (TypeWriter) findViewById(R.id.tv);
//        tw.setVisibility(GONE);
        tit = "";
        pri = "";
        arrayListPr.clear();
        progressBar.setVisibility(View.VISIBLE);
        noPoduct.setVisibility(GONE);
        main.setVisibility(GONE);
        new Thread(new Runnable() {
            private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36";

            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect(URL).
                            userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
                            .cookie("auth", "token")
                            .timeout(10000).get();

                    Elements main = doc.select("#imgTagWrapperId");
                    if (main != null) {
                        tit = main.select("img").attr("alt");
                        img = main.select("img").attr("src");

                        Element forPri = doc.getElementById("priceblock_ourprice");
                        if (forPri != null) {
                            pri = forPri.text();
                        }
                        if (pri.isEmpty()) {
                            Element forPri2 = doc.getElementById("priceblock_dealprice");
                            if (forPri2 != null) {
                                pri = forPri2.text();
                            }
                        }


                        Elements elements = doc.select("span.a-icon-alt");
                        for (Element t : elements) {
                            String f = t.text();
                            if (f.indexOf("stars") > 0) {
                                rat = f;
                                break;
                            }
                        }
                        arrayListPr.clear();
                        Element table = doc.getElementById("productDetails_techSpec_section_1");
                        if (table != null) {
                            Elements rows = table.select("tr");

                            for (int i = 0; i < rows.size(); i++) {
                                ProductDescription productDescription = new ProductDescription();
                                Elements th = rows.get(i).select("th");
                                Elements td = rows.get(i).select("td");
                                productDescription.setType(th.text());
                                productDescription.setValue((td.text()));
                                arrayListPr.add(productDescription);

                            }
                        }
                        builder.append(tit).append("\n");

                    } else {

                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (tit.isEmpty()) {
//                            Toast.makeText(ProductDetails.this, "No such product exist..", Toast.LENGTH_SHORT).show();
                            midheader.setText("No such product exist..");
                            midheader.setVisibility(View.VISIBLE);
                            main.setVisibility(GONE);
                            recyclerViewTopDeals.setVisibility(View.VISIBLE);
                            newMid.setVisibility(View.VISIBLE);
                        } else {
                            title.setText(builder);
                            price.setText(pri);
                            String r = rat.substring(0, rat.indexOf(' '));
                            float f = Float.valueOf(r);

                            rating.setRating(f);
                            Picasso.get().load(img).into(image);
                            if (f >= 3.5f) {
                                newrating.setText(String.valueOf(f) + "/5");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    newrating.setBackground(getDrawable(R.drawable.green));
                                }
                            } else {
                                newrating.setText(String.valueOf(f) + "/5");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    newrating.setBackground(getDrawable(R.drawable.red));
                                }
                            }
                            if (arrayListPr.size() == 0)
                                noPoduct.setVisibility(View.VISIBLE);

                            productDescriptionAdapter = new ProductDescriptionAdapter(getApplicationContext(), arrayListPr);
                            recyclerView.setAdapter(productDescriptionAdapter);
                            productDescriptionAdapter.notifyDataSetChanged();
                            main.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.VISIBLE);


                            SharedPreferences settingS = getSharedPreferences("setting", Context.MODE_PRIVATE);
                            String setting = settingS.getString("setting", "");
//                            Toast.makeText(ProductDetails.this, setting, Toast.LENGTH_SHORT).show();
                            if (setting.isEmpty() || setting.equals("allowed"))
                                database();
                        }
                        progressBar.setVisibility(GONE);
                    }
                });
            }
        }).start();
    }

    public void openaboutus(View view) {
        startActivity(new Intent(getApplicationContext(), AboutUs.class));

    }

    public void BuyNow(View view) {
//        Toast.makeText(ProductDetails.this, , Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(URL); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);
    }

    public void opentrack(View view) {
        String s = sharedPreferences.getString("title", "");
        if (!s.isEmpty()) {

            startActivity(new Intent(getApplicationContext(), Track.class));
        } else {
            Toast.makeText(this, "No item In Track.", Toast.LENGTH_SHORT).show();
        }
    }


    private void navigationDrawer() {

        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.current_tracking);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {

        drawerLayout.addDrawerListener((new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                //scale the view based on current slide off set
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
//                contentView.setScaleX(offsetScale);
//                contentView.setScaleY(offsetScale);
//                //Translate the view,accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
//                contentView.setTranslationX(xTranslation);
            }
        }));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about_us:
                startActivity(new Intent(getApplicationContext(), AboutUs.class));
                break;
            case R.id.current_tracking:
                String s = sharedPreferences.getString("title", "");
                if (!s.isEmpty()) {

                    startActivity(new Intent(getApplicationContext(), Track.class));
                } else {
                    Toast.makeText(this, "No item In Track.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.history:
                startActivity(new Intent(getApplicationContext(), searchHistory.class));
                break;
            case R.id.menu_share:
                String ShareUrl = "Hey, Try App It has amazing features\n https://play.google.com/store/apps/details?id=com.shivam.webscrapper";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, ShareUrl);
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent, "Share via");
                startActivity(sendIntent);
                break;
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
        }

        return true;
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

    public void openNew(View view) {
        startActivity(new Intent(getApplicationContext(),TrailHomeScreen.class));
    }
}
