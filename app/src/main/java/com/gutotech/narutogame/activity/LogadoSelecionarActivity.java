package com.gutotech.narutogame.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.narutogame.R;
import com.gutotech.narutogame.adapter.MenuLogadoExpandableLisViewAdapter;
import com.gutotech.narutogame.config.ConfigFirebase;
import com.gutotech.narutogame.fragment.deslogado.HalldafamaFragment;
import com.gutotech.narutogame.fragment.deslogado.HomeFragment;
import com.gutotech.narutogame.fragment.logado.PersonagemCriarFragment;
import com.gutotech.narutogame.fragment.logado.PersonagemSelecionarFragment;
import com.gutotech.narutogame.fragment.logado.SenhaTrocarFragment;
import com.gutotech.narutogame.fragment.logado.SuporteFragment;
import com.gutotech.narutogame.fragment.logado.UsuarioDadosFragment;
import com.gutotech.narutogame.model.Player;
import com.gutotech.narutogame.publicentities.CurrentFragment;
import com.gutotech.narutogame.publicentities.PlayerOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogadoSelecionarActivity extends AppCompatActivity {
    private List<Integer> groupList;
    private HashMap<Integer, List<String>> childList;

    final int GROUP_USUARIO = 0;
    final int GROUP_PERSONAGEM = 1;
    final int GROUP_PRINCIPAL = 2;

    private TextView tituloSecaoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logado_selecionar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        recuperarPlayerOn();

        tituloSecaoTextView = findViewById(R.id.tituloSecaoTextView);
        tituloSecaoTextView.setText("SELECIONE SEU PERSONAGEM");
        changeFragment(new PersonagemSelecionarFragment());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        buildMenu();
        ExpandableListView expandableListView = findViewById(R.id.expanded_menu_main);
        MenuLogadoExpandableLisViewAdapter adapter = new MenuLogadoExpandableLisViewAdapter(this, groupList, childList);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == GROUP_USUARIO) {
                    switch (childPosition) {
                        case 0:
                            tituloSecaoTextView.setText("DADOS DA CONTA");
                            changeFragment(new UsuarioDadosFragment());
                            break;
                        case 1:
                            tituloSecaoTextView.setText("TROQUE SUA SENHA");
                            changeFragment(new SenhaTrocarFragment());
                            break;
                        case 2:
                            tituloSecaoTextView.setText("SUPORTE");
                            changeFragment(new SuporteFragment());
                    }
                } else if (groupPosition == GROUP_PERSONAGEM) {
                    switch (childPosition) {
                        case 0:
                            tituloSecaoTextView.setText("SELECIONE SEU PERSONAGEM");
                            changeFragment(new PersonagemSelecionarFragment());
                            break;
                        case 1:
                            tituloSecaoTextView.setText("CRIAR PERSONAGEM");
                            changeFragment(new PersonagemCriarFragment());
                            break;
                    }
                } else if (groupPosition == GROUP_PRINCIPAL) {
                    switch (childPosition) {
                        case 0:
                            tituloSecaoTextView.setText("HOME");
                            HomeFragment homeFragment = new HomeFragment();
                            homeFragment.setArguments(new Bundle());
                            changeFragment(homeFragment);
                            break;
                        case 1:
                            tituloSecaoTextView.setText("HALL DA FAMA");
                            changeFragment(new HalldafamaFragment());
                            break;
                    }
                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        expandableListView.expandGroup(0);
        expandableListView.expandGroup(1);
        expandableListView.expandGroup(2);
    }

    private void recuperarPlayerOn() {
        DatabaseReference playerReference = ConfigFirebase.getDatabase()
                .child("player")
                .child(ConfigFirebase.getAuth().getCurrentUser().getUid());

        playerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PlayerOn.player = dataSnapshot.getValue(Player.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void buildMenu() {
        groupList = new ArrayList<>();
        childList = new HashMap<>();

        groupList.add(R.drawable.layout_usuario);
        groupList.add(R.drawable.layout_personagem);
        groupList.add(R.drawable.layout_principal);

        List<String> itemsFromUSUARIO = new ArrayList<>();
        itemsFromUSUARIO.add("Meus dados");
        itemsFromUSUARIO.add("Trocar Senha");
        itemsFromUSUARIO.add("Suporte");
        childList.put(groupList.get(0), itemsFromUSUARIO);

        List<String> itemsFromPersonagem = new ArrayList<>();
        itemsFromPersonagem.add("Selecionar");
        itemsFromPersonagem.add("Criar Personagem");
        childList.put(groupList.get(1), itemsFromPersonagem);

        List<String> itemsFromPrincipal = new ArrayList<>();
        itemsFromPrincipal.add("Home");
        itemsFromPrincipal.add("Hall da Fama");
        childList.put(groupList.get(2), itemsFromPrincipal);
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.conteiner, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (CurrentFragment.LER_NOTICIA == 1) {
            CurrentFragment.LER_NOTICIA = 0;
            tituloSecaoTextView.setText("HOME");
            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setArguments(new Bundle());
            changeFragment(homeFragment);
        } else {
            super.onBackPressed();
        }
    }
}
