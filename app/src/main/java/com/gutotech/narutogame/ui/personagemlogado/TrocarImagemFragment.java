package com.gutotech.narutogame.ui.personagemlogado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.ui.adapter.ProfilesAdapter;
import com.gutotech.narutogame.util.StorageUtil;
import com.gutotech.narutogame.util.Helper;
import com.gutotech.narutogame.data.model.PersonagemOn;

public class TrocarImagemFragment extends Fragment {

    public TrocarImagemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trocar_imagem, container, false);

        GridView gridView = view.findViewById(R.id.profilesGridView);
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

                        ImageView profileLogadoimageView = getActivity().findViewById(R.id.profilePersonagemOnImageView);
                        StorageUtil.baixarProfile(getActivity(), profileLogadoimageView, PersonagemOn.personagem.getIdProfile(), PersonagemOn.personagem.getFotoAtual());

                        PersonagemOn.personagem.salvar();

                        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                        drawer.openDrawer(GravityCompat.START);
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
}
