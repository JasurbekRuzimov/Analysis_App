package uz.jasurbekruzimov.randomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<Game> gamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gamesList = new ArrayList<>();

        EditText elementCountEditText = findViewById(R.id.elementCountEditText);
        Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamesList.clear();
                int elementCount = Integer.parseInt(elementCountEditText.getText().toString());
                calculateCombinations(elementCount, 0, new boolean[elementCount]);
            }
        });

        gameAdapter = new GameAdapter(gamesList);
        recyclerView.setAdapter(gameAdapter);
    }

    private void calculateCombinations(int n, int index, boolean[] currentCombination) {
        if (index == n) {
            Game game = new Game(currentCombination);
            gamesList.add(game);
        } else {
            currentCombination[index] = false;
            calculateCombinations(n, index + 1, currentCombination);
            currentCombination[index] = true;
            calculateCombinations(n, index + 1, currentCombination);
        }
        gameAdapter.notifyDataSetChanged();
    }
}
