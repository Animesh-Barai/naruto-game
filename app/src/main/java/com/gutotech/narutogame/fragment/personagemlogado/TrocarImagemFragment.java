package com.gutotech.narutogame.fragment.personagemlogado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.storage.StorageReference;
import com.gutotech.narutogame.R;
import com.gutotech.narutogame.activity.PersonagemLogadoActivity;
import com.gutotech.narutogame.adapter.ProfilesAdapter;
import com.gutotech.narutogame.config.ConfigFirebase;
import com.gutotech.narutogame.config.Storage;
import com.gutotech.narutogame.fragment.personagemlogado.personagem.PersonagemStatusFragment;
import com.gutotech.narutogame.model.Personagem;
import com.gutotech.narutogame.publicentities.Helper;
import com.gutotech.narutogame.publicentities.PersonagemOn;

public class TrocarImagemFragment extends Fragment {
    private GridView gridView;

    public TrocarImagemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trocar_imagem, container, false);

        gridView = view.findViewById(R.id.profilesGridView);
        ProfilesAdapter profilesAdapter = new ProfilesAdapter(getActivity(), Helper.quantasImagens(PersonagemOn.personagem.getIdProfile()));
        gridView.setAdapter(profilesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Aviso");
                builder.setMessage("Deseja alterar a imagem do seu personagem para escolhida?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PersonagemOn.personagem.setFotoAtual(position + 1);

                        Storage.baixarProfile(getActivity(), PersonagemLogadoActivity.profileLogadoimageView, PersonagemOn.personagem.getIdProfile(), PersonagemOn.personagem.getFotoAtual());

                        PersonagemOn.personagem.salvar();

                        changeFragment(new PersonagemStatusFragment());
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.setCancelable(false);
                builder.create();
                builder.show();
            }
        });

        return view;
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.conteiner, fragment);
        transaction.commit();
    }
}
