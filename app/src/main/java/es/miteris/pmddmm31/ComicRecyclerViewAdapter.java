package es.miteris.pmddmm31;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.miteris.pmddmm31.model.Comic;

public class ComicRecyclerViewAdapter extends RecyclerView.Adapter<ComicRecyclerViewAdapter.ViewHolder> {

    private List<Comic> comicList;
    private final OnUserClickListener listener;

    public ComicRecyclerViewAdapter(OnUserClickListener listener) {
        this.comicList = new ArrayList<>();
        this.listener = listener;
    }

    public void updateComics(List<Comic> comicList){
        this.comicList = comicList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comic_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comic comic = comicList.get(position);
        holder.comicView.setText(comic.getId() + " - " + comic.getTitle() + " - " + comic.getDate());
        holder.comicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClick(comic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView comicView;
        public ViewHolder(View view) {
            super(view);
            comicView = (TextView) view.findViewById(R.id.comicListItemText);
        }
    }

    public interface OnUserClickListener {
        void onUserClick(Comic comicClicked);
    }
}
