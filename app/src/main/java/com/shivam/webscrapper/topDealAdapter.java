package com.shivam.webscrapper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class topDealAdapter extends  RecyclerView.Adapter<topDealAdapter.ViewHolder> {


    private topDealAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(topDealAdapter.OnItemClickListener listener){

        mListener=listener;

    }

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<TopDealsHelperClass> amazonList;
    public topDealAdapter(Context mContext, ArrayList<TopDealsHelperClass> amazonList) {
        this.mContext = mContext;
        this.amazonList = amazonList;
    }



    @NonNull
    @Override
    public topDealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_deals, parent, false);

        return new topDealAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull topDealAdapter.ViewHolder holder, int position) {

        int []image={R.drawable.pink,R.drawable.lightblue,R.drawable.blue,R.drawable.orange};



        int t=position%4;
        holder.relativeLayout.setBackgroundResource(image[t]);

//        long l=Long.valueOf(shopInnerDetailsList.get(position).getTime());
//        String dateString = DateFormat.format("dd/MM/yyyy hh:mm:ss", new Date(l)).toString();
        holder.title.setText(amazonList.get(position).getTitle());
        holder.price.setText(amazonList.get(position).getPrice());
//        holder.url.setText(amazonList.get(position).getURL());
        Picasso.get().load(amazonList.get(position).getImage()).into(holder.imageView);

    }
    @Override
    public int getItemCount() {
        return amazonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView price,title,url;
        ImageView imageView;
        //  TextView cate;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.title);
            url = itemView.findViewById(R.id.url);
            imageView = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.price);
            relativeLayout = itemView.findViewById(R.id.mainLayout);


            url.setOnClickListener(new View.OnClickListener() {
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


}
