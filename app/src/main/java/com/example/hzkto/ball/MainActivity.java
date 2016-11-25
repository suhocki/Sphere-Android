package com.example.hzkto.ball;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.hzkto.ball.fragments.ConfigFragment;
import com.example.hzkto.ball.fragments.LightFragment;
import com.example.hzkto.ball.fragments.PerformanceFragment;
import com.example.hzkto.ball.fragments.SphereFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SphereFragment sphereFragment;
    ConfigFragment configFragment;
    LightFragment lightFragment;
    PerformanceFragment performanceFragment;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        setSupportActionBar(toolbar);
        initViews();
        initEtc();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, sphereFragment)
                .commit();
    }

    private void initEtc() {
        sphereFragment = new SphereFragment();
        configFragment = new ConfigFragment();
        lightFragment = new LightFragment();
        performanceFragment = new PerformanceFragment();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                new Thread(new Runnable() {
                    public void run() {
                        closeKeyboard();
                    }
                }).start();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_config) {
            if (!configFragment.isAdded()) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, configFragment)
                        .addToBackStack(null)
                        .commit();
            }
            drawer.closeDrawers();
        }
        if (id == R.id.nav_performance) {
            if (!performanceFragment.isAdded()) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, performanceFragment)
                        .addToBackStack(null)
                        .commit();
            }
            drawer.closeDrawers();
        }
        if (id == R.id.nav_light) {
            if (!lightFragment.isAdded()) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, lightFragment)
                        .addToBackStack(null)
                        .commit();
            }
            drawer.closeDrawers();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_config) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
