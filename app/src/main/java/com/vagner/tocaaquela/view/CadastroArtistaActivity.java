package com.vagner.tocaaquela.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.vagner.tocaaquela.model.Artista;
import com.vagner.tocaaquela.model.Musica;

public class CadastroArtistaActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextSenha;
    private EditText editTextConfirmaSenha;
    private Button button_save_artista;
    private Artista artista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_artista);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        artista = (Artista) getIntent().getSerializableExtra("artista");

        editTextEmail = (EditText) this.findViewById(R.id.edit_email_cadastro_artista);
        editTextSenha =(EditText) this.findViewById(R.id.edit_senha_cadastro_artista);
        editTextConfirmaSenha = (EditText) this.findViewById(R.id.edit_confirma_senha_cadastro);
        button_save_artista = (Button) findViewById(R.id.button_save_artista);




        if(artista != null){
            editTextEmail.setText(artista.getEmail());
            editTextSenha.setText(artista.getSenha());
        }

        button_save_artista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(capturaDados()){

                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_config), Context.MODE_PRIVATE);
                    String id = sharedPreferences.getString(getString(R.string.id), "");

                    DatabaseReference myRef = database.getReference("artistas");
                    myRef.child(id).push().setValue(artista).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroArtistaActivity.this);
                            alerta.setTitle("Cadastro");
                            alerta.setMessage("Cadastro Realizado com sucesso!");
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
                else{
                    Intent intent = new Intent(CadastroArtistaActivity.this,LoginArtistaActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public boolean capturaDados(){
        String emailArtista = editTextEmail.getText().toString();
        String senhaArtista = editTextSenha.getText().toString();
        String confirmaSenhaArtista = editTextConfirmaSenha.getText().toString();

        if(emailArtista.isEmpty()){
            editTextEmail.setError("Informe o email");
            return false;
        }
        if(senhaArtista.isEmpty()){
            editTextSenha.setError("Informe a senha");
            return false;
        }
        if(confirmaSenhaArtista.isEmpty()){
            editTextConfirmaSenha.setError("Confirme a senha");
            return false;
        }
        if(!senhaArtista.equals(confirmaSenhaArtista)){
            editTextConfirmaSenha.setError("As senhas devem ser igual");
            return false;
        }





        artista = new Artista();

        artista.setEmail(emailArtista);
        artista.setSenha(senhaArtista);
        artista.setConfirmaSenha(confirmaSenhaArtista);

        return true;
    }


}
