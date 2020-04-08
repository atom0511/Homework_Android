package trungatom.tqt.lesson5.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trungatom.tqt.lesson5.R;
import trungatom.tqt.lesson5.databases.DatabaseUtils;
import trungatom.tqt.lesson5.models.ItemModel;

public class BookListActivity extends AppCompatActivity {

    @BindView(R.id.tv_story)
    TextView tvStory;
    @BindView(R.id.iv_image_inside)
    ImageView ivImageInside;
    @BindView(R.id.tv_title_inside)
    TextView tvTitleInside;
    @BindView(R.id.tv_author_inside)
    TextView tvAuthorInside;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.iv_back)
    ImageView ivBack;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        getData(position);
        Log.d("check", "onCreate: " + position);
    }


    public void getData(int position) {
        Glide.with(this).load(DatabaseUtils.getInstance(this).getListTopic().get(position).getmImage()).into(ivImageInside);
        tvTitleInside.setText(DatabaseUtils.getInstance(this).getListTopic().get(position).getmTitle());
        tvAuthorInside.setText(DatabaseUtils.getInstance(this).getListTopic().get(position).getmAuthor());
        tvDescription.setText(DatabaseUtils.getInstance(this).getListTopic().get(position).getmDescription());
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
