package com.gutotech.narutogame.ui.onlinecharacter.sensei;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.narutogame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DesafiosFragment extends Fragment {


    public DesafiosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_desafios, container, false);
    }

}
