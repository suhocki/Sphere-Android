package com.example.hzkto.ball.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hzkto.ball.R;

import static com.example.hzkto.ball.Constants.PROJECTION_AXONOMETRIC;
import static com.example.hzkto.ball.Constants.PROJECTION_FRONT;
import static com.example.hzkto.ball.Constants.PROJECTION_HORIZONTAL;
import static com.example.hzkto.ball.Constants.PROJECTION_OBLIQUE;
import static com.example.hzkto.ball.Constants.PROJECTION_PERSPECTIVE;
import static com.example.hzkto.ball.Constants.PROJECTION_PROFILE;
import static com.example.hzkto.ball.Constants.SETTINGS_PERFOMANCE;
import static com.example.hzkto.ball.MainActivity.setToolbarTitle;
import static com.example.hzkto.ball.R.id.container;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectionsFragment extends MyFragment {
    Button btnAxon, btnOblique, btnPersp, btnFrontal, btnHoriz, btnProfile;
    TextView tvAxonFi, tvAxonPsi, tvObliqueL, tvObliqueAlpha, tvPerspectD, tvPerspectQ, tvPerspectFi, tvPerspectPsi;
    Bundle bundle;
    public ProjectionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setToolbarTitle(getActivity(), R.string.projections);
        View view = inflater.inflate(R.layout.f_projections, container, false);
        initViews(view);
        setListeners();
        return view;
    }

    private void setListeners() {
        btnPersp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvPerspectFi.getText().toString().isEmpty() || tvPerspectPsi.getText().toString().isEmpty() ||
                        tvPerspectQ.getText().toString().isEmpty() ||  tvPerspectD.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
                    return;
                }
                bundle = new Bundle();
                bundle.putInt("label", PROJECTION_PERSPECTIVE);
                bundle.putDouble("fi", Double.valueOf(tvPerspectFi.getText().toString()));
                bundle.putDouble("psi", Double.valueOf(tvPerspectPsi.getText().toString()));
                bundle.putDouble("q", Double.valueOf(tvPerspectQ.getText().toString()));
                bundle.putDouble("d", Double.valueOf(tvPerspectD.getText().toString()));
                showFragment();
            }
        });
        btnOblique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvObliqueAlpha.getText().toString().isEmpty() || tvObliqueL.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
                    return;
                }
                bundle = new Bundle();
                bundle.putInt("label", PROJECTION_OBLIQUE);
                bundle.putDouble("alpha", Double.valueOf(tvObliqueAlpha.getText().toString()));
                bundle.putDouble("l", Double.valueOf(tvObliqueL.getText().toString()));
                showFragment();
            }
        });
        btnAxon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvAxonFi.getText().toString().isEmpty() || tvAxonPsi.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
                    return;
                }
                bundle = new Bundle();
                bundle.putInt("label", PROJECTION_AXONOMETRIC);
                bundle.putDouble("fi", Double.valueOf(tvAxonFi.getText().toString()));
                bundle.putDouble("psi", Double.valueOf(tvAxonPsi.getText().toString()));
                showFragment();
            }
        });
        btnFrontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putInt("label", PROJECTION_FRONT);
                showFragment();
            }
        });
        btnHoriz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putInt("label", PROJECTION_HORIZONTAL);
                showFragment();
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putInt("label", PROJECTION_PROFILE);
                showFragment();
            }
        });
    }

    private void showFragment() {
        SphereFragment sphereFragment = new SphereFragment();
        sphereFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(container, sphereFragment)
                .commit();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void initViews(View view) {
        btnAxon = (Button) view.findViewById(R.id.proj_axonometric_back_ok);
        btnOblique = (Button) view.findViewById(R.id.proj_oblique_back_ok);
        btnPersp = (Button) view.findViewById(R.id.proj_perspective_back_ok);
        btnProfile = (Button) view.findViewById(R.id.f_projections_profile);
        btnHoriz = (Button) view.findViewById(R.id.f_projections_horizontal);
        btnFrontal = (Button) view.findViewById(R.id.f_projections_frontal);
        tvAxonFi = (TextView) view.findViewById(R.id.proj_axonometric_back_phi);
        tvAxonPsi = (TextView) view.findViewById(R.id.proj_axonometric_back_psi);
        tvObliqueAlpha = (TextView) view.findViewById(R.id.proj_oblique_back_alpha);
        tvObliqueL = (TextView) view.findViewById(R.id.proj_oblique_back_l);
        tvPerspectD = (TextView) view.findViewById(R.id.proj_perspective_back_d);
        tvPerspectFi = (TextView) view.findViewById(R.id.proj_perspective_back_phi);
        tvPerspectPsi = (TextView) view.findViewById(R.id.proj_perspective_back_psi);
        tvPerspectQ = (TextView) view.findViewById(R.id.proj_perspective_back_q);
    }


}
