package com.vagner.tocaaquela.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.vagner.tocaaquela.R;
import com.vagner.tocaaquela.fragment.EventoFragment;
import com.vagner.tocaaquela.fragment.MinhasMusicasFragment;
import com.vagner.tocaaquela.fragment.PerfilFragment;
import com.vagner.tocaaquela.fragment.TesteFragment;
import com.vagner.tocaaquela.utils.BottomNavigationViewHelper;
import com.vagner.tocaaquela.utils.FragmentoUtils;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_eventos:
                    FragmentoUtils.replace(MainActivity.this, new EventoFragment());
                    return true;
                case R.id.navigation_minhas_musicas:
                    FragmentoUtils.replace(MainActivity.this, new MinhasMusicasFragment());
                    return true;
                case R.id.navigation_perfil:
                    FragmentoUtils.replace(MainActivity.this, new TesteFragment());
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        FragmentoUtils.replace(MainActivity.this, new EventoFragment());
    }

}
