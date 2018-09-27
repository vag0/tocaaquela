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
import com.vagner.tocaaquela.model.Evento;

public class NovoEventoActivity extends AppCompatActivity {

    private EditText editTextLocal;
    private EditText editTextDia;
    private EditText editHorarioInicio;
    private EditText editHorarioTermino;
    private Button button_save_evento;
    private Evento evento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_evento);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        evento = (Evento) getIntent().getSerializableExtra("evento");

        editTextLocal = (EditText) this.findViewById(R.id.edit_local);
        editTextDia =(EditText) this.findViewById(R.id.edit_dia);
        editHorarioInicio = (EditText) this.findViewById(R.id.edit_hora_inicio);
        editHorarioTermino=(EditText) this.findViewById(R.id.edit_hora_termino);
        button_save_evento = (Button) this.findViewById(R.id.button_save_evento);




        if(evento != null){
            editTextLocal.setText(evento.getLocal());
        }

        button_save_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(capturaDados()){

                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_config), Context.MODE_PRIVATE);
                    String id = sharedPreferences.getString(getString(R.string.id), "");

                    DatabaseReference myRef = database.getReference("eventos");
                    myRef.child(id).push().setValue(evento).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(NovoEventoActivity.this);
                            alerta.setTitle("Novo evento");
                            alerta.setMessage("Evento criado com sucesso!");
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
        String localEvento = editTextLocal.getText().toString();
        String diaEvento = editTextDia.getText().toString();
        String horarioInicio = editHorarioInicio.getText().toString();
        String horarioTermino = editHorarioTermino.getText().toString();

        if(localEvento.isEmpty()){
            editTextLocal.setError("Informe o local");
            return false;
        }

        evento = new Evento();

        evento.setLocal(localEvento);
        evento.setDiaEvento(diaEvento);
        evento.setHorarioInicio(horarioInicio);
        evento.setHorarioTermino(horarioTermino);

        return true;
    }
}
