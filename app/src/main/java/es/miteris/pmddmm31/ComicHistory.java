package es.miteris.pmddmm31;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

import es.miteris.pmddmm31.model.Comic;

public class ComicHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_history);

        RecyclerView recyclerView = findViewById(R.id.comicList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ComicRecyclerViewAdapter adapter = new ComicRecyclerViewAdapter(new ComicRecyclerViewAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(Comic comicClicked) {
                Intent detailViewAct = new Intent(ComicHistory.this, ComicView.class);
                detailViewAct.putExtra("comicData", comicClicked);
                startActivity(detailViewAct);
            }
        });
        recyclerView.setAdapter(adapter);

        HashMap<Integer,Comic> comicMap = new HashMap<>();
        Bundle comicBundle = this.getIntent().getExtras();
        if(comicBundle!=null) {
            comicMap = (HashMap<Integer,Comic>) comicBundle.getSerializable("comicMap");
        }
        ArrayList<Comic> comicList = comicMap.values()
                .stream()
                .collect(Collectors.toCollection(ArrayList::new));
        //Ordenar por número de comic
        comicList.sort(Comparator.comparingInt(Comic::getId));
        adapter.updateComics(comicList);
        adapter.notifyDataSetChanged();

    }
}