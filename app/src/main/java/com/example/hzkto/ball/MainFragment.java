package com.example.hzkto.ball;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hzkto.ball.system.MySurfaceView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    MySurfaceView mySurfaceView;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mySurfaceView = (MySurfaceView) view.findViewById(R.id.mySurfaceView);
        mySurfaceView.setZOrderOnTop(false);
        return view;
    }

}
