package trungatom.tqt.lesson3;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class CustomView extends View {

    public CustomView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width;
        int height;
        width = widthSize;
        height = widthSize/2;
        setMeasuredDimension(width,height);
    }

}
