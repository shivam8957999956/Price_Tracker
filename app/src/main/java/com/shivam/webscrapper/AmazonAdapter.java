package com.shivam.webscrapper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class AmazonAdapter extends  RecyclerView.Adapter<AmazonAdapter.ViewHolder> {


    private AmazonAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(AmazonAdapter.OnItemClickListener listener){

        mListener=listener;

    }

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<AmazonSearchResult> amazonList;
    public AmazonAdapter(Context mContext, ArrayList<AmazonSearchResult> amazonList) {
        this.mContext = mContext;
        this.amazonList = amazonList;
    }



    @NonNull
    @Override
    public AmazonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_view, parent, false);

        return new AmazonAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AmazonAdapter.ViewHolder holder, int position) {

//        long l=Long.valueOf(shopInnerDetailsList.get(position).getTime());
//        String dateString = DateFormat.format("dd/MM/yyyy hh:mm:ss", new Date(l)).toString();
        holder.title.setText(amazonList.get(position).getTitle());
        holder.price.setText("Rs: "+amazonList.get(position).getPrice());
        Picasso.get().load(amazonList.get(position).getImage()).into(holder.image);
    }
    @Override
    public int getItemCount() {
        return amazonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView title;
        TextView price;
        ImageView image;
        //  TextView cate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.result);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);
            //   cate=itemView.findViewById(R.id.category);


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
