package com.shiyan.app.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiyan.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends Fragment {

    public static LiveFragment newInstance(){
        LiveFragment fragment = new LiveFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

}
