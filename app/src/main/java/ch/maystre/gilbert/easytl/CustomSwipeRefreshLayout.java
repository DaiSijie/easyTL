package ch.maystre.gilbert.easytl;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {

    private final int threshold;

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        this.threshold = height/3; //only the first third of the screen triggers a refresh on swipe.
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(event.getY() > threshold){
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }
}
