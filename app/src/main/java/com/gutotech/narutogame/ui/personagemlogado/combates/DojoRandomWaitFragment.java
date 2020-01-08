package com.gutotech.narutogame.ui.personagemlogado.combates;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.narutogame.R;
import com.gutotech.narutogame.data.firebase.FirebaseConfig;
import com.gutotech.narutogame.ui.personagemlogado.personagem.PersonagemStatusFragment;
import com.gutotech.narutogame.data.model.BatalhaPVP;
import com.gutotech.narutogame.data.model.Personagem;
import com.gutotech.narutogame.data.model.Oponente;
import com.gutotech.narutogame.data.model.PersonagemOn;

import java.util.UUID;

public class DojoRandomWaitFragment extends Fragment {
    private DatabaseReference procurarOponentesReference;
    private ValueEventListener valueEventListenerProcurar;

    private ValueEventListener valueEventListenerFuiPego;
    private DatabaseReference euNaFilaReference;

    public DojoRandomWaitFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dojo_random_wait, container, false);
        TextView tituloSecao = getActivity().findViewById(R.id.tituloSecaoTextView);
        tituloSecao.setText("PROCURANDO OPONENTES");

        PersonagemOn.personagem.setIdBatalhaAtual("");

        // ENTRA NA FILA
        euNaFilaReference = FirebaseConfig.getDatabase()
                .child("dojo_random_wait")
                .child(PersonagemOn.personagem.getNick());
        euNaFilaReference.setValue(PersonagemOn.personagem);

        verificarSeFuiPego();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                    procurarOponentesReference = FirebaseConfig.getDatabase()
                            .child("dojo_random_wait");
                    procurarOponentes();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

        // PROCUR

        Button sairDaFilaButton = view.findViewById(R.id.sairDaFilaButton);
        sairDaFilaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                euNaFilaReference.removeValue();
                changeToFragment(new PersonagemStatusFragment());
            }
        });

        return view;
    }

    private void procurarOponentes() {
        valueEventListenerProcurar = procurarOponentesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Oponente.oponente = data.getValue(Personagem.class);

                    if (!Oponente.oponente.getNick().equals(PersonagemOn.personagem.getNick())) {
                        if (Oponente.oponente.getIdBatalhaAtual().equals("")) {
                            if (Oponente.oponente.getLevel() == PersonagemOn.personagem.getLevel()) {
                                procurarOponentesReference.removeEventListener(valueEventListenerProcurar);

                                String idBatalha = UUID.randomUUID().toString();

                                PersonagemOn.personagem.setFuiPego(false);
                                PersonagemOn.personagem.setIdBatalhaAtual(idBatalha);

                                Oponente.oponente.setFuiPego(true);
                                Oponente.oponente.setNickOponente(PersonagemOn.personagem.getNick());
                                Oponente.oponente.setIdBatalhaAtual(idBatalha);

                                DatabaseReference batalhaReference = FirebaseConfig.getDatabase()
                                        .child("batalha_dojo_pvp")
                                        .child(idBatalha);
                                BatalhaPVP batalhaPVP = new BatalhaPVP();
                                batalhaPVP.setQuemAtaca(PersonagemOn.personagem.getNick());
                                batalhaPVP.setNumeroDeQuemAtaca(0);
                                batalhaPVP.setPlayer1(PersonagemOn.personagem);
                                batalhaPVP.setPlayer2(Oponente.oponente);
                                batalhaReference.setValue(batalhaPVP);

                                euNaFilaReference.setValue(PersonagemOn.personagem);

                                DatabaseReference oponenteReference = FirebaseConfig.getDatabase()
                                        .child("dojo_random_wait")
                                        .child(Oponente.oponente.getNick());
                                oponenteReference.setValue(Oponente.oponente);

                                changeToFragment(new DojoBatalhaPVPFragment());
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void verificarSeFuiPego() {
        valueEventListenerFuiPego = euNaFilaReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PersonagemOn.personagem = dataSnapshot.getValue(Personagem.class);

                if (!PersonagemOn.personagem.getIdBatalhaAtual().equals("")) {
                    euNaFilaReference.removeEventListener(valueEventListenerFuiPego);
                    changeToFragment(new DojoBatalhaPVPFragment());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void changeToFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.conteiner, fragment).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        euNaFilaReference.removeEventListener(valueEventListenerFuiPego);
        procurarOponentesReference.removeEventListener(valueEventListenerProcurar);
    }
}
