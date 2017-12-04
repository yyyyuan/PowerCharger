package com.example.yuanbo.powercharger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yuanbo on 12/3/17.
 */

public class TermsFrag extends Fragment {
    public static TermsFrag newInstance() {
        TermsFrag fragment = new TermsFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
        return inflater.inflate(R.layout.terms_frag,container,false);
    }

    public TermsFrag() {}
}
