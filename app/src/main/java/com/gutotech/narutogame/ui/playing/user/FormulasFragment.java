package com.gutotech.narutogame.ui.playing.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.ui.SectionFragment;
import com.gutotech.narutogame.utils.FragmentUtil;

public class FormulasFragment extends Fragment implements SectionFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=  inflater.inflate(R.layout.fragment_formulas, container, false);

        FragmentUtil.setSectionTitle(getActivity(), R.string.section_formulas);

        return root;
    }

    @Override
    public int getDescription() {
        return R.string.game_formulas;
    }
}
