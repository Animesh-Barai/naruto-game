package com.gutotech.narutogame.ui.playing;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.data.model.CharOn;
import com.gutotech.narutogame.ui.QuestionDialog;
import com.gutotech.narutogame.ui.SectionFragment;
import com.gutotech.narutogame.ui.adapter.ProfilesAdapter;
import com.gutotech.narutogame.utils.FragmentUtil;
import com.gutotech.narutogame.utils.StorageUtil;

public class ChangeImageFragment extends Fragment implements SectionFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_image, container, false);

        RecyclerView profilesRecyclerView = view.findViewById(R.id.profilesRecyclerView);
        profilesRecyclerView.setHasFixedSize(true);

        ProfilesAdapter adapter = new ProfilesAdapter(CharOn.character.getNinja(), profile -> {
            QuestionDialog dialog = new QuestionDialog("Deseja alterar a imagem do seu personagem para escolhida?");
            dialog.setOnOkButton(v -> {
                CharOn.character.setProfile(profile);

                ImageView profileImageView = getActivity().findViewById(
                        R.id.profileImageView);

                StorageUtil.downloadProfile(getActivity(), profileImageView,
                        CharOn.character.getNinja().getId(), profile);

                CharOn.character.salvar();

                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            });
            dialog.show(getFragmentManager(), "QuestionDialog");
        });
        profilesRecyclerView.setAdapter(adapter);

        FragmentUtil.setSectionTitle(getActivity(), R.string.section_change_image);

        return view;
    }

    @Override
    public int getDescription() {
        return 0;
    }
}
