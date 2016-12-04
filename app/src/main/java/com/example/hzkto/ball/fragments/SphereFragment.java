package com.example.hzkto.ball.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hzkto.ball.R;
import com.example.hzkto.ball.system.MySurfaceView;

import static com.example.hzkto.ball.MainActivity.setToolbarTitle;


/**
 * A simple {@link Fragment} subclass.
 */
public class SphereFragment extends Fragment {
    MySurfaceView mySurfaceView;

    public SphereFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_sphere, container, false);
        mySurfaceView = (MySurfaceView) view.findViewById(R.id.f_sphere_svFrontal);
        mySurfaceView.setZOrderOnTop(false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mySurfaceView.setBundle(bundle);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarTitle(getActivity(), R.string.sphere);
    }


}
