package uz.jasurbekruzimov.randomapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> resultList;
    private RecyclerView recyclerView;
    EditText elementCountEditText;
    TextView variantNumber;
    ImageView downloadID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadID = findViewById(R.id.downloadID);
        downloadID.setVisibility(View.GONE);

        variantNumber = findViewById(R.id.variantNumber);
        recyclerView = findViewById(R.id.groupRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        elementCountEditText = findViewById(R.id.elementCountEditText);
        Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(v -> {
            calculate500Combinations();
            downloadID.setVisibility(View.VISIBLE);
        });

        downloadID.setOnClickListener(v -> {
            saveToFile(resultList);
            Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
        });
    }

    private void calculate500Combinations() {
        String input = elementCountEditText.getText().toString().trim();
        if (input.isEmpty()) {
            downloadID.setVisibility(View.GONE);
            Toast.makeText(this, "Ma'lumotlar kiritilmagan", Toast.LENGTH_SHORT).show();
        } else {
            resultList = new ArrayList<>();
            ArrayList<Integer> combinations = new ArrayList<>();
            for (int i = 0; i < (int) Math.pow(2, Double.parseDouble(input)); i++) {
                combinations.add(i);
            }
            Collections.shuffle(combinations);

            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            for (int i = 0; i < 20; i++) {
                String result = getCombinationResult(combinations.get(i));
                resultList.add(result);
                myEdit.putString("variant" + i, result);
            }
            myEdit.apply();

            GameAdapter gameAdapter = new GameAdapter(resultList);
            recyclerView.setAdapter(gameAdapter);
        }

    }

    private String getCombinationResult(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int temp = n >> i;
            if ((temp & 1) == 1) {
                sb.append(i+1 + " - Ha \n");
            } else {
                sb.append(i+1 + " - Yo'q \n");
            }
        }
        return sb.toString();
    }

    private void saveToFile(ArrayList<String> resultList) {
        String fileName = "Variants-20.docx";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        try {
            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < resultList.size(); i++) {
                fw.append("Variant ").append(String.valueOf(i + 1)).append(": \n").append(resultList.get(i)).append("\n");
            }
            fw.flush();
            fw.close();
            Toast.makeText(this, "Fayl muvaffaqiyatli yuklab olindi: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
