package com.example.hzkto.ball.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.example.hzkto.ball.R;

/**
 * Created by user on 12/7/2016.
 */

public class MyFragment extends Fragment {


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_projections).setVisible(false);
        menu.findItem(R.id.action_reset).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
}
