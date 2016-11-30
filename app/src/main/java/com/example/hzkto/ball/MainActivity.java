package com.example.hzkto.ball;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.hzkto.ball.fragments.LightFragment;
import com.example.hzkto.ball.fragments.MoveFragment;
import com.example.hzkto.ball.fragments.PerformanceFragment;
import com.example.hzkto.ball.fragments.RotateFragment;
import com.example.hzkto.ball.fragments.ScaleFragment;
import com.example.hzkto.ball.fragments.SphereFragment;

import static com.example.hzkto.ball.R.id.nav_light;
import static com.example.hzkto.ball.R.id.nav_move;
import static com.example.hzkto.ball.R.id.nav_performance;
import static com.example.hzkto.ball.R.id.nav_rotate;
import static com.example.hzkto.ball.R.id.nav_scale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LightFragment lightFragment;
    MoveFragment moveFragment;
    PerformanceFragment performanceFragment;
    RotateFragment rotateFragment;
    ScaleFragment scaleFragment;
    SphereFragment sphereFragment;

    public static Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        setSupportActionBar(toolbar);
        initViews();
        initEtc();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new SphereFragment())
                .commit();

    }

    private void initEtc() {
        sphereFragment = new SphereFragment();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                new Thread(() -> {
                    closeKeyboard(context);
                }).start();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getFragmentManager().addOnBackStackChangedListener(() -> {
            if (sphereFragment.isVisible()) {
                toolbar.setTitle(R.string.sphere);
            }

        });
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case nav_move:
                toolbar.setTitle(R.string.move);
                showFragment(new MoveFragment());
                return true;
            case nav_performance:
                toolbar.setTitle(R.string.performance);
                showFragment(new PerformanceFragment());
                return true;
            case nav_light:
                toolbar.setTitle(R.string.light);
                showFragment(new LightFragment());
                return true;
            case nav_scale:
                toolbar.setTitle(R.string.scale);
                showFragment(new ScaleFragment());
                return true;
            case nav_rotate:
                toolbar.setTitle(R.string.rotate);
                showFragment(new RotateFragment());
                return true;
            default:
                return false;
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
        drawer.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == nav_move) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void closeKeyboard(Context context) {
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
