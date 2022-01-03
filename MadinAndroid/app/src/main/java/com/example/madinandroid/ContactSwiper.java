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



    /*

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if(actionState == ACTION_STATE_SWIPE){
            if(buttonShowed != ButtonState.GONE){
                if(buttonShowed == ButtonState.LEFT_VISIBLE) dX = Math.max(dX,buttonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }else{
                setTouchListener(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
            }
        }
        if(buttonShowed == ButtonState.GONE){
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        currentViewHolder = viewHolder;

    }
    private void setTouchListener(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

        recyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                swipeBack = (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) || (motionEvent.getAction() == MotionEvent.ACTION_UP);

                // set movement threshold
                if(swipeBack) {
                    if(dX > buttonWidth) buttonShowed = ButtonState.LEFT_VISIBLE;

                    if(buttonShowed != ButtonState.GONE){
                        // if button is showed we need to disabled original item clicklistener
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        // set Items NONclickable
                        for(int i=0 ; i<recyclerView.getChildCount(); i++){
                            recyclerView.getChildAt(i).setClickable(false);

                        }
                    }
                }
                return false;
            }
        });


    }

    // to make item swipe stop
    // check how much item swiped and set show buttons

    // touch start
    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if( motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    setTouchUpListener(c, recyclerView, viewHolder, dX,dY, actionState, isCurrentActive);
                }
                return false;
            }
        });

    }

    // touch stop
    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if( motionEvent.getAction() == MotionEvent.ACTION_UP){

                    ContactSwiper.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch (View v, MotionEvent event){
                            return false;
                        }
                    });

                    // set Items clickable again
                    for(int i=0 ; i<recyclerView.getChildCount(); i++){
                        recyclerView.getChildAt(i).setClickable(true);
                    }
                    swipeBack = false;
                    buttonShowed = ButtonState.GONE;
                }
                return false;
            }
        });

    }
    */





}
