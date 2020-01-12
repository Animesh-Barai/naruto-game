package com.gutotech.narutogame.ui.loggedout;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.ui.SectionFragment;
import com.gutotech.narutogame.ui.adapter.ExpandableAdapter;
import com.gutotech.narutogame.ui.loggedout.signup.SignupFragment;
import com.gutotech.narutogame.ui.loggedout.halloffame.HallOfFameFragment;
import com.gutotech.narutogame.ui.loggedout.home.HomeFragment;
import com.gutotech.narutogame.ui.loggedout.recuperarsenha.RecuperarSenhaFragment;
import com.gutotech.narutogame.util.FragmentUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeslogadoActivity extends AppCompatActivity {
    private List<Integer> groupList;
    private HashMap<Integer, List<SectionFragment>> sectionsHashMap;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deslogado);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        ImageView logoImageView = headerView.findViewById(R.id.logoImageView);
        logoImageView.setOnClickListener(v -> {
            FragmentUtil.goTo(DeslogadoActivity.this, new HomeFragment());
            drawer.closeDrawer(GravityCompat.START);
        });

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        buildMenu();
        ExpandableListView expandableListView = findViewById(R.id.expanded_menu_main);

        ExpandableAdapter adapter = new ExpandableAdapter(groupList, sectionsHashMap);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            FragmentUtil.goTo(DeslogadoActivity.this,
                    (Fragment) sectionsHashMap.get(groupPosition).get(childPosition));

            drawer.closeDrawer(GravityCompat.START);
            return false;
        });
        expandableListView.expandGroup(0);

        FragmentUtil.goTo(this, (Fragment) sectionsHashMap.get(0).get(0));

//        FirebaseDynamicLinks . getInstance ()
//                . getDynamicLink ( getIntent ())
//                . addOnSuccessListener ( este , novo OnSuccessListener < PendingDynamicLinkData > () {
//            @Sobrepor
//            public void onSuccess ( PendingDynamicLinkData pendingDynamicLinkData ) {
//                // Obter link direto do resultado (pode ser nulo se nenhum link for encontrado)
//                Uri deepLink = nulo ;
//                if ( pendingDynamicLinkData ! = null ) {
//                    deepLink = pendingDynamicLinkData . getLink ();
//                }
//
//
//                // Manipule o link direto. Por exemplo, abra o link
//                // conteúdo ou aplique crédito promocional ao usuário
//                // conta.
//                // ...
//
//                // ...
//            }
//        })
//        . addOnFailureListener ( este , novo OnFailureListener () {
//            @Sobrepor
//            vazio público onFailure  ( @NonNull Exception e ) {
//                Log . W ( TAG , "getDynamicLink: onFailure" , e );
//            }
//        });
    }

    private void buildMenu() {
        groupList = new ArrayList<>();
        sectionsHashMap = new HashMap<>();

        groupList.add(R.drawable.layout_principal);

        List<SectionFragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new SignupFragment());
        fragments.add(new RecuperarSenhaFragment());
        fragments.add(new HallOfFameFragment());

        sectionsHashMap.put(0, fragments);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
