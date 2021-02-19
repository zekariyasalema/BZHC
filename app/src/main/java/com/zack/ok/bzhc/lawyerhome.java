package com.zack.ok.bzhc;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class lawyerhome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clienthome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(lawyerhome.this,lcontactus.class);
                startActivity(intent);
            }
        });
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.lawrhpagm));
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clienthome, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.action_logout){
            SharedPrefManager.getInstance(this).logout();
            finish();
            startActivity(new Intent(this,MainActivity.class));
            return true;

        }
        if(id==R.id.action_changelangl){
            showToolsDialog();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_appointment) {
            Intent intent=new Intent(lawyerhome.this,lappointment.class);
            startActivity(intent);

        } else if (id == R.id.nav_comment) {
            Intent intent=new Intent(lawyerhome.this,lcomment.class);
            startActivity(intent);

        } else if (id == R.id.nav_tools) {
            showToolsDialog();

        } else if (id == R.id.nav_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Bale Zone High Court Appointment Booking");
            // Replace * BZHC * with  the app link
            String message = "\nLet me recommend you this application *BZHC* \n\n";

            i.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(i, getString(R.string.shareusingl)));

        } else if (id == R.id.nav_contact) {
            Intent intent=new Intent(lawyerhome.this,lcontactus.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showToolsDialog() {
        final String[] listitems={"English","አማርኛ","Afaan Oromoo"};
        AlertDialog.Builder builder=new AlertDialog.Builder(lawyerhome.this);
        builder.setTitle("Choose Language...");
        builder.setSingleChoiceItems(listitems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    setLocale("en");
                    recreate();
                }
                if(i==1){
                    setLocale("am");
                    recreate();
                }
                if(i==2){
                    setLocale("om");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }
    public void loadLocale(){
        SharedPreferences preferences=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language= preferences.getString("My_Lang","");
        setLocale(language);
    }

}

