package com.example.minhasanotacoes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final String ANOTACOES = "minhasAnotacoes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        preferences = getSharedPreferences(ANOTACOES, MODE_PRIVATE);

        atualizarEditText();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (salvarTexto())
                    confirmarAcao(view);
                else
                    informarErro(view);

            }
        });
    }

    private void atualizarEditText() {
        editText.setText(preferences.getString("anotacoes", ""));
    }

    private void informarErro(View view) {
        Snackbar.make(view, "Ocorreu um erro ao salvar o texto.", Snackbar.LENGTH_LONG)
                .show();
    }

    private void confirmarAcao(View view) {
        Snackbar.make(view, "Anotações salvas com sucesso!", Snackbar.LENGTH_LONG)
                .setAction("Desfazer", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        desfazer();
                    }
                }).show();
    }

    private void desfazer() {
        editor = preferences.edit();

        String textoAntigo = preferences.getString("anotacoesAntigas", null);

        if (textoAntigo != null) {
            editor.putString("anotacoes", textoAntigo);
            editText.setText(textoAntigo);
        }

        editor.commit();
    }

    private boolean salvarTexto() {
        try {

            salvarTextoAntigo();

            String texto = editText.getText().toString(); // recupera o texto atual
            editor = preferences.edit(); // abre o editor
            editor.putString("anotacoes", texto); // salva o texto atual
            editor.commit(); // confirma as alterações e fecha o editor

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void salvarTextoAntigo() {
        editor = preferences.edit(); // abre o editor
        String textoAntigo = preferences.getString("anotacoes", ""); // recupera o
        editor.putString("anotacoesAntigas", textoAntigo);
        editor.commit();
    }

}
