package com.example.madinandroid;

import static androidx.recyclerview.widget.ItemTouchHelper.*;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.locks.ReentrantLock;

public class ContactSwiper extends Callback {

    private final float clamp = 200;

    // define basic methods
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // setting the movement direction
        // left return value indicates 'DRAG' direction
        // right return value indicates 'SWIPE' direction
        return makeMovementFlags(0, RIGHT);
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }




    // return the foreground view
    public View getView(RecyclerView.ViewHolder viewHolder){
        View view = ((RecyclerContactAdapter.ContactViewHolder)viewHolder).getView();
        return view;
    }


    // main methods
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        getDefaultUIUtil().clearView(getView(viewHolder));
    }
    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null){
            getDefaultUIUtil().onSelected(getView(viewHolder));
        }
    }

    // isCurrentlyActive : True - being controlled by user / False - return to original state
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // it will return the top cardview
        View view = getView(viewHolder);
        Boolean isClamp = ((RecyclerContactAdapter.ContactViewHolder)viewHolder).getIsClamp();
        float x = 0f;
        Boolean active = isCurrentlyActive;

        if(!isClamp){
            if (!isCurrentlyActive && dX > clamp) { // if user stop the swipe and if it over the threshold position
                x = clamp;
                active = true;
                ((RecyclerContactAdapter.ContactViewHolder)viewHolder).setIsClamp(true);
            }else{ // if user is still controlling
                x = dX;
            }
        }else{ // if isClamp, item should stop at threshold position
            x = clamp;
        }


        getDefaultUIUtil().onDraw(c, recyclerView, view, x, dY, actionState, active);
    }

    // block item escape
    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return defaultValue*10;
    }
    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 2f;
    }



}
