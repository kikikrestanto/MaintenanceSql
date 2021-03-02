package com.example.maintenancesql.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.maintenancesql.Fragments.HomeFragment;
import com.example.maintenancesql.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeAct extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameHomeContainer,new HomeFragment()).commit();
        init();
    }

    private void init() {
        fab = findViewById(R.id.id_fab);
        fab.setOnClickListener(v->{
            Intent i = new Intent(HomeAct.this,addPostAct.class);
            startActivity(i);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go previous activty
        return super.onSupportNavigateUp();
    }

}
