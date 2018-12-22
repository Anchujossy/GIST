package com.example.admin.myapplication.activity;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.admin.myapplication.fragments.MainFragment;
import com.example.admin.myapplication.R;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentTransacction();
    }

    /**
     * method is used for fragment transaction
     */
    private void fragmentTransacction() {
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.rl_fragment_container, mainFragment);
        fragmentTransaction.commit();
    }


}
