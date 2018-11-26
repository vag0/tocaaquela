package com.vagner.tocaaquela.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.utils.Singleton;

public class BloqueiaVotoActivity extends AppCompatActivity {

    EditText editTextPegaCogigo;
    Button buttonEnviaCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueia_voto);

        editTextPegaCogigo = (EditText)findViewById(R.id.editTextCodigoUser);
        buttonEnviaCodigo = (Button)findViewById(R.id.buttonEnviaCodigo);

        final String codigo = editTextPegaCogigo.getText().toString().trim();

        buttonEnviaCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Singleton.getInstacia().salvaCodigo(codigo);
                startActivity(new Intent(getApplicationContext(), EscolhaDeMusicasActivity.class ));
            }
        });
    }
}
