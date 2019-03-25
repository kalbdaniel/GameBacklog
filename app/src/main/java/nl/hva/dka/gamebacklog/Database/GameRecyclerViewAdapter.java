package nl.hva.dka.gamebacklog.Database;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import nl.hva.dka.gamebacklog.Game;
import nl.hva.dka.gamebacklog.R;

public class GameRecyclerViewAdapter  extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder> {

    private List<Game> games;

    public GameRecyclerViewAdapter(List<Game> gameList) {
        this.games = gameList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView statusTextView;
        TextView dateTextView;
        TextView platformTextView;
        CardView parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            platformTextView = itemView.findViewById(R.id.platformTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            parentLayout = itemView.findViewById(R.id.cardViewEdit);
            parentLayout.setClickable(true);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        GameRecyclerViewAdapter.ViewHolder viewHolder =  new GameRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Game game = games.get(i);
        viewHolder.titleTextView.setText((game.getTitle()));
        viewHolder.statusTextView.setText((game.getStatus()));
        viewHolder.platformTextView.setText((game.getPlatform()));
        viewHolder.dateTextView.setText(game.getDate());

        viewHolder.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
            }
        });

    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void swapList (List<Game> newList) {
        games = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


}
