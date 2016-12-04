package com.example.hzkto.ball.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.example.hzkto.ball.R;

import static com.example.hzkto.ball.MainActivity.setToolbarTitle;
import static com.example.hzkto.ball.R.id.container;

/**
 * A simple {@link Fragment} subclass.
 */
public class Projection1Fragment extends Fragment {
    Button btnNext, btnClose;

    public Projection1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.f_projections_1, container, false);
        setToolbarTitle(getActivity(), R.string.projections);
        initViews(view);
        setListeners();
        return view;
    }

    private void initViews(View v) {
        btnNext = (Button) v.findViewById(R.id.f_projections_1_btnNext);
        btnClose = (Button) v.findViewById(R.id.f_projections_1_btnClose);
    }

    private void setListeners() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToolbarTitle(getActivity(), R.string.sphere);
                getFragmentManager()
                        .beginTransaction()
                        .replace(container, new SphereFragment())
                        .commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(container, new Projection2Fragment())
                        .commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
