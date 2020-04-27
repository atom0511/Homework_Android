package trungatom.tqt.thirdapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import trungatom.tqt.thirdapp.R;
import trungatom.tqt.thirdapp.activities.MoviesDetailActivity;
import trungatom.tqt.thirdapp.api.RetrofitConfiguration;
import trungatom.tqt.thirdapp.models.GetMovieRespond;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    Context context;
    List<GetMovieRespond.ResultsBean> movies;

    public MoviesAdapter(List<GetMovieRespond.ResultsBean> movies) {
        this.movies = movies;
    }


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.item_list_movie, parent, false);
        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.setData(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        ImageView ivThumbnail;
        TextView tvTitle;
        TextView tvRating;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRating = itemView.findViewById(R.id.tv_rating);
        }

        public void setData(GetMovieRespond.ResultsBean movies) {
            Glide.with(context)
                    .load(RetrofitConfiguration.getImageBaseUrlThumbnail() + movies.getPoster_path())
                    .into(ivThumbnail);
            tvTitle.setText(movies.getTitle() + "\n");
            tvRating.setText(String.valueOf(movies.getVote_average()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MoviesDetailActivity.class);
                    intent.putExtra("movie_id", movies.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

}
