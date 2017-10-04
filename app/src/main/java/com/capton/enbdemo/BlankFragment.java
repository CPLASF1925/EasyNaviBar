package com.capton.enbdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BlankFragment extends Fragment {

    TextView textView;
    int index;

    public BlankFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!TextUtils.isEmpty(getArguments().getInt("index")+""))
            index=getArguments().getInt("index");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_blank, container, false);

        textView= (TextView) view.findViewById(R.id.text);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setText("This is a Fragment Index: "+index);
        switch (index){
            case 0:
                textView.setBackgroundColor(getResources().getColor(R.color.blue_light));
                break;
            case 1:
                textView.setBackgroundColor(getResources().getColor(R.color.green_dark));
                break;
            case 2:
                textView.setBackgroundColor(getResources().getColor(R.color.pink));
                break;
            case 3:
                textView.setBackgroundColor(getResources().getColor(R.color.red_light));
                break;
        }

        return view;

    }


}
