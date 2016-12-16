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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.hzkto.ball.fragments.LightFragment;
import com.example.hzkto.ball.fragments.MoveFragment;
import com.example.hzkto.ball.fragments.PerformanceFragment;
import com.example.hzkto.ball.fragments.ProjectionsFragment;
import com.example.hzkto.ball.fragments.RotateFragment;
import com.example.hzkto.ball.fragments.ScaleFragment;
import com.example.hzkto.ball.fragments.SphereFragment;

import static com.example.hzkto.ball.Constants.SETTINGS_RESET;
import static com.example.hzkto.ball.R.id.nav_light;
import static com.example.hzkto.ball.R.id.nav_move;
import static com.example.hzkto.ball.R.id.nav_performance;
import static com.example.hzkto.ball.R.id.nav_rotate;
import static com.example.hzkto.ball.R.id.nav_scale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    SphereFragment sphereFragment;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        initViews();
        initEtc();
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new SphereFragment())
                .commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initEtc() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        closeKeyboard(context);
                    }
                }).start();
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getFragmentManager().addOnBackStackChangedListener(new android.app.FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (sphereFragment.isVisible()) {
                    toolbar.setTitle(R.string.sphere);
                }
            }
        });
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case nav_move:
                showFragment(new MoveFragment());
                return true;
            case nav_performance:
                showFragment(new PerformanceFragment());
                return true;
            case nav_light:
                showFragment(new LightFragment());
                return true;
            case nav_scale:
                showFragment(new ScaleFragment());
                return true;
            case nav_rotate:
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
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                changeDrawerState();
                return true;
            case R.id.action_projections:
                showFragment(new ProjectionsFragment());
                return true;
            case R.id.action_reset:
                Bundle bundle = new Bundle();
                bundle.putInt("label", SETTINGS_RESET);
                SphereFragment sphereFragment = new SphereFragment();
                sphereFragment.setArguments(bundle);
                showFragment(sphereFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeDrawerState() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
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
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            toolbar.setTitle(R.string.sphere);
        }
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static void setToolbarTitle(Activity activity, int title) {
        Toolbar toolbarTitle = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbarTitle.setTitle(activity.getResources().getString(title));
    }
}
