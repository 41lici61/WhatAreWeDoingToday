package com.example.quehacemoshoy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Random;

public class customiceoptions extends AppCompatActivity {

    private LinearLayout containerOptions;
    private EditText etNewOption;
    private Button btnAdd, btnClearAll;
    private MaterialButton btnChooseRandom;
    private TextView tvSelectedOption;
    private ArrayList<String> optionsList;

    private static final String PREFS_NAME = "CustomOptionsPrefs";
    private static final String OPTIONS_COUNT_KEY = "options_count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customiceoptions);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        containerOptions = findViewById(R.id.containerOptions);
        etNewOption = findViewById(R.id.etNewOption);
        btnAdd = findViewById(R.id.btnAdd);
        btnClearAll = findViewById(R.id.btnClearAll);
        btnChooseRandom = findViewById(R.id.btnChooseRandom);
        tvSelectedOption = findViewById(R.id.tvSelectedOption);

        optionsList = new ArrayList<>();

        cargarOpcionesGuardadas();

        if (optionsList.isEmpty()) {
            cargarOpcionesPorDefecto();
        }

        btnAdd.setOnClickListener(v -> agregarOpcion());
        btnClearAll.setOnClickListener(v -> limpiarTodasOpciones());
        btnChooseRandom.setOnClickListener(v -> elegirOpcionAleatoria());
    }

    private void cargarOpcionesPorDefecto() {
        String[] opcionesPorDefecto = {
                "Go for a walk (for example)"
        };

        for (String opcion : opcionesPorDefecto) {
            optionsList.add(opcion);
            agregarVistaOpcion(opcion);
        }
        guardarOpciones();
    }

    private void cargarOpcionesGuardadas() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int count = prefs.getInt(OPTIONS_COUNT_KEY, 0);

        for (int i = 0; i < count; i++) {
            String opcion = prefs.getString("option_" + i, null);
            if (opcion != null) {
                optionsList.add(opcion);
                agregarVistaOpcion(opcion);
            }
        }
    }

    private void guardarOpciones() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(OPTIONS_COUNT_KEY, optionsList.size());

        for (int i = 0; i < optionsList.size(); i++) {
            editor.putString("option_" + i, optionsList.get(i));
        }

        editor.apply();
    }

    private void agregarOpcion() {
        String nuevaOpcion = etNewOption.getText().toString().trim();

        if (!nuevaOpcion.isEmpty()) {
            optionsList.add(nuevaOpcion);
            agregarVistaOpcion(nuevaOpcion);
            etNewOption.setText("");
            guardarOpciones();
            Toast.makeText(this, "Option added and saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter an option", Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarVistaOpcion(String opcion) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        itemLayout.setPadding(16, 12, 16, 12);

        TextView tvOption = new TextView(this);
        tvOption.setText(opcion);
        tvOption.setTextColor(getResources().getColor(android.R.color.white));
        tvOption.setTextSize(16);
        tvOption.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        ));

        MaterialButton btnDelete = new MaterialButton(this);
        btnDelete.setText("Delete");
        btnDelete.setTextColor(getResources().getColor(android.R.color.white));
        btnDelete.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_red_dark));
        btnDelete.setCornerRadius(20);

        btnDelete.setOnClickListener(v -> {
            optionsList.remove(opcion);
            containerOptions.removeView(itemLayout);
            guardarOpciones();
            Toast.makeText(customiceoptions.this, "Option deleted", Toast.LENGTH_SHORT).show();
        });

        itemLayout.addView(tvOption);
        itemLayout.addView(btnDelete);

        View separator = new View(this);
        separator.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        ));
        separator.setBackgroundColor(0x33FFFFFF);

        containerOptions.addView(itemLayout);
        containerOptions.addView(separator);
    }

    private void elegirOpcionAleatoria() {
        if (optionsList.isEmpty()) {
            tvSelectedOption.setText("⚠️ There are no options");
            Toast.makeText(this, "Add some options first?", Toast.LENGTH_LONG).show();
        } else {
            Random random = new Random();
            int index = random.nextInt(optionsList.size());
            String opcionElegida = optionsList.get(index);
            tvSelectedOption.setText("✨ You got: ✨\n\n" + opcionElegida);

            btnChooseRandom.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_green_dark));
            btnChooseRandom.postDelayed(() ->
                    btnChooseRandom.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_blue_dark)), 500);
        }
    }

    private void limpiarTodasOpciones() {
        optionsList.clear();
        containerOptions.removeAllViews();
        guardarOpciones();
        tvSelectedOption.setText("Press the button to choose");
        Toast.makeText(this, "All options were deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        guardarOpciones();
    }
}