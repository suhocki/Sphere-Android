package com.example.hzkto.ball.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.hzkto.ball.R;
import com.example.hzkto.ball.system.DrawThread;

import static com.example.hzkto.ball.R.id.container;

/**
 * Created by hzkto on 11/23/2016.
 */

public class ConfigFragment extends Fragment {
    Button btnOk, btnClose;
    TextView tvX, tvY, tvZ, tvRadius, tvStandart;
    View focusView;

    public ConfigFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_config, container, false);
        initViews(view);
        setListeners();
        return view;
    }

    private void setListeners() {
        tvStandart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRadius.setText(String.valueOf(DrawThread.radius));
                tvX.setText(String.valueOf(DrawThread.center.x));
                tvY.setText(String.valueOf(DrawThread.center.y));
                tvZ.setText(String.valueOf(DrawThread.center.z));
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        tvRadius.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKeyboard();
                    btnOk.callOnClick();
                    return true;
                }
                return false;
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if (tvRadius.getText().toString().equals("")) {
                    focusView = tvRadius;
                    focusView.requestFocus();
                    return;
                }

                Bundle args = new Bundle();
                args.putDouble("X", Double.valueOf(tvX.getText().toString()));
                args.putDouble("Y", Double.valueOf(tvY.getText().toString()));
                args.putDouble("Z", Double.valueOf(tvZ.getText().toString()));
                args.putDouble("radius", Double.valueOf(tvRadius.getText().toString()));
                SphereFragment sphereFragment = new SphereFragment();
                sphereFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(container, sphereFragment)
                        .commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void initViews(View v) {
        btnOk = (Button) v.findViewById(R.id.f_config_btnOk);
        btnClose = (Button) v.findViewById(R.id.f_config_btnClose);
        tvX = (TextView) v.findViewById(R.id.f_config_centerX);
        tvY = (TextView) v.findViewById(R.id.f_config_centerY);
        tvZ = (TextView) v.findViewById(R.id.f_config_centerZ);
        tvStandart = (TextView) v.findViewById(R.id.f_config_tvStandart);
        tvRadius = (TextView) v.findViewById(R.id.f_config_radius);
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
