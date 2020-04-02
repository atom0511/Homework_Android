package trungatom.tqt.lesson5;

import android.content.Context;


public class SquareImage extends androidx.appcompat.widget.AppCompatImageView {


    public SquareImage(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

}