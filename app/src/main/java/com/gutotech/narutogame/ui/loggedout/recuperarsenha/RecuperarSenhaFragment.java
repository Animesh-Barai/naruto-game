package com.gutotech.narutogame.ui.loggedout.recuperarsenha;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.databinding.FragmentRecuperarSenhaBinding;
import com.gutotech.narutogame.ui.loggedout.AuthListener;

import es.dmoral.toasty.Toasty;

public class RecuperarSenhaFragment extends Fragment implements AuthListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRecuperarSenhaBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recuperar_senha, container, false);

        View root = binding.getRoot();

        RecuperarSenhaViewModel mViewModel = ViewModelProviders.of(this).get(RecuperarSenhaViewModel.class);
        mViewModel.setAuthListener(this);

        binding.setViewModel(mViewModel);

        return root;
    }


    @Override
    public void onStarted() {
    }

    @Override
    public void onSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.password_was_sent);
        builder.setMessage(R.string.password_sent_description);
        builder.create();
        builder.show();
    }

    @Override
    public void onFailure(int resId) {
        Toasty.error(getActivity(), R.string.email_not_found, Toasty.LENGTH_SHORT).show();
    }
}
