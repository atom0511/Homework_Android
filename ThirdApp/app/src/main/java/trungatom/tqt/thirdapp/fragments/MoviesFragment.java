package trungatom.tqt.thirdapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trungatom.tqt.thirdapp.R;
import trungatom.tqt.thirdapp.adapters.MoviesAdapter;
import trungatom.tqt.thirdapp.api.APIService;
import trungatom.tqt.thirdapp.api.RetrofitConfiguration;
import trungatom.tqt.thirdapp.models.GetMovieRespond;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    private MoviesAdapter moviesAdapter;
    private List<GetMovieRespond.ResultsBean> movies = new ArrayList<>();

    private int page = 1;
    private int totalItemCount, lastVisible;
    private int visibleThreadhold = 5;
    private boolean isLoading = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        setupUI();
        loadData();

        return view;
    }

    private void setupUI() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvMovies.setLayoutManager(gridLayoutManager);

        moviesAdapter = new MoviesAdapter(movies);
        rvMovies.setAdapter(moviesAdapter);

        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = gridLayoutManager.getItemCount();
                lastVisible = gridLayoutManager.findLastVisibleItemPosition();
                Log.d("fix", "onScrolled: total = " + totalItemCount);
                Log.d("fix", "onScrolled: last = " + lastVisible);

                if (!isLoading && lastVisible >= totalItemCount - visibleThreadhold) {
                    Log.d("fix", "onScrolled: comehereeeeeeeeeeeee");
                    page++;
                    loadData();
                    isLoading = true;
                }
            }
        });

    }

    private void loadData() {
        APIService service = RetrofitConfiguration.getInstance().create(APIService.class);
        Call<GetMovieRespond> call = service.getMovies(page);
        call.enqueue(new Callback<GetMovieRespond>() {
            @Override
            public void onResponse(Call<GetMovieRespond> call, Response<GetMovieRespond> response) {
                if (response.code() == 200) {
                    isLoading = false;
                    movies.addAll(response.body().getResults());
                    moviesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetMovieRespond> call, Throwable t) {
                Toast.makeText(getContext(), "Network failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
