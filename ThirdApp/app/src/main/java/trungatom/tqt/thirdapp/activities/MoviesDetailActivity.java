package trungatom.tqt.thirdapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trungatom.tqt.thirdapp.R;
import trungatom.tqt.thirdapp.api.APIService;
import trungatom.tqt.thirdapp.api.RetrofitConfiguration;
import trungatom.tqt.thirdapp.models.GetMoviesDetailRespond;

public class MoviesDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_favorite)
    ImageView ivFavorite;
    @BindView(R.id.ns_detail)
    NestedScrollView nsDetail;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_genres)
    TextView tvGenres;
    @BindView(R.id.ll_trailer)
    LinearLayout llTrailer;
    GetMoviesDetailRespond movie;
    @BindView(R.id.rb_rated)
    RatingBar rbRated;
    @BindView(R.id.tv_voted)
    TextView tvVoted;
    @BindView(R.id.tv_run_time)
    TextView tvRunTime;
    @BindView(R.id.tv_result_date)
    TextView tvResultDate;
    @BindView(R.id.tv_result_over)
    TextView tvResultOver;
    @BindView(R.id.tv_result_revenue)
    TextView tvResultRevenue;
    @BindView(R.id.tv_result_company)
    TextView tvResultCompany;
    @BindView(R.id.tv_result_country)
    TextView tvResultCountry;
    @BindView(R.id.tv_rated)
    TextView tvRated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);
        ButterKnife.bind(this);

        setupUI();
        loadData();
    }

    public void loadData() {
        int id = getIntent().getIntExtra("movie_id", 0);
        APIService apiService = RetrofitConfiguration.getInstance().create(APIService.class);
        Call<GetMoviesDetailRespond> call = apiService.getMoviesDetails(id);
        call.enqueue(new Callback<GetMoviesDetailRespond>() {
            @Override
            public void onResponse(Call<GetMoviesDetailRespond> call, Response<GetMoviesDetailRespond> response) {
                movie = response.body();
                setupUIWithData(movie);
                Log.d("movies", "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<GetMoviesDetailRespond> call, Throwable t) {
                Log.d("movies", "onResponse: " + t.toString());
            }
        });
    }

    private void setupUIWithData(GetMoviesDetailRespond movie) {
        Glide.with(this)
                .load(RetrofitConfiguration.getImageBaseUrlOriginal() + movie.getPoster_path())
                .centerCrop()
                .into(ivBackground);
        tvTitle.setText(movie.getTitle());

        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < movie.getGenres().size(); i++) {
            genres.append(movie.getGenres().get(i).getName());
            if (i != movie.getGenres().size() - 1) {
                genres.append(", ");
            }
        }
        tvGenres.setText(genres);
        String rated = String.valueOf(movie.getVote_average());
        tvRated.setText(rated);
        rbRated.setRating((float) (movie.getVote_average() / 2));
        tvVoted.setText(String.valueOf(movie.getVote_count()));
        tvRunTime.setText(movie.getRuntime() + "m");
        tvResultDate.setText(String.valueOf(movie.getRelease_date()));
        tvResultOver.setText(String.valueOf(movie.getOverview()));

        NumberFormat format = new DecimalFormat("#,###.##");

        tvResultRevenue.setText("$" + format.format(movie.getRevenue()) + ",00");

        StringBuilder company = new StringBuilder();
        for (int i = 0; i < movie.getProduction_companies().size(); i++) {
            company.append(movie.getProduction_companies().get(i).getName());
            if (i != movie.getProduction_companies().size() - 1) {
                company.append(", ");
            }
        }
        tvResultCompany.setText(company);

        StringBuilder country = new StringBuilder();
        for (int i = 0; i < movie.getProduction_countries().size(); i++) {
            country.append(movie.getProduction_countries().get(i).getName());
            if (i != movie.getProduction_countries().size() - 1) {
                country.append(", ");
            }
        }
        tvResultCountry.setText(country);
    }

    private void setupUI() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        ViewGroup.LayoutParams params = nsDetail.getLayoutParams();
        params.height = (int) (screenHeight * 0.85);
        nsDetail.setLayoutParams(params);
    }

    public void watchTrailer() {
        if (movie.getVideos().getResults().size() == 0) {
            Toast.makeText(MoviesDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            String trailerKey = movie.getVideos().getResults().get(0).getKey();
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerKey));
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube/watch?v=" + trailerKey));
            try {
                startActivity(appIntent);
            } catch (Exception e) {
                startActivity(webIntent);
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_favorite, R.id.ll_trailer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_trailer:
                watchTrailer();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_favorite:
                break;
        }
    }


}
