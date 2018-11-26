package com.vagner.tocaaquela.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.fragment.EventoFragment;
import com.vagner.tocaaquela.utils.Singleton;

public class AvaliaArtistaActivity extends AppCompatActivity {

    private  static Button button_save_avaliacao;
    private  static TextView textView_avaliacao;
    private  static RatingBar ratingBar_avaliacao;
    DatabaseReference databaseAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avalia_artista);



        listenerForRatingBar();
        avaliaArtista();

        databaseAvaliacao = FirebaseDatabase.getInstance().getReference("avaliacao").child(Singleton.getInstacia().getEvento().getEvento1Nome());




    }

    public void listenerForRatingBar(){
        ratingBar_avaliacao = (RatingBar) findViewById(R.id.ratingBar_avaliacao_id);
        textView_avaliacao = (TextView)findViewById(R.id.textView_avaliacao_id);
        button_save_avaliacao = (Button)findViewById(R.id.button_save_avaliacao_id);

        ratingBar_avaliacao.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String id = databaseAvaliacao.push().getKey();
                databaseAvaliacao.child(id).setValue(v);

                textView_avaliacao.setText(String.valueOf(v));
            }
        });

    }

    private void avaliaArtista() {
        button_save_avaliacao = (Button) findViewById(R.id.button_save_avaliacao_id);
        ratingBar_avaliacao = (RatingBar) findViewById(R.id.ratingBar_avaliacao_id);

        button_save_avaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(AvaliaArtistaActivity.this);
                alerta.setTitle("Nova Avaliação");
                alerta.setMessage("Avaliação enviada com sucesso!");
                alerta.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alerta.show();
                Toast.makeText(AvaliaArtistaActivity.this,String.valueOf(ratingBar_avaliacao.getRating()),Toast.LENGTH_SHORT).show();
            }

        });



    }

}
