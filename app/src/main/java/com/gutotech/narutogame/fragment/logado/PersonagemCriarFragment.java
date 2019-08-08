package com.gutotech.narutogame.fragment.logado;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.narutogame.R;
import com.gutotech.narutogame.adapter.ProfilesPequenasAdapter;
import com.gutotech.narutogame.config.ConfigFirebase;
import com.gutotech.narutogame.config.Storage;
import com.gutotech.narutogame.model.Atributos;
import com.gutotech.narutogame.model.Jutsu;
import com.gutotech.narutogame.model.Personagem;
import com.gutotech.narutogame.model.Player;
import com.gutotech.narutogame.model.ResumoCombates;
import com.gutotech.narutogame.model.ResumoMissoes;
import com.gutotech.narutogame.publicentities.Helper;
import com.gutotech.narutogame.publicentities.PersonagemOn;
import com.gutotech.narutogame.publicentities.PlayerOn;

import java.util.ArrayList;
import java.util.List;

public class PersonagemCriarFragment extends Fragment {
    private Personagem personagem;
    private Atributos atributos;

    private EditText nickEditText;

    private TextView vilaSelecionadaTextView;
    private String vilaSelecionada;
    private int numVila;

    private String classeSelecionada;

    private ImageView profileImageView;
    private TextView nomePersonagemSelecionadoTextView;

    private GridView profilesPequenasGridView;
    private ProfilesPequenasAdapter adapter;
    private List<Integer> pequenasLista = new ArrayList<>();
    private List<Integer> profilesGrupoAtual = new ArrayList<>();

    private TextView grupoAtualTextView;
    private int grupoAtual = 0;

    private FirebaseAuth auth;

