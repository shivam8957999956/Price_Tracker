package com.shivam.webscrapper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringJoiner;

public class MyWorker extends Worker {
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    public final static String key_return="return";
    String tit,value="failed";
    ArrayList<TrackModel> arrayList;


    @NonNull
    @Override
    public Result doWork() {
        Data data=getInputData();
        String val=data.getString(ProductDetails.key);
//        noti("hey ",val);
        String URL=data.getString(ProductDetails.URLKEY);
        data(URL,val);
//        noti("Hurry Up your price hs dropped below " + val + " At amazon.in", "pri",URL);
        return Result.success();
    }

    private Result data(String URL, String val) {
        final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36";
        final StringBuilder builder = new StringBuilder();
//        String URL="https://www.amazon.in";
        String t7="";
        try {


            SharedPreferences sharedPreferences;
            sharedPreferences=getApplicationContext().getSharedPreferences("Track",Context.MODE_PRIVATE);
            String st=sharedPreferences.getString("Key","");
            if(st.isEmpty()){
                arrayList=new ArrayList<>();
            }
            else{
                Gson gsonk=new Gson();
                Type type=new TypeToken<ArrayList<TrackModel>>(){}.getType();
                ArrayList<TrackModel> arrayList1=gsonk.fromJson(st,type);
                arrayList=arrayList1;
            }

            Document doc = Jsoup.connect(URL).
                    userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
                    .cookie("auth", "token")
                    .timeout(10000).get();
            tit=doc.title();
            String pri="NOT FETCHED";

//
            Element forPri=doc.getElementById("priceblock_ourprice");
            if(forPri!=null) {
                pri=forPri.text();}
            if(pri.equals("NOT FETCHED")){
                Element forPri2=doc.getElementById("priceblock_dealprice");
                if(forPri2!=null) {
                    pri=forPri2.text();
                }
            }

            int t=0;
            String fs1=pri.replace("₹","");
            fs1=fs1.replace(",","");
            fs1=fs1.replace(fs1.substring(fs1.indexOf('.')),"");
            for(int i=0;i<fs1.length();i++) {
                if(fs1.charAt(i)>='0' && fs1.charAt(i)<='9') {
                    int f = (fs1.charAt(i)) - '0';
                    t = t * 10 + f;
                }
            }
            int t1=0;
            for(int i=0;i<val.length();i++) {
                if(val.charAt(i)>='0' && val.charAt(i)<='9') {
                    int f = (val.charAt(i)) - '0';
                    t1 = t1 * 10 + f;
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a ' at' dd/MM/yyyy");
            String currentDateandTime = sdf.format(new Date());
            arrayList.add(new TrackModel(currentDateandTime,pri));
            if(t1>t) {
                noti("Hurry Up your price has dropped below ₹ " + val + " At amazon.in", "Now Available At  "+pri,URL);
            }else{
//                noti("Hurry Up your price hs dropped below " + val + " At amazon.in", "pri",URL);
            }


            Gson gson=new Gson();
            String jsonS=gson.toJson(arrayList);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("Key",jsonS);
            editor.putString("CurrVal",val);
            editor.apply();


        } catch (IOException e) {
            builder.append("Error : ").append(e.getMessage()).append("\n");
//            noti(builder + val + " At amazon.in", "pri",URL);

            Toast.makeText(getApplicationContext(), builder, Toast.LENGTH_SHORT).show();
        }
        t7= "failed";
        return Result.success();
    }

    private void noti(String task,String desc,String url){
        SharedPreferences sharedPreferences;
        sharedPreferences=getApplicationContext().getSharedPreferences("Notifcation",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("statusNoti","DONE");
        editor.commit();
        NotificationManager manager= (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse(url));
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("simple", "simple", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"simple").
                setContentTitle(task)
                .setContentText(desc)
                .setContentIntent(pi)
                .setSmallIcon(R.drawable.logobb);
        manager.notify(1,builder.build());
    }
}
