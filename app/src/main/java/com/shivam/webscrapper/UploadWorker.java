package com.shivam.webscrapper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class UploadWorker extends ListenableWorker {
    private static final String TAG = "UploadWorker";
    public static final String ARG_PHOTO_KEY = "photo-key";

    private String mPhotoKey;

    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public UploadWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        mPhotoKey = workerParams.getInputData().getString(ARG_PHOTO_KEY);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36";
        final StringBuilder builder = new StringBuilder();
        String URL="https://www.amazon.in";
        try {
            Document doc = Jsoup.connect(URL).
                    userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
                    .cookie("auth", "token")
                    .timeout(10000).get();

//            Elements main=doc.select("#imgTagWrapperId");


        } catch (IOException e) {
            builder.append("Error : ").append(e.getMessage()).append("\n");
        }
        return null;
    }

//
}