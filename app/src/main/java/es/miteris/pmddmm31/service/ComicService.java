package es.miteris.pmddmm31.service;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;

import es.miteris.pmddmm31.MyApplication;
import es.miteris.pmddmm31.R;
import es.miteris.pmddmm31.model.Comic;
import es.miteris.pmddmm31.model.ComicDAO;
import retrofit2.Call;
import retrofit2.Response;

public class ComicService {
    public Comic getComicById(Context context, String id) throws Exception {
        int comicNumber;
        //Verificar si nos llega un entero
        try {
            comicNumber = Integer.parseInt(id);
        } catch (Exception e) {
            throw new Exception(context.getString(R.string.err_invalid_id));
        }

        Comic comic;

        if (comicNumber > 0) {
            //Buscamos primero si ya lo tenemos en la base de datos
            comic = new ComicDAO(context).getComic(comicNumber);
            if (comic == null) {
                //Si no lo tenemos en la base de datos, lo pedimos a la api
                Call<APIComicData> request = ((MyApplication) context.getApplicationContext()).xkcdService.getComic(id);
                try {
                    Response<APIComicData> response = request.execute();
                    if (response.isSuccessful()) {
                        comic = formatAPIComicData(response.body());
                    }else{
                        //La api devolvio un error
                        throw new Exception(context.getString(R.string.err_comic_not_found));
                    }
                    //Añadimos el comic desconocido a la base de datos
                    new ComicDAO(context).createComic(comic);
                } catch (IOException e) {
                    throw new Exception(context.getString(R.string.err_comic_not_found));
                }
            }
        } else {
            //No hay comics con id <=0
            throw new Exception(context.getString(R.string.err_invalid_id));
        }
        return comic;
    }

    public HashMap<Integer, Comic> getComicHistory(Context context) throws Exception {
        HashMap<Integer, Comic> comicList;
        try {
            comicList = new ComicDAO(context).getAllComics();
        }catch (Exception e){
            throw new Exception(context.getString(R.string.err_io_history));
        }
        if(comicList.size() > 0){
            return comicList;
        }else{
            throw new Exception(context.getString(R.string.err_empty_history));
        }
    }

    private Comic formatAPIComicData(APIComicData comicData) {
        String date = comicData.getDay() + "/" + comicData.getMonth() + "/" + comicData.getYear();
        int id = Integer.parseInt(comicData.getNum());
        return new Comic(id, comicData.getTitle(), date, comicData.getImg());
    }
}
