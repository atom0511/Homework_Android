package trungatom.tqt.thirdapp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import trungatom.tqt.thirdapp.models.GetMovieRespond;
import trungatom.tqt.thirdapp.models.GetMoviesDetailRespond;

public interface APIService {
    String apiKey = "b7e160a3febc0840469cc167bc94c643";

    @GET("discover/movie?api_key=" + apiKey)
    Call<GetMovieRespond> getMovies(@Query("page") int page);

    @GET("movie/{movie_id}?api_key=" + apiKey + "&append_to_response=videos")
    Call<GetMoviesDetailRespond> getMoviesDetails(@Path("movie_id") int id);
}
