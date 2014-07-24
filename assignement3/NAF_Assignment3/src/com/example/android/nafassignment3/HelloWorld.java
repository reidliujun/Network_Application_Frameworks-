package com.example.android.nafassignment3;

import com.example.android.effectivenavigation.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Helloworld!
 */
public class HelloWorld extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.helloworld, container, false);
        return rootView;
    }
}