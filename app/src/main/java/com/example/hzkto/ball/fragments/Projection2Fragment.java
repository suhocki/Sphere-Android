package com.example.hzkto.ball.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hzkto.ball.R;

import static com.example.hzkto.ball.MainActivity.setToolbarTitle;
import static com.example.hzkto.ball.R.id.container;

/**
 * A simple {@link Fragment} subclass.
 */
public class Projection2Fragment extends Fragment {
    Button btnPrev, btnClose;

    public Projection2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.f_projections_2, container, false);
        setToolbarTitle(getActivity(), R.string.projections);
        initViews(view);
        setListeners();
        return view;
    }

    private void initViews(View v) {
        btnPrev = (Button) v.findViewById(R.id.f_projections_2_btnPrev);
        btnClose = (Button) v.findViewById(R.id.f_projections_2_btnClose);
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
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(container, new Projection1Fragment())
                        .commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
