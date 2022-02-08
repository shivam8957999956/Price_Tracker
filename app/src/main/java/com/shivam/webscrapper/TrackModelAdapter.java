package com.shivam.webscrapper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrackModelAdapter extends RecyclerView.Adapter<TrackModelAdapter.ViewHolder> {


    private TrackModelAdapter.OnItemClickListener mListener;
    String curr="";
    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(TrackModelAdapter.OnItemClickListener listener){

        mListener=listener;

    }

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<TrackModel> track;
    public TrackModelAdapter(Context mContext, ArrayList<TrackModel> track,String curr) {
        this.mContext = mContext;
        this.track = track;
        this.curr = curr;
    }



    @NonNull
    @Override
    public TrackModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        return new TrackModelAdapter.ViewHolder(view);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TrackModelAdapter.ViewHolder holder, int position) {

        String val=curr,pri=track.get(position).getPrice();

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

        if(t<t1)
        {
            holder.price.setTextColor(mContext.getResources().getColor(R.color.red));
        }else if(t>t1)
            holder.price.setTextColor(mContext.getResources().getColor(R.color.red));
        else
            holder.price.setTextColor(mContext.getResources().getColor(R.color.lighYellow));
        holder.price.setText(track.get(position).getPrice());

        holder.time.setText(track.get(position).getTime());

        String ZeroS=track.get(0).getPrice();
        int Z=0;

        String Z1=ZeroS.replace("₹","");
        Z1=Z1.replace(",","");
        Z1=Z1.replace(Z1.substring(Z1.indexOf('.')),"");
        for(int i=0;i<Z1.length();i++) {
            if(Z1.charAt(i)>='0' && Z1.charAt(i)<='9') {
                int f = (Z1.charAt(i)) - '0';
                Z = Z * 10 + f;
            }
        }
        double d=((1.0*(t-Z))/Z)*100;
        String dd=String.format("%.1f",d);
        holder.percent.setText(dd+"%");
        if(d<=0.0f)
        {

            holder.percent.setBackground(mContext.getResources().getDrawable(R.drawable.red));
        }else
            holder.percent.setBackground(mContext.getResources().getDrawable(R.drawable.green));



    }
    @Override
    public int getItemCount() {
        return track.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView price,time,percent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            price = itemView.findViewById(R.id.price);
            time= itemView.findViewById(R.id.time);
            percent= itemView.findViewById(R.id.percent);



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
