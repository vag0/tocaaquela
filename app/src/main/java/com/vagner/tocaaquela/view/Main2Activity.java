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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.model.Artist;
import com.vagner.tocaaquela.utils.ArtistList;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    public static final String ARTIST_NAME = "com.vagner.tocaaquela.artistid";
    public static final String ARTIST_ID = "com.vagner.tocaaquela.artistid";

    TextView textViewNomeArtist;
    EditText editTextLocal;
    EditText editTextDia;
    EditText editTextHorario;
    Spinner spinnerGenre;
    Button buttonAddArtist;
    ListView listViewArtists;

    //a list to store all the artist from firebase database
    List<Artist> artists;

    //our database reference object
    DatabaseReference databaseArtists;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //getting the reference of artists node
        databaseArtists = FirebaseDatabase.getInstance().getReference("events");

        //getting views
        editTextLocal = (EditText)findViewById(R.id.editTextLocalEvent);
        editTextDia = (EditText)findViewById(R.id.editTextDiaEvent);
        editTextHorario = (EditText)findViewById(R.id.editTextName);
       // textViewNomeArtist = (TextView) findViewById(R.id.textViewNameArtist);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenres);
        listViewArtists = (ListView) findViewById(R.id.listViewArtists);

        buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

      //  textViewNomeArtist.setText(user.getEmail());


        //list to store artists
        artists = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addArtist();
            }
        });

        //attaching listener to listview
        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Artist artist = artists.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ArtistActivity.class);

                //putting artist name and id to intent
                intent.putExtra(ARTIST_ID, artist.getArtistId());
                intent.putExtra(ARTIST_NAME, artist.getArtistLocalEvent());

                //starting the activity with intent
                startActivity(intent);
            }
        });

        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = artists.get(i);
                showUpdateDeleteDialog(artist.getArtistId(), artist.getArtistLocalEvent());
                return true;
            }
        });


    }

    private void showUpdateDeleteDialog(final String artistId, String artistName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextHorario = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextDia = (EditText) dialogView.findViewById(R.id.editTextDiaEvent);
        final EditText editTextLocal = (EditText) dialogView.findViewById(R.id.editTextLocalEvent);
        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(artistName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth = FirebaseAuth.getInstance();

                FirebaseUser user = firebaseAuth.getCurrentUser();

                String horario = editTextHorario.getText().toString().trim();
                String nameEmail = user.getEmail();
                String genre = spinnerGenre.getSelectedItem().toString();
                String dia = editTextDia.getText().toString().trim();
                String local = editTextLocal.getText().toString().trim();


                if (!TextUtils.isEmpty(local)) {
                    updateArtist(artistId, local, dia,genre,nameEmail, horario);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(artistId);
                b.dismiss();
            }
        });
    }

    private boolean updateArtist(String id, String name, String genre, String local,String dia, String horario) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(id);

        //updating artist
        Artist artist = new Artist(id, name, genre,local,dia,horario);
        dR.setValue(artist);
        Toast.makeText(getApplicationContext(), "Evento Atualizado", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(id);

        //removing artist
        dR.removeValue();

        //getting the tracks reference for the specified artist
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        //removing all tracks
        drTracks.removeValue();
        Toast.makeText(getApplicationContext(), "Evento Apagado", Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                artists.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Artist artist = postSnapshot.getValue(Artist.class);
                    //adding artist to the list
                    artists.add(artist);
                }

                //creating adapter
                ArtistList artistAdapter = new ArtistList(Main2Activity.this, artists);
                //attaching adapter to the listview
                listViewArtists.setAdapter(artistAdapter);
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
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //getting the values to save
        String local = editTextLocal.getText().toString().trim();
        String dia = editTextDia.getText().toString().trim();
        String horario = editTextHorario.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();
        String nameEmail = user.getEmail().trim();

        //checking if the value is provided
        if (!TextUtils.isEmpty(local)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseArtists.push().getKey();

            //creating an Artist Object
            Artist artist = new Artist(id, local, dia,genre,nameEmail, horario);

            //Saving the Artist
            databaseArtists.child(id).setValue(artist);

            //setting edittext to blank again
            editTextHorario.setText("");
            editTextLocal.setText("");
            editTextDia.setText("");


            //displaying a success toast
            Toast.makeText(this, "Evento  Criado", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Por favor informe os dados", Toast.LENGTH_LONG).show();
        }
    }
}