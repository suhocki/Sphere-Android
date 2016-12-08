package com.example.hzkto.ball.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hzkto.ball.R;

import static com.example.hzkto.ball.MainActivity.closeKeyboard;
import static com.example.hzkto.ball.MainActivity.setToolbarTitle;
import static com.example.hzkto.ball.R.id.container;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScaleFragment extends MyFragment {
    Button btnOk, btnClose;
    TextView tvX, tvY, tvZ, tvStandart;
    View focusView;

    public ScaleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_scale, container, false);
        setToolbarTitle(getActivity(), R.string.scale);
        initViews(view);
        setListeners();
        return view;
    }

    private void setListeners() {
        tvStandart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    tvX.setText("1");
                    tvY.setText("1");
                    tvZ.setText("1");
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    setToolbarTitle(getActivity(), R.string.sphere);
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        } );
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    focusView = null;
                    if (tvX.getText().toString().equals("")) {
                        focusView = tvX;
                        focusView.requestFocus();
                        return;
                    }
                    if (tvY.getText().toString().equals("")) {
                        focusView = tvY;
                        focusView.requestFocus();
                        return;
                    }
                    if (tvZ.getText().toString().equals("")) {
                        focusView = tvZ;
                        focusView.requestFocus();
                        return;
                    }
                    closeKeyboard(getContext());
                    Bundle args = new Bundle();
                    args.putDouble("scaleX", Double.valueOf(tvX.getText().toString()));
                    args.putDouble("scaleY", Double.valueOf(tvY.getText().toString()));
                    args.putDouble("scaleZ", Double.valueOf(tvZ.getText().toString()));
                    SphereFragment sphereFragment = new SphereFragment();
                    sphereFragment.setArguments(args);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(container, sphereFragment)
                            .commit();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        } );
    }

    private void initViews(View v) {
        btnOk = (Button) v.findViewById(R.id.f_scale_btnOk);
        btnClose = (Button) v.findViewById(R.id.f_scale_btnClose);
        tvStandart = (TextView) v.findViewById(R.id.f_scale_tvStandart);
        tvX = (TextView) v.findViewById(R.id.f_scale_X);
        tvY = (TextView) v.findViewById(R.id.f_scale_Y);
        tvZ = (TextView) v.findViewById(R.id.f_scale_Z);
    }
}
