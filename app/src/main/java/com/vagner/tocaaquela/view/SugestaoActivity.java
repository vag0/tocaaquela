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
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Evento;
import com.vagner.tocaaquela.model.Musica;
import com.vagner.tocaaquela.model.Sugestao;
import com.vagner.tocaaquela.utils.Singleton;

public class SugestaoActivity extends AppCompatActivity {
    private TextView textViewSugestao;

    private EditText editTextSugestao;

    private Button buttonEnviaSugestao;
    private Sugestao sugestao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugestao);



    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    sugestao = (Sugestao) getIntent().getSerializableExtra("sugestao");

    textViewSugestao = (TextView)this.findViewById(R.id.textView_sugestao_id);
    editTextSugestao =(EditText)this.findViewById(R.id.editText_suagestao_id);
    buttonEnviaSugestao =(Button)this.findViewById(R.id.button_enviar_sugestao);



        if( sugestao!= null){
           editTextSugestao.setText(sugestao.getMensagem());
        }


        buttonEnviaSugestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(capturaDados()){

                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_config), Context.MODE_PRIVATE);
                    String id = sharedPreferences.getString(getString(R.string.id), "");

                    DatabaseReference databaseSugestoes = database.getReference("sugestoes").child(Singleton.getInstacia().getEvento().getEvento1Id());
                    databaseSugestoes.child(id).push().setValue(sugestao).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(SugestaoActivity.this);
                            alerta.setTitle("Nova Sugestão");
                            alerta.setMessage("Sugestão enviada com sucesso!");
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
        String sugestaoMensagem = editTextSugestao.getText().toString();


        if(sugestaoMensagem.isEmpty()){
            editTextSugestao.setError("Informe o local");
            return false;
        }

        sugestao = new Sugestao();

        sugestao.setMensagem(sugestaoMensagem);

        return true;
    }
}