    public PersonagemCriarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personagem_criar, container, false);

        auth = ConfigFirebase.getAuth();

        personagem = new Personagem();
        atributos = new Atributos();
        atributos.setTaijutsu(10);
        atributos.setBukijutsu(1);
        atributos.setNinjutsu(1);
        atributos.setGenjutsu(1);
        atributos.setForca(5);
        atributos.setAgilidade(3);
        atributos.setInteligencia(1);
        atributos.setSelo(3);
        atributos.setResistencia(1);
        atributos.setEnergia(10);
        classeSelecionada = "Taijutsu";
        vilaSelecionada = "Folha";
        numVila = 1;
        personagem.setIdProfile(1);

        nickEditText = view.findViewById(R.id.nickCriarEditText);

        vilaSelecionadaTextView = view.findViewById(R.id.vilaSelecionadaTextView);
        RadioGroup vilasRadioGroup = view.findViewById(R.id.vilasRadioGroup);
        vilasRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.folhaRadioButton:
                        vilaSelecionada = "Folha";
                        numVila = 1;
                        vilaSelecionadaTextView.setText(vilaSelecionada);
                        break;
                    case R.id.areiaRadioButton:
                        vilaSelecionada = "Areia";
                        numVila = 2;
                        vilaSelecionadaTextView.setText(vilaSelecionada);
                        break;
                    case R.id.nevoaRadioButton:
                        vilaSelecionada = "Névoa";
                        numVila = 3;
                        vilaSelecionadaTextView.setText(vilaSelecionada);
                        break;
                    case R.id.pedraRadioButton:
                        vilaSelecionada = "Pedra";
                        numVila = 4;
                        vilaSelecionadaTextView.setText(vilaSelecionada);
                        break;
                    case R.id.nuvemRadioButton:
                        vilaSelecionada = "Nuvem";
                        numVila = 5;
                        vilaSelecionadaTextView.setText(vilaSelecionada);
                        break;
                    case R.id.akatsukiRadioButton:
                        vilaSelecionada = "Akatsuki";
                        numVila = 6;
                        vilaSelecionadaTextView.setText(vilaSelecionada);
                        break;
                    case R.id.somRadioButton:
                        vilaSelecionada = "Som";
                        numVila = 7;
                        vilaSelecionadaTextView.setText(vilaSelecionada);
                        break;
                    case R.id.chuvaRadioButton:
                        vilaSelecionada = "Chuva";
                        numVila = 8;
                        vilaSelecionadaTextView.setText(vilaSelecionada);
                        break;
                }
            }
        });

        RadioGroup classesRadioGroup = view.findViewById(R.id.classesRadioGroup);
        classesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.taiRadioButton:
                        classeSelecionada = "Taijutsu";
                        atributos.setTaijutsu(10);
                        atributos.setBukijutsu(1);
                        atributos.setNinjutsu(1);
                        atributos.setGenjutsu(1);
                        atributos.setForca(5);
                        atributos.setAgilidade(3);
                        atributos.setInteligencia(1);
                        atributos.setSelo(3);
                        break;
                    case R.id.ninRadioButton:
                        classeSelecionada = "Ninjutsu";
                        atributos.setTaijutsu(1);
                        atributos.setBukijutsu(1);
                        atributos.setNinjutsu(10);
                        atributos.setGenjutsu(1);
                        atributos.setForca(1);
                        atributos.setAgilidade(3);
                        atributos.setInteligencia(5);
                        atributos.setSelo(3);
                        break;
                    case R.id.genRadioButton:
                        classeSelecionada = "Genjutsu";
                        atributos.setTaijutsu(1);
                        atributos.setBukijutsu(1);
                        atributos.setNinjutsu(1);
                        atributos.setGenjutsu(10);
                        atributos.setForca(1);
                        atributos.setAgilidade(3);
                        atributos.setInteligencia(5);
                        atributos.setSelo(3);
                        break;
                    case R.id.bukRadioButton:
                        classeSelecionada = "Bukijutsu";
                        atributos.setTaijutsu(1);
                        atributos.setBukijutsu(10);
                        atributos.setNinjutsu(1);
                        atributos.setGenjutsu(1);
                        atributos.setForca(5);
                        atributos.setAgilidade(3);
                        atributos.setInteligencia(1);
                        atributos.setSelo(3);
                        break;
                }
                atributos.setResistencia(1);
                atributos.setEnergia(10);
            }
        });

        nomePersonagemSelecionadoTextView = view.findViewById(R.id.nomePersonagemSelecionadoTextView);
        profileImageView = view.findViewById(R.id.profileCriarImageView);

        grupoAtualTextView = view.findViewById(R.id.grupoAtualTextView);
        profilesPequenasGridView = view.findViewById(R.id.profilesPequenasGridView);
        configurarGriView();

        ImageButton voltarButton = view.findViewById(R.id.voltarImageButton);
        voltarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grupoAtual - 1 >= 0)
                    grupoAtual--;
                else
                    grupoAtual = 19;

                carregarGrupoAtual();
            }
        });

        ImageButton avancarButton = view.findViewById(R.id.avancarImageButton);
        avancarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grupoAtual + 1 < 20)
                    grupoAtual++;
                else
                    grupoAtual = 0;

                carregarGrupoAtual();
            }
        });

        Button criarPersonagemButton = view.findViewById(R.id.criarPersonagemButton);
        criarPersonagemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nick = nickEditText.getText().toString();

                if (validarNick(nick)) {
                    DatabaseReference personagensRef = ConfigFirebase.getDatabase().child("personagem");

                    Query pesquisarNickRepetidoQuery = personagensRef.orderByKey().equalTo(nick);

                    pesquisarNickRepetidoQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Personagem personagemResult = dataSnapshot.getValue(Personagem.class);

                            if (personagemResult == null) {
                                salvarPersonagem(nick);
                                changeToFragment(new PersonagemSelecionarFragment());
                            } else {
                                exibirAlerta("Aviso!", "Os seguintes problemas evitaram que a operação fosse completada:\n" +
                                        "\nJá existe um personagem com o nome escolhido");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                }
            }
        });

        return view;
    }

    private void carregarGrupoAtual() {
        int from = grupoAtual * 6;
        int to = from + 6;

        profilesGrupoAtual.clear();

        for (Integer i : pequenasLista.subList(from, to))
            profilesGrupoAtual.add(i);

        adapter.notifyDataSetChanged();

        grupoAtualTextView.setText(String.valueOf(grupoAtual + 1));
    }

    private void configurarGriView() {
        recuperarImagensPequenas();
        adapter = new ProfilesPequenasAdapter(getActivity(), profilesGrupoAtual);
        carregarGrupoAtual();
        profilesPequenasGridView.setAdapter(adapter);
        profilesPequenasGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Storage.baixarProfile(getActivity(), profileImageView, profilesGrupoAtual.get(position));

                personagem.setIdProfile(profilesGrupoAtual.get(position));
                nomePersonagemSelecionadoTextView.setText(Helper.nomeDoPersonagem(personagem.getIdProfile()));
            }
        });
    }

    private void recuperarImagensPequenas() {
        for (int i = 1; i <= 265; i++) {
            if (Helper.nomeDoPersonagem(i) != null)
                pequenasLista.add(i);
        }
    }

    private void salvarPersonagem(String nick) {
        if (PlayerOn.player != null) {
            if (PlayerOn.player.getPersonagens() == null)
                PlayerOn.player.setPersonagens(new ArrayList<String>());

            PlayerOn.player.getPersonagens().add(nick);

            personagem.setIdPlayer(auth.getCurrentUser().getUid());
            personagem.setNick(nick);
            personagem.setLevel(1);
            personagem.setGraducao("Estudante");
            personagem.setRyous(500);
            personagem.setVila(vilaSelecionada);
            personagem.setNumVila(numVila);
            personagem.setClasse(classeSelecionada);
            personagem.setAtributos(atributos);
            personagem.setFotoAtual(1);
            personagem.setExpUpar(1200);
            personagem.setPontos(1000);
            personagem.setResumoCombates(new ResumoCombates());
            personagem.setResumoMissoes(new ResumoMissoes());
            personagem.setMapa_posicao(-1);

            PersonagemOn.personagem = personagem;
            personagem.atualizarAtributos();
            personagem.getAtributos().getFormulas().setVidaAtual(personagem.getAtributos().getFormulas().getVida());
            personagem.getAtributos().getFormulas().setChakraAtual(personagem.getAtributos().getFormulas().getChakra());
            personagem.getAtributos().getFormulas().setStaminaAtual(personagem.getAtributos().getFormulas().getStamina());

            List<Jutsu> jutsus = new ArrayList<>();
            if (personagem.getClasse().equals("Ninjutsu") || personagem.getClasse().equals("Genjutsu")) {
                jutsus.add(new Jutsu(0, "defesa_2_mao", 1, 0, 0, 5, 0, 3, 10, 3, "def", "basico"));
                jutsus.add(new Jutsu(1, "defesa_acrobatica", 1, 0, 0, 5, 0, 3, 15, 4, "def", "basico"));
                jutsus.add(new Jutsu(2, "soco", 1, 0, 5, 0, 0, 3, 8, 1, "atk", "basico"));
                jutsus.add(new Jutsu(3, "chute", 1, 0, 8, 0, 0, 3, 11, 2, "atk", "basico"));
            } else {
                jutsus.add(new Jutsu(0, "defesa_2_mao", 1, 0, 0, 5, 0, 3, 3, 10, "def", "basico"));
                jutsus.add(new Jutsu(1, "defesa_acrobatica", 1, 0, 0, 5, 0, 3, 4, 15, "def", "basico"));
                jutsus.add(new Jutsu(2, "soco", 1, 5, 0, 0, 0, 3, 1, 8, "atk", "basico"));
                jutsus.add(new Jutsu(3, "chute", 1, 8, 0, 0, 0, 3, 2, 11, "atk", "basico"));
            }
            personagem.setJutsus(jutsus);
            personagem.salvar();

            exibirAlerta("NINJA CRIADO COM SUCESSO", "Parabéns você acaba de criar seu personagem no Naruto Game.\n" +
                    "Selecione seu personagem e comece agora mesmo seu treinamento");
        } else
            Toast.makeText(getActivity(), "Erro: sem conecção com a internet!", Toast.LENGTH_SHORT).show();
    }

    private boolean validarNick(String nick) {
        if (nick.isEmpty()) {
            nickEditText.setError("Campo obrigatório");
            return false;
        }

        String message = "";

        if (nick.length() > 18)
            message = "O nome do seu personagem não pode ter mais de 18 caractéres\n";

        String nickSemPontuacao = nick.replaceAll("(?!_)\\p{P}", "");

        if (!nickSemPontuacao.equals(nick))
            message += "O nome do seu personagem só pode conter letras, números e underlines";

        if (message.isEmpty())
            return true;
        else {
            exibirAlerta("Aviso!", "Os seguintes problemas evitaram que a operação fosse completada:\n\n" + message);
            return false;
        }
    }

    private void exibirAlerta(String titulo, String mensagem) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.create();
        alert.show();
    }

    private void changeToFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.conteiner, fragment);
        transaction.commit();
    }
}
