package uz.jasurbekruzimov.randomapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private List<Game> gamesList;

    public GameAdapter(List<Game> gamesList) {
        this.gamesList = gamesList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View gameView = inflater.inflate(R.layout.my_row, parent, false);
        return new GameViewHolder(gameView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gamesList.get(position);
        holder.numId.setText(String.valueOf(position + 1));
        holder.yes_txt.setText(game.isYes() ? "Ha" : "Yo'q");
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView numId;
        TextView yes_txt;

        GameViewHolder(View itemView) {
            super(itemView);
            numId = itemView.findViewById(R.id.numId);
            yes_txt = itemView.findViewById(R.id.yes_txt);
        }
    }
}
