package com.flavorsujung.isthereopen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView implements ViewTreeObserver.OnGlobalLayoutListener {
    private View header;
    private boolean mIsHeaderSticky;
    private float mHeaderInitPosition;

    public MyScrollView(Context context) {
        super(context, null, 0);
        setOverScrollMode(OVER_SCROLL_NEVER);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        setOverScrollMode(OVER_SCROLL_NEVER);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOverScrollMode(OVER_SCROLL_NEVER);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    @Override
    public void onGlobalLayout() {

    }
    public final View getHeader() {
        return this.header;
    }
}
