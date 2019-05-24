package com.hfad.swipecontrol;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.telecom.Call;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import static android.content.ContentValues.TAG;
import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;

public class SwipeController extends ItemTouchHelper.Callback {

    List<Player> players;
    PlayersDataAdapter mAdapter;
    boolean swipeBack;
    Context context;

    public SwipeController(List<Player> players, PlayersDataAdapter adapter, Context context) {
        this.players = players;
        mAdapter = adapter;
        this.context = context;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

//        moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        deleteItem(viewHolder.getAdapterPosition());
    }

    void deleteItem(int position) {
        players.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // get item
        View itemview = viewHolder.itemView;
        // get an icon from drawable folder
        Drawable deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_menu_delete);
// get height and width sizes from layout
        float IcontHeight = deleteIcon.getIntrinsicHeight();
        float IconWidth = deleteIcon.getIntrinsicWidth();
// get item's bottom and Top sizeфв
        float itemHeight = itemview.getBottom() - itemview.getTop();

        if (actionState == ACTION_STATE_SWIPE) {   // user is swipe
            Log.d(TAG, "////////////////////////////////////////////////");
            Log.d(TAG, "ACTION STATE SWAP is true: ");

            Resources r = context.getResources();   // as you read
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);    // create paint object
            // get layout all values from inflate crime_fragment_list.xml
            RectF layout = new RectF(itemview.getLeft(), itemview.getTop(), itemview.getRight(), itemview.getBottom());
            // set color
            paint.setColor(r.getColor(R.color.colorAccent));
            // drawing canvas
            c.drawRect(layout, paint);

            // to calculate deleteIcon object that necessary values
            int deleteIconTop = (int) (itemview.getTop() + (itemHeight - IcontHeight)/2 );
            int deleteIconBottom = (int) (deleteIconTop + IcontHeight);
            int deleteIconMargin = (int) ((itemHeight - IcontHeight) / 2);
            int deleteIconLeft = (int) (itemview.getRight() - deleteIconMargin - IconWidth);
            int deleteIconRight = (int) itemview.getRight() - deleteIconMargin;
            // then set boundry that get values above
            deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
            // to add canvas
            deleteIcon.draw(c);

            /*c.save(); */
// you can find explanation here (1**)
            getDefaultUIUtil().onDraw(c, recyclerView, viewHolder.itemView, dX, dY, actionState, isCurrentlyActive);

        } else {
            Log.d(TAG, "////////////////////////////////////////////////");
            Log.d(TAG, "ACTION STATE SWAP is false: ");
        }
    }


//    @Override
//    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//        super.onSelectedChanged(viewHolder, actionState);
//    }

//    @Override
//    public void onChildDraw(Canvas c,
//                            RecyclerView recyclerView,
//                            RecyclerView.ViewHolder viewHolder,
//                            float dX, float dY,
//                            int actionState, boolean isCurrentlyActive) {
//
//        if (actionState == ACTION_STATE_SWIPE) {
//            setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        }
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//    }
//
//    private void setTouchListener(Canvas c,
//                                  RecyclerView recyclerView,
//                                  RecyclerView.ViewHolder viewHolder,
//                                  float dX, float dY,
//                                  int actionState, boolean isCurrentlyActive) {
//
//        recyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
//                return false;
//            }
//        });
//    }
//
//
//    @Override
//    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
//        if (swipeBack) {
//            swipeBack = false;
//            return 0;
//        }
//        return super.convertToAbsoluteDirection(flags, layoutDirection);
//    }


//
//    void moveItem(int oldPos, int newPos) {
//        Player fooditem = players.get(oldPos);
//
//        players.remove(oldPos);
//        players.add(newPos, fooditem);
//        mAdapter.notifyItemMoved(oldPos, newPos);
//    }
//


//    @Override
//    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
//        if (swipeBack) {
//            swipeBack = false;
//            return 0;
//        }
//        return super.convertToAbsoluteDirection(flags, layoutDirection);
//    }
//
//    @Override
//    public void onChildDraw(Canvas c,
//                            RecyclerView recyclerView,
//                            RecyclerView.ViewHolder viewHolder,
//                            float dX, float dY,
//                            int actionState, boolean isCurrentlyActive) {
//
//        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//            setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        }
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//    }
//
//    private void setTouchListener(Canvas c,
//                                  RecyclerView recyclerView,
//                                  RecyclerView.ViewHolder viewHolder,
//                                  float dX, float dY,
//                                  int actionState, boolean isCurrentlyActive) {
//
//        recyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
//                return false;
//            }
//        });
}
