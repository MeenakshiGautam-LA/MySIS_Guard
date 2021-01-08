package com.sisindia.mysis.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewClickListner implements RecyclerView.OnItemTouchListener {

    private RecyclerItemClickListener.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongPress(View childView, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerViewClickListner(Context context, RecyclerItemClickListener.OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(view, view.getChildAdapterPosition (childView) /*(onGetItemId)view.getAdapter()).getItemIdEvent(view.getChildAdapterPosition(childView))*/);
        }
        else if(childView != null && mListener != null && mGestureDetector.onTouchEvent(e)){
            mListener.onItemLongPress(view, view.getChildAdapterPosition (childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
