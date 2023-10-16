package uz.jasurbekruzimov.randomapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
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
    private GameAdapter gameAdapter;
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
        downloadID.setVisibility(View.GONE); // Boshlang'ich holatda yashirilgan

        variantNumber = findViewById(R.id.variantNumber);
        recyclerView = findViewById(R.id.groupRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        elementCountEditText = findViewById(R.id.elementCountEditText);
        Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate500Combinations();
                downloadID.setVisibility(View.VISIBLE);
            }
        });

        downloadID.setOnClickListener(v -> saveToFile(resultList));
    }

    private void calculate500Combinations() {
        String input = elementCountEditText.getText().toString().trim();
        if (input.isEmpty()) {
            downloadID.setVisibility(View.GONE);// ElementCountEditText bo'sh bo'lsa, bunda ma'lumot kiritilmagan
            Toast.makeText(this, "Ma'lumotlar kiritilmagan", Toast.LENGTH_SHORT).show();
        } else {
            // ElementCountEditText bo'sh bo'lmasa, ma'lumotlarni ishlab chiqing
            resultList = new ArrayList<>();
            ArrayList<Integer> combinations = new ArrayList<>();
            for (int i = 0; i < (int) Math.pow(2, Double.parseDouble(input)); i++) {
                combinations.add(i);
            }
            Collections.shuffle(combinations);

            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            for (int i = 0; i < 500; i++) {
                String result = getCombinationResult(combinations.get(i), 20); // Assuming 500 is the element count
                resultList.add(result);
                myEdit.putString("variant" + i, result);
            }
            myEdit.apply();

            gameAdapter = new GameAdapter(resultList);
            recyclerView.setAdapter(gameAdapter);
        }

    }

    // Qolgan metodlarni shunchaki saqlashingiz mumkin

    private String getCombinationResult(int n, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int temp = n >> i;
            if ((temp & 1) == 1) {
                sb.append("  Ha \n");
            } else {
                sb.append("  Yo'q \n");
            }
        }
        return sb.toString();
    }

    private void saveToFile(ArrayList<String> resultList) {
        // O'zingiz kerakli kodi qo'shing
    }

    private void saveToCSV(ArrayList<String> resultList) {
        String fileName = "variants.csv";
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        try {
            FileWriter fw = new FileWriter(file);
            for (String variant : resultList) {
                fw.append(variant).append("\n");
            }
            fw.flush();
            fw.close();
            Toast.makeText(this, "File saved: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    private void saveToExcel(ArrayList<String> resultList) {
//        String fileName = "variants.xls";
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("Variants");
//
//        int rowNum = 0;
//        for (String variant : resultList) {
//            Keyboard.Row row = sheet.createRow(rowNum++);
//            String[] tokens = variant.split(",");
//            int colNum = 0;
//            for (String token : tokens) {
//                Cell cell = row.createCell(colNum++);
//                cell.setCellValue(token);
//            }
//        }
//
//        try {
//            FileOutputStream outputStream = new FileOutputStream(fileName);
//            workbook.write(outputStream);
//            workbook.close();
//            Toast.makeText(this, "File saved: " + fileName, Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



}
