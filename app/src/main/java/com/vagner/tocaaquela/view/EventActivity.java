package com.vagner.tocaaquela.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Evento1;
import com.vagner.tocaaquela.utils.ArtistList;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    public static final String EVENTO_NAME = "com.vagner.tocaaquela.evento1name";
    public static final String EVENTO_ID = "com.vagner.tocaaquela.evento1id";

    EditText editTextName;
    EditText editTextDia;
    EditText editTextHorario;
    Spinner spinnerGenre;
    Button buttonAddEvento;
    ListView listViewEventos;

    //a list to store all the artist from firebase database
    List<Evento1> eventos;

    //our database reference object
    DatabaseReference databaseEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //getting the reference of eventos node
        databaseEventos = FirebaseDatabase.getInstance().getReference("eventos");

        //getting views
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDia = (EditText) findViewById(R.id.editTextDiaEvent);
        editTextHorario = (EditText) findViewById(R.id.editTextHorario);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenres);
        listViewEventos = (ListView) findViewById(R.id.listViewArtists);

        buttonAddEvento = (Button) findViewById(R.id.buttonAddArtist);

        //list to store eventos
        eventos = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAddEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addArtist();
            }
        });

        //attaching listener to listview
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Evento1 artist = eventos.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ArtistActivity.class);

                //putting artist name and id to intent
                intent.putExtra(EVENTO_ID, artist.getEvento1Id());
                intent.putExtra(EVENTO_NAME, artist.getEvento1Nome());

                //starting the activity with intent
                startActivity(intent);
            }
        });

        listViewEventos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Evento1 artist = eventos.get(i);
                showUpdateDeleteDialog(artist.getEvento1Id(), artist.getEvento1Nome());
                return true;
            }
        });


    }

    private void showUpdateDeleteDialog(final String eventoId, String eventoNome) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextDia = (EditText) dialogView.findViewById(R.id.editTextDiaEvent);
        final EditText editTextHorario = (EditText) dialogView.findViewById(R.id.editTextHorario);
        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(eventoNome);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String dia = editTextDia.getText().toString().trim();
                String horario = editTextHorario.getText().toString().trim();
                String genre = spinnerGenre.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(dia)&&!TextUtils.isEmpty(horario)) {
                    updateArtist(eventoId, name, genre,dia,horario);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(eventoId);
                b.dismiss();
            }
        });
    }

    private boolean updateArtist(String id, String name, String genre, String dia, String horario) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("eventos").child(id);

        //updating artist
        Evento1 artist = new Evento1(id, name, genre, dia , horario);
        dR.setValue(artist);
        Toast.makeText(getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("eventos").child(id);

        //removing artist
        dR.removeValue();

        //getting the tracks reference for the specified artist
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        //removing all tracks
        drTracks.removeValue();
        Toast.makeText(getApplicationContext(), "Artist Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseEventos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                eventos.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Evento1 artist = postSnapshot.getValue(Evento1.class);
                    //adding artist to the list
                    eventos.add(artist);
                }

                //creating adapter
                ArtistList artistAdapter = new ArtistList(EventActivity.this, eventos);
                //attaching adapter to the listview
                listViewEventos.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /*
     * This method is saving a new artist to the
     * Firebase Realtime Database
     * */
    private void addArtist() {
        //getting the values to save
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();
        String dia = spinnerGenre.getSelectedItem().toString();
        String horario = editTextName.getText().toString().trim();




        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseEventos.push().getKey();

            //creating an Artist Object
            Evento1 evento = new Evento1(id, name, genre, dia, horario);

            //Saving the Artist
            databaseEventos.child(id).setValue(evento);

            //setting edittext to blank again
            editTextName.setText("");

            //displaying a success toast
            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}