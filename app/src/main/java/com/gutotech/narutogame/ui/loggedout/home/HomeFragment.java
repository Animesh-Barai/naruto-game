package com.gutotech.narutogame.ui.loggedout.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.databinding.FragmentHomeBinding;
import com.gutotech.narutogame.ui.SectionFragment;
import com.gutotech.narutogame.ui.adapter.KagesAndVilasViewPagerAdapter;
import com.gutotech.narutogame.ui.adapter.NinjaStatisticsRecyclerViewAdapter;
import com.gutotech.narutogame.ui.adapter.NewsAdapter;
import com.gutotech.narutogame.ui.loggedout.AuthListener;
import com.gutotech.narutogame.ui.loggedin.LogadoSelecionarActivity;
import com.gutotech.narutogame.util.FragmentUtil;
import com.gutotech.narutogame.util.RecyclerItemClickListener;
import com.gutotech.narutogame.ui.loggedout.recuperarsenha.RecuperarSenhaFragment;

import es.dmoral.toasty.Toasty;

public class HomeFragment extends Fragment implements AuthListener, SectionFragment {
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;

    private RecyclerView ninjaStatisticsRecyclerView;
    private NinjaStatisticsRecyclerViewAdapter ninjaStatisticsAdapter;

    private Dialog waitDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HomeViewModel mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setViewmodel(mHomeViewModel);
        View root = binding.getRoot();

        mHomeViewModel.setAuthListener(this);

        mHomeViewModel.getNews().observe(this, news -> newsAdapter.setNewsList(news));

        binding.forgotPasswordTextView.setOnClickListener(v -> {
            FragmentUtil.goTo(getActivity(), new RecuperarSenhaFragment());
        });

//        Bundle bundle = getArguments();
//        if (bundle == null)
//            configurarLogin();
//        else {
//            emailEditText.setVisibility(View.GONE);
//            senhaEditText.setVisibility(View.GONE);
//            esqueceuSenhaTextView.setVisibility(View.GONE);
//            jogarButton.setVisibility(View.GONE);
//        }

        newsRecyclerView = root.findViewById(R.id.newsRecyclerView);
        setUpNewsRecyclerView();

        ninjaStatisticsRecyclerView = root.findViewById(R.id.ninjaStatisticsRecyclerView);
        setUpNinjaStatistics();

        ViewPager viewPager = root.findViewById(R.id.kagesAndVilaViewPager);
        KagesAndVilasViewPagerAdapter viewPagerAdapter = new KagesAndVilasViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        waitDialog = new Dialog(getActivity());
        waitDialog.setContentView(R.layout.dialog_wait);
        waitDialog.setCancelable(false);

        FragmentUtil.setSectionTitle(getActivity(), R.string.section_home);

        return root;
    }

    public void setUpNewsRecyclerView() {
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.HORIZONTAL));

        newsAdapter = new NewsAdapter(getActivity());
        newsRecyclerView.setAdapter(newsAdapter);

        newsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), newsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                CurrentFragment.LER_NOTICIA = 1;
//                TextView textView = getActivity().findViewById(R.id.tituloSecaoTextView);
//                textView.setText("LER NOTÍCIA");
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("noticia", noticiaList.get(position));
//
//                LerNoticiaFragment lerNoticiaFragment = new LerNoticiaFragment();
//                lerNoticiaFragment.setArguments(bundle);
//                changeTo(lerNoticiaFragment);
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        }));
    }

    public void setUpNinjaStatistics() {
        ninjaStatisticsRecyclerView.setHasFixedSize(true);

        ninjaStatisticsAdapter = new NinjaStatisticsRecyclerViewAdapter(getActivity());
        ninjaStatisticsRecyclerView.setAdapter(ninjaStatisticsAdapter);
    }

    @Override
    public void onStarted() {
        waitDialog.show();
    }

    @Override
    public void onSuccess() {
        waitDialog.dismiss();
        startActivity(new Intent(getActivity(), LogadoSelecionarActivity.class));
        getActivity().finish();
    }

    @Override
    public void onFailure(int resId) {
        waitDialog.dismiss();
        Toasty.error(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getDescription() {
        return R.string.home;
    }
}
