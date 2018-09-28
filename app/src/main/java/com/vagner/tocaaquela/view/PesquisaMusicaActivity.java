package com.vagner.tocaaquela.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Musica;

import java.util.ArrayList;
import java.util.List;

public class PesquisaMusicaActivity extends AppCompatActivity {


    private EditText editPalavra;
    private ListView listVPesquisa;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Musica> listMusica = new ArrayList<>();
    private ArrayAdapter<Musica> arrayAdapterMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_musica);
        databaseReference = FirebaseDatabase.getInstance().getReference("musicas");

        inicializarComponentes();// 1º passo - inicializar os componentes da tela e referencia-los
        inicializarFirebase();//2º passo - inicializar a conexão e pegar a referencia do Banco Firebase
        eventoEdit();// 3º passo - implementos os eventos que serão utilizando durante a aplicação
    }

    private void eventoEdit() {
        editPalavra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String palavra = editPalavra.getText().toString().trim(); // armaze o valor do editText
                pesquisarPalavra(palavra);
            }
        });
    }

    private void pesquisarPalavra(String palavra) {
        Query query; // 5º passo - cria o objeto que ira receber os dados da pesquisa
        if (palavra.equals("")){// verifica o conteudo da variavel

            // orderByChil("nome") indica qual 'chave' sejá usa-da para ordenar os objetos
            // obs: letra maiuscula vem antes de minuscula na pesquisa
            query = databaseReference.child("musicas").orderByChild("nome");
        }else{
            /// caso palavra tenha algum valor
            // trazermos todos os objetos de formar que o primeiro a ser buscado no banco
            // é exatamente iqual a palavra enviada - starAt
            // e os seguintes sera a palavra acrescida de qualquer outro valore de string - endAt
            // a string  "\uf8ff" indica que quero qualquer valor apos a palavra inicial
            query = databaseReference.child("musicas")
                    .orderByChild("nome").startAt(palavra).endAt(palavra+"\uf8ff");
        }


        listMusica.clear();

        // implemento o metodo na query
        databaseReference.child("musicas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){// separo cada objeto contido na dataSnapshot
                    Musica musica = objSnapshot.getValue(Musica.class);

                   // if(!musica.getNome().isEmpty())
                    listMusica.add(musica);// .add(musica);



                }


                arrayAdapterMusica = new ArrayAdapter<Musica>(PesquisaMusicaActivity.this,
                        android.R.layout.simple_list_item_1,listMusica);

                // incluo na ListView o arrayAdapter
                listVPesquisa.setAdapter(arrayAdapterMusica);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(PesquisaMusicaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializarComponentes() {
        editPalavra = (EditText) findViewById(R.id.editPalavra);
        listVPesquisa = (ListView) findViewById(R.id.listVPesquisa);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ultimo passo - assim que mostra a tela quero que seja exibido todos os dados contidos no Banco
        pesquisarPalavra("");
    }

}
