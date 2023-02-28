package es.miteris.pmddmm31;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.miteris.pmddmm31.service.XKCDService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    public XKCDService xkcdService;
    ExecutorService diskIOExecutor = Executors.newSingleThreadExecutor();

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_xkcd))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        xkcdService = retrofit.create(XKCDService.class);
    }
}
