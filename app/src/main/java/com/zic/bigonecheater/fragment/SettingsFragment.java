package com.zic.bigonecheater.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zic.bigonecheater.R;
import com.zic.bigonecheater.activity.DialogActivity;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btnShowBubble = (Button) rootView.findViewById(R.id.btnShowBubble);
        Button btnShowTools = (Button) rootView.findViewById(R.id.btnShowTools);

        btnShowBubble.setOnClickListener(this);
        btnShowTools.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnShowBubble:
                break;
            case R.id.btnShowTools:
                startActivity(new Intent(getActivity(), DialogActivity.class));
                break;
        }
    }
}
