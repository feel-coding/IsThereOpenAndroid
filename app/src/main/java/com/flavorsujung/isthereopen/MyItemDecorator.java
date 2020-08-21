package com.flavorsujung.isthereopen;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemDecorator extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int index = parent.getChildAdapterPosition(view) + 1;

        outRect.set(20, 15, 20, 15);

        view.setBackgroundColor(0xFFFFFFFF);
        ViewCompat.setElevation(view, 20.0f);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int width = parent.getWidth();
        int height = parent.getHeight();
        Paint paint = new Paint();
        paint.setColor(0xFFF9F9F9);
        c.drawRect(0, 0, width, height, paint);
    }
}
