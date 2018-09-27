package com.vagner.tocaaquela.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Musica;

public class NovaMusicaActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextAutor;
    private EditText editTextGenero;
    private Button button_save_musica;
    private Musica musica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_musica);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        musica = (Musica) getIntent().getSerializableExtra("musica");

        editTextNome = (EditText) this.findViewById(R.id.edit_nome_musica);
        editTextAutor =(EditText) this.findViewById(R.id.edit_autor_musica);
        editTextGenero = (EditText) this.findViewById(R.id.edit_genero_musica);
        button_save_musica = (Button) this.findViewById(R.id.button_save_musica);




        if(musica != null){
            editTextNome.setText(musica.getNome());
        }

        button_save_musica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(capturaDados()){

                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_config), Context.MODE_PRIVATE);
                    String id = sharedPreferences.getString(getString(R.string.id), "");

                    DatabaseReference myRef = database.getReference("musicas");
                    myRef.child(id).push().setValue(musica).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(NovaMusicaActivity.this);
                            alerta.setTitle("Nova Musica");
                            alerta.setMessage("Musica adicionada com sucesso!");
                            alerta.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                            alerta.show();
                        }
                    });

                }
            }
        });
    }

    public boolean capturaDados(){
        String nomeMusica = editTextNome.getText().toString();
        String autorMusica = editTextAutor.getText().toString();
        String generoMusica = editTextGenero.getText().toString();

        if(nomeMusica.isEmpty()){
            editTextNome.setError("Informe o nome");
            return false;
        }

        musica = new Musica();

        musica.setNome(nomeMusica);
        musica.setAutor(autorMusica);
        musica.setGenero(generoMusica);

        return true;
    }

}
