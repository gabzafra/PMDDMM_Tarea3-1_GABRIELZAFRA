package es.miteris.pmddmm31;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.concurrent.Executor;

import es.miteris.pmddmm31.model.Comic;
import es.miteris.pmddmm31.service.ComicService;

public class Welcome extends AppCompatActivity {

    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executor = ((MyApplication)getApplication()).diskIOExecutor;

        EditText comicNumberInput = (EditText) findViewById(R.id.inputComicNumber);

        Button detailsButton = (Button) findViewById(R.id.btnShowDetail);
        detailsButton.setOnClickListener(view -> {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Obtener el comic
                        Comic comic = new ComicService().getComicById(Welcome.this,comicNumberInput.getText().toString());
                        //Tenemos los datos del comic
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Enviamos el comic a la actividad de detalle
                                Intent detailViewAct = new Intent(Welcome.this, ComicView.class);
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
                                Toast.makeText(Welcome.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });
        });

        Button historyButton = (Button) findViewById(R.id.btnShowHistory);
        historyButton.setOnClickListener(view -> {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Obtener el historial de comics vistos
                        HashMap<Integer,Comic> comicList = new ComicService().getComicHistory(Welcome.this);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Enviamos el comic a la actividad de detalle
                                Intent historyViewAct = new Intent(Welcome.this, ComicHistory.class);
                                historyViewAct.putExtra("comicMap", comicList);
                                startActivity(historyViewAct);
                            }
                        });
                    }catch (Exception e){
                        //No se ha podido acceder al historial o esta vacio
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Mostramos un mensaje de error
                                Toast.makeText(Welcome.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });
        });



    }
}