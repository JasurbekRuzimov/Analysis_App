package uz.jasurbekruzimov.randomapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private final List<String> gamesList;

    public GameAdapter(List<String> gamesList) {
        this.gamesList = gamesList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        String game = gamesList.get(position);
        holder.variantNumber.setText("  Variant - " + (position + 1) + "\n");
        holder.resultTextView.setText(game);
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView variantNumber;
        TextView resultTextView;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            variantNumber = itemView.findViewById(R.id.variantNumber);
            resultTextView = itemView.findViewById(R.id.textView);
        }
    }
}
