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
import android.widget.Switch;
import android.widget.TextView;

import com.example.hzkto.ball.R;

import static com.example.hzkto.ball.MainActivity.setToolbarTitle;
import static com.example.hzkto.ball.R.id.container;

/**
 * Created by hzkto on 11/24/2016.
 */

public class PerformanceFragment extends MyFragment {
    Button btnOk, btnClose;
    TextView tvStandart, tvPolygonCount;
    SeekBar seekBar;
    Switch swReflect, swDelLines;
    int polygonsOnCircle[] = {18, 36, 54, 72, 124, 172, 212, 256, 312, 312};

    public PerformanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_performance, container, false);
        setToolbarTitle(getActivity(), R.string.performance);
        initViews(view);
        setListeners();
        return view;
    }

    private void initViews(View v) {
        btnOk = (Button) v.findViewById(R.id.f_performance_btnOk);
        btnClose = (Button) v.findViewById(R.id.f_performance_btnClose);
        tvStandart = (TextView) v.findViewById(R.id.f_performance_tvStandart);
        seekBar = (SeekBar) v.findViewById(R.id.f_performance_seekBar);
        tvPolygonCount = (TextView) v.findViewById(R.id.f_performance_tvPolygonCount);
        swDelLines = (Switch) v.findViewById(R.id.f_performance_swDelLines);
        swReflect = (Switch) v.findViewById(R.id.f_performance_swReflect);
    }

    private void setListeners() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String polygonCount = String.valueOf(polygonsOnCircle[progress] * polygonsOnCircle[progress] / 2);
                tvPolygonCount.setText(polygonCount);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        swDelLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        swReflect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                {
                    if (isChecked) {
                        swDelLines.setChecked(true);
                        swDelLines.setEnabled(false);
                    } else {
                        swDelLines.setEnabled(true);
                    }
                }
            }
        });
        tvStandart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) seekBar.setProgress(0, true);
                    else seekBar.setProgress(0);
                    swDelLines.setEnabled(false);
                    swDelLines.setChecked(true);
                    swReflect.setChecked(true);
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
                    Bundle args = new Bundle();
                    args.putInt("polygonsCount", polygonsOnCircle[seekBar.getProgress()]);
                    args.putBoolean("reflect", swReflect.isChecked());
                    args.putBoolean("invisLines", swDelLines.isChecked());
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

}
