package com.vagner.tocaaquela.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vagner.tocaaquela.R;


public class MenuArtistaActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String ARTIST_NAME = "com.vagner.tocaaquela.artistid";
    public static final String ARTIST_ID = "com.vagner.tocaaquela.artistid";

    private Boolean isFabOpen = false;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private FirebaseAuth firebaseAuth;

    DatabaseReference databaseArtists;


    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_artista);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseArtists = FirebaseDatabase.getInstance().getReference("events");


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backword);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forword);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        //initializing firebase authentication object

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginArtistaActivityTeste.class));
        }
        else {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmailMenu);
            if(user.getDisplayName()!=null){
                textViewUserEmail.setText(" Olá "+user.getDisplayName());
            }
            else{
                textViewUserEmail.setText(" Olá "+user.getEmail());
            }

        }



    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:
                //starting the activity with intent
                startActivity(new Intent(getApplicationContext(), EventActivity.class));


                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab2:
                Intent intencao2 = new Intent(this, NovaMusicaActivity.class);
                startActivity(intencao2);

                Log.d("Raj", "Fab 2");
                break;
        }
    }
    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_menu_artista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_pesquisar) {
            Intent intent = new Intent(this,PesquisaMusicaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_logout) {
             firebaseAuth.signOut();
             finish();
             startActivity(new Intent(this, MainActivity.class));
             return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
