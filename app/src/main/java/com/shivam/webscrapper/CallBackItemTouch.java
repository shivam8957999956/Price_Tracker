package com.shivam.webscrapper;

import androidx.recyclerview.widget.RecyclerView;

public interface CallBackItemTouch {
    void itemTouchOnMove(int oldPosition,int newPosition);
    void onSwipped(RecyclerView.ViewHolder viewHolder,int position);
}
