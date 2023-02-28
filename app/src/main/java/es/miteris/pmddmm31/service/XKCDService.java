package es.miteris.pmddmm31.service;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface XKCDService {
    @GET("{id}/info.0.json")
    Call<APIComicData> getComic(@Path("id") String id);
}
