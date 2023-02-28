package es.miteris.pmddmm31;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;

import es.miteris.pmddmm31.model.Comic;
import es.miteris.pmddmm31.service.ComicService;

public class ComicView extends AppCompatActivity {
    private Executor executor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        executor = ((MyApplication)getApplication()).diskIOExecutor;

        setContentView(R.layout.activity_comic_view);
        Comic comic = (Comic) getIntent().getSerializableExtra("comicData");

        TextView comicTitle = findViewById(R.id.comicDetailTitle);
        comicTitle.setText(comic.getTitle());
        TextView comicDate = findViewById(R.id.comicDetailDate);
        comicDate.setText(comic.getDate());

        ImageView imageView = (ImageView) findViewById(R.id.comicImage);
        Picasso.get().load(comic.getUrl()).placeholder(R.drawable.ima_loading).error(R.drawable.ima_error).into(imageView);

        //Boton next
        Button nextButton = (Button) findViewById(R.id.btnNext);
        nextButton.setOnClickListener(view -> {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Obtener el siguiente comic
                        int comicNumber = comic.getId() + 1;
                        Comic comic = new ComicService().getComicById(ComicView.this, String.valueOf(comicNumber));
                        //Tenemos los datos del comic
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Enviamos el comic a la actividad de detalle
                                Intent detailViewAct = new Intent(ComicView.this, ComicView.class);
                                detailViewAct.putExtra("comicData", comic);
                                startActivity(detailViewAct);
                            }
                        });
                    }catch (Exception e){
                        //No se ha encontrado el comic
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Mostramos un mensaje de error
                                Toast.makeText(ComicView.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });
        });

        //Boton prev
        Button prevButton = (Button) findViewById(R.id.btnPrev);
        prevButton.setOnClickListener(view -> {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Obtener el siguiente comic
                        int comicNumber = comic.getId() - 1;
                        Comic comic = new ComicService().getComicById(ComicView.this, String.valueOf(comicNumber));
                        //Tenemos los datos del comic
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Enviamos el comic a la actividad de detalle
                                Intent detailViewAct = new Intent(ComicView.this, ComicView.class);
                                detailViewAct.putExtra("comicData", comic);
                                startActivity(detailViewAct);
                            }
                        });
                    }catch (Exception e){
                        //No se ha encontrado el comic
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Mostramos un mensaje de error
                                Toast.makeText(ComicView.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });
        });
    }
}