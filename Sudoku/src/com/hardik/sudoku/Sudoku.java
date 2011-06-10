package com.hardik.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;



import java.lang.ref.PhantomReference;

public class Sudoku extends Activity implements OnClickListener
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Set up click listeners for all the buttons
        View btnContinue = findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(this);
        View btnNew = findViewById(R.id.btn_new);
        btnNew.setOnClickListener(this);
        View btnAbout = findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(this);
        View btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_about:
                Intent i = new Intent(this, About.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(this, Prefs.class));
                return true;
        }
        return false;
    }

}
