package com.example.hzkto.ball.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.example.hzkto.ball.R;
import com.example.hzkto.ball.system.DrawThread;

import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorMode;

import static android.graphics.Color.YELLOW;
import static com.example.hzkto.ball.MainActivity.closeKeyboard;
import static com.example.hzkto.ball.MainActivity.setToolbarTitle;
import static com.example.hzkto.ball.R.id.container;

/**
 * Created by hzkto on 11/25/2016.
 */

public class LightFragment extends Fragment {
    Button btnOk, btnClose, btnColor;
    TextView tvX, tvY, tvZ, tvStandart;
    View focusView;
    private int color[];

    public LightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_light, container, false);
        setToolbarTitle(getActivity(), R.string.light);
        initViews(view);
        setListeners();
        return view;
    }


    private void setListeners() {
        tvStandart.setOnClickListener(v -> {
            tvX.setText(String.valueOf(DrawThread.lightPoint.x));
            tvY.setText(String.valueOf(DrawThread.lightPoint.y));
            tvZ.setText(String.valueOf(DrawThread.lightPoint.z));
            if (color == null) {
                color = new int[]{255, 255, 0};
                btnColor.setBackground(new ColorDrawable(Color.YELLOW));
            }
        });
        btnClose.setOnClickListener(v -> {
            setToolbarTitle(getActivity(), R.string.sphere);
            getActivity().getSupportFragmentManager().popBackStack();
        });
        tvZ.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                return true;
            }
            return false;
        });
        btnOk.setOnClickListener(v -> {
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
            args.putDouble("lightX", Double.valueOf(tvX.getText().toString()));
            args.putDouble("lightY", Double.valueOf(tvY.getText().toString()));
            args.putDouble("lightZ", Double.valueOf(tvZ.getText().toString()));
            args.putIntArray("color", color);
            SphereFragment sphereFragment = new SphereFragment();
            sphereFragment.setArguments(args);
            getFragmentManager()
                    .beginTransaction()
                    .replace(container, sphereFragment)
                    .commit();
            getActivity().getSupportFragmentManager().popBackStack();
        });
        btnColor.setOnClickListener(v -> new ChromaDialog.Builder()
                .initialColor(YELLOW)
                .colorMode(ColorMode.RGB) // There's also ARGB and HSV
                .onColorSelected(intColor -> saveColor(intColor))
                .create()
                .show(getFragmentManager(), "ChromaDialog"));
    }

    private void saveColor(int intColor) {
        color = new int[3];
        color[0] = Color.red(intColor);
        color[1] = Color.green(intColor);
        color[2] = Color.blue(intColor);
        btnColor.setBackground(new ColorDrawable(intColor));
    }

    private void initViews(View v) {
        btnOk = (Button) v.findViewById(R.id.f_light_btnOk);
        btnClose = (Button) v.findViewById(R.id.f_light_btnClose);
        btnColor = (Button) v.findViewById(R.id.f_light_btnColor);
        tvX = (TextView) v.findViewById(R.id.f_light_X);
        tvY = (TextView) v.findViewById(R.id.f_light_Y);
        tvZ = (TextView) v.findViewById(R.id.f_light_Z);
        tvStandart = (TextView) v.findViewById(R.id.f_light_tvStandart);
    }
}
