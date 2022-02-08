package com.shivam.webscrapper;

import android.app.AppOpsManager;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {


    private SearchHistoryAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(SearchHistoryAdapter.OnItemClickListener listener) {

        mListener = listener;

    }

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<SearchHistoryHelperClass> amazonList;
    int last=-1;
    public SearchHistoryAdapter(Context mContext, ArrayList<SearchHistoryHelperClass> amazonList) {
        this.mContext = mContext;
        this.amazonList = amazonList;
    }


    @NonNull
    @Override
    public SearchHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_layout_cardview, parent, false);

        return new SearchHistoryAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryAdapter.ViewHolder holder, int position) {
        if(holder.getAdapterPosition()>last) {
            Animation animation= AnimationUtils.loadAnimation(mContext,R.anim.slide);
            holder.itemView.startAnimation(animation);

            String dating = amazonList.get(position).getTime();
            String timing = dating.substring(0, 11);
            String m = dating.substring(19, 21);
            String d = dating.substring(16, 18);
            String monthing = "MOn";
            if (m.equals("01"))
                monthing = "Jan";
            if (m.equals("02"))
                monthing = "feb";
            if (m.equals("03"))
                monthing = "Mar";
            if (m.equals("04"))
                monthing = "Apr";
            if (m.equals("05"))
                monthing = "May";
            if (m.equals("06"))
                monthing = "Jun";
            if (m.equals("07"))
                monthing = "Jul";
            if (m.equals("08"))
                monthing = "Aug";
            if (m.equals("09"))
                monthing = "Sep";
            if (m.equals("10"))
                monthing = "Oct";
            if (m.equals("11"))
                monthing = "Nov";
            if (m.equals("12"))
                monthing = "Dec";


            holder.title.setText(amazonList.get(position).getTitle());
            holder.excat.setText(timing);
            holder.date.setText(d);
            holder.month.setText(monthing);
            holder.excat.setText(timing);
//        holder.date.setText(amazonList.get(position).getTime());
            holder.url.setText(amazonList.get(position).getUrl());
            Picasso.get().load(amazonList.get(position).getImage().toString()).into(holder.imageView);
            last=holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return amazonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView title, date, url, month, excat;
        ImageView imageView;
        //  TextView cate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.title);
            month = itemView.findViewById(R.id.month);
            excat = itemView.findViewById(R.id.timeEXact);
            date = itemView.findViewById(R.id.date);
            url = itemView.findViewById(R.id.url);
            imageView = itemView.findViewById(R.id.image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }

                }
            });

        }

    }

    public void remove(int position) {
        DbManager dp = new DbManager(mContext);
        dp.deleteRow(amazonList.get(position).getId());
        amazonList.remove(position);
        notifyItemRemoved(position);

    }

    public void restore(SearchHistoryHelperClass item, int posi) {
        amazonList.add(posi, item);
        notifyItemInserted(posi);

    }

}
