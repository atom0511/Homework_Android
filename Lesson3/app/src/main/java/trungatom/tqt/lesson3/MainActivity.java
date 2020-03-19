package trungatom.tqt.lesson3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fbAdd;
    LinearLayout llInScroll;
    ScrollView myScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myScroll = findViewById(R.id.my_sv);
        llInScroll = findViewById(R.id.ll_in_scroll);
        fbAdd = findViewById(R.id.floatingButton);

        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llInScroll.addView(createImageView(MainActivity.this));
                myScroll.post(new Runnable() {
                    @Override
                    public void run() {
                        myScroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    public CustomView createImageView(Context context) {
        CustomView imageView = new CustomView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0, 16);
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundResource(randomImage());
        return imageView;
    }

    public int randomImage() {
        Random random = new Random();
        int rdImage = random.nextInt(5) + 1;
        switch (rdImage) {
            case 1:
                return R.drawable.food_1;
            case 2:
                return R.drawable.food_2;
            case 3:
                return R.drawable.food_3;
            case 4:
                return R.drawable.food_4;
            case 5:
                return R.drawable.food_5;
        }
        return 0;
    }

}
