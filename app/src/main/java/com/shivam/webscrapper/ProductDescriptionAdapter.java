package com.shivam.webscrapper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductDescriptionAdapter extends  RecyclerView.Adapter<ProductDescriptionAdapter.ViewHolder> {


    private ProductDescriptionAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(ProductDescriptionAdapter.OnItemClickListener listener){

        mListener=listener;

    }

    private static final String tag = "RecyclerView";

    int lastPosition =-1;

    private Context mContext;
    private ArrayList<ProductDescription> amazonList;
    public ProductDescriptionAdapter(Context mContext, ArrayList<ProductDescription> amazonList) {
        this.mContext = mContext;
        this.amazonList = amazonList;
    }



    @NonNull
    @Override
    public ProductDescriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_details, parent, false);

        return new ProductDescriptionAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductDescriptionAdapter.ViewHolder holder, int position) {

//        long l=Long.valueOf(shopInnerDetailsList.get(position).getTime());
//        String dateString = DateFormat.format("dd/MM/yyyy hh:mm:ss", new Date(l)).toString();
        if(holder.getAdapterPosition()>lastPosition) {
            Animation animation= AnimationUtils.loadAnimation(mContext,R.anim.slide);
            holder.itemView.startAnimation(animation);
            holder.type.setText(amazonList.get(position).getType());
            holder.value.setText(amazonList.get(position).getValue());
            lastPosition=holder.getAdapterPosition();
        }
    }
    @Override
    public int getItemCount() {
        return amazonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView value;
        TextView type;
        //  TextView cate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            value = itemView.findViewById(R.id.value);
            type = itemView.findViewById(R.id.type);


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


}
