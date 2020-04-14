package trungatom.tqt.lesson6.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trungatom.tqt.lesson6.R;
import trungatom.tqt.lesson6.api.APIService;
import trungatom.tqt.lesson6.api.RetrofitConfiguration;
import trungatom.tqt.lesson6.models.GetWeatherRespond;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.et_input_city_name)
    EditText etInputCityName;
    @BindView(R.id.bt_get_data)
    Button btGetData;
    @BindView(R.id.tv_show_date)
    TextView tvShowDate;

    String apiKey = "751b5e44a578a890dc6470abc99faa11";
    String temperatureType = "metric";
    @BindView(R.id.tv_show_country)
    TextView tvShowCountry;
    @BindView(R.id.tv_show_wind)
    TextView tvShowWind;
    @BindView(R.id.tv_show_temperature)
    TextView tvShowTemperature;
    @BindView(R.id.tv_show_description)
    TextView tvShowDescription;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_status)
    TextView tvStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.bt_get_data)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_get_data:
                APIService service = RetrofitConfiguration.getInstance().create(APIService.class);
                Call<GetWeatherRespond> call = service.getWeather(
                        etInputCityName.getText().toString(),
                        temperatureType,
                        apiKey
                );
                call.enqueue(new Callback<GetWeatherRespond>() {
                    @Override
                    public void onResponse(Call<GetWeatherRespond> call, Response<GetWeatherRespond> response) {
                        if (response.code() == 200) {
                            Log.d("wtf", "onResponse: " + response.body().getTimezone());
                            GetWeatherRespond quick = response.body();
                            String country = quick.getSys().getCountry();
                            tvShowCountry.setText("Country: " + country);
                            int date1 = quick.getDt();
                            Date date2 = new Date(date1 * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                            String day = simpleDateFormat.format(date2);
                            tvShowDate.setText(day);

                            GetWeatherRespond.WeatherBean quickWeather = response.body().getWeather().get(0);
                            String status = quickWeather.getMain();
                            Log.d("wtf", "onResponse: " + status);
                            tvStatus.setText(status);
                            String icon = quickWeather.getIcon();
                            Glide.with(MainActivity.this)
                                    .load("http://openweathermap.org/img/w/" + icon + ".png").into(ivIcon);
                            String description = quickWeather.getDescription();
                            tvShowDescription.setText("Description: "+description);
                            GetWeatherRespond.MainBean quickMain = response.body().getMain();
                            int temperature = quickMain.getTemp();
                            tvShowTemperature.setText("Temperature: " + temperature);
                            double wind = response.body().getWind().getSpeed();
                            tvShowWind.setText("SpeedWind: " + wind);
                        } else if (response.code() == 404) {
                            Toast.makeText(MainActivity.this, "City not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetWeatherRespond> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                });
                break;
        }
    }
}
