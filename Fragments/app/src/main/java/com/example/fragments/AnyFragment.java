package com.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class AnyFragment extends Fragment {

    static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";

    static AnyFragment newInstance(int page){
        AnyFragment fragment = new AnyFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_SECTION_NUMBER, page);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutID;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 0:
                layoutID = R.layout.fragment_a;
                break;
            case 1:
                layoutID = R.layout.fragment_b;
                break;
            default:
                layoutID = 0;
                break;
        }
        if (layoutID != 0){
            return inflater.inflate(layoutID, container, false);
        } else {
            return null;
        }
    }
}
