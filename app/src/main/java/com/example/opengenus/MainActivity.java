package com.example.opengenus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomeFragment homeFragment;
    private QnAFragment qnAFragment;
    private FeedFragment feedFragment;

    // Building the Dialog Window
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button okay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
        Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("IS_FIRST_TIME", true)) {
            createHelpDialog();
            sharedPreferences.edit().putBoolean("IS_FIRST_TIME", false).apply();
        }

        tabLayout = findViewById(R.id.tabMode);
        viewPager = findViewById(R.id.viewPager);
        homeFragment = new HomeFragment();
        qnAFragment = new QnAFragment();
        feedFragment = new FeedFragment();

        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    public void createHelpDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View helpPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        okay = helpPopupView.findViewById(R.id.okay);

        dialogBuilder.setView(helpPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // defining the okay button
                dialog.dismiss();
            }
        });
    }


    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");

        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        return builder;
    }

    // Code for the Action Option "Help" in action bar

    // method to inflate the options menu when
    // the user opens the menu for the first time
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "Help Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, help.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}