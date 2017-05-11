package com.project.impacta.ibvn.membro.ibvn_membro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.impacta.ibvn.membro.ibvn_membro.adapter.CelulaCustomAdapter;
import com.project.impacta.ibvn.membro.ibvn_membro.adapter.EventoCustomAdapter;
import com.project.impacta.ibvn.membro.ibvn_membro.model.Celula;
import com.project.impacta.ibvn.membro.ibvn_membro.model.Evento;
import com.project.impacta.ibvn.membro.ibvn_membro.utils.Constants;
import com.project.impacta.ibvn.membro.ibvn_membro.webservice.APIClient;
import com.project.impacta.ibvn.membro.ibvn_membro.webservice.APIInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        try {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

        } catch (Exception ex) {
            throw ex;
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mensagem) {
            Intent i = new Intent(this, MensagemActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        private Call<List<Evento>> callEventos;
        private Call<List<Celula>> callCelula;
        private APIInterface apiService;
        private RecyclerView recyclerView;
        private ArrayList<Evento> eventoList;
        private EventoCustomAdapter eventoCustomAdapter;
        private final ScheduledThreadPoolExecutor EXECUTOR = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);
        private ScheduledFuture<?> sEvento, sCelula;
        private WebView mWebView;
        private ListView listViewCelula;
        private ArrayList<Celula> celulaList;
        private static final String ARG_SECTION_NUMBER = "section_number";
        private CelulaCustomAdapter celulaCustomAdapter;


        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView;

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_evento, container, false);

                    recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                    recyclerView.setLayoutManager(mLayoutManager);

                    apiService = APIClient.getService().create(APIInterface.class);
                    callEventos = apiService.getEventos();
                    eventoList = new ArrayList<>();

                    sEvento = EXECUTOR.scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            callEventos.enqueue(new Callback<List<Evento>>() {
                                @Override
                                public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                                    if (response.raw().code() == 200) {

                                        for (Evento evento : response.body()) {
                                            eventoList.add(new Evento(evento.getId(), evento.getData(), evento.getNome().toString(), evento.getDescricao(), evento.getTipo(), evento.getLink_imagem(), evento.getLink(), evento.getCreated_at(), evento.getUpdate_at()));
                                        }

                                        Collections.reverse(eventoList);
                                        eventoCustomAdapter = new EventoCustomAdapter(getContext(), eventoList);

                                        recyclerView.setAdapter(eventoCustomAdapter);

                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Evento>> call, Throwable t) {
                                    Log.e("INFOEVENTOS", t.toString());
                                }
                            });
                        }
                    }, 0, 6000, TimeUnit.SECONDS);

                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_celula, container, false);

                    listViewCelula = (ListView) rootView.findViewById(R.id.listReuniao);

                    sCelula = EXECUTOR.scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            apiService = APIClient.getService().create(APIInterface.class);
                            callCelula = apiService.getCelula();
                            celulaList = new ArrayList<>();

                            Log.e("Celula", ""+ callCelula.request());

                            callCelula.enqueue(new Callback<List<Celula>>() {
                                @Override
                                public void onResponse(Call<List<Celula>> call, Response<List<Celula>> response) {
                                    if (response.raw().code() == 200) {


                                        Log.e("Celula", ""+ callCelula.request());

                                        List<Celula> l = new ArrayList<Celula>();
                                        l.addAll(response.body());

                                        for (Celula celula : l) {

                                            Log.e("Celula", ""+ celula.getNome());
                                            celulaList.add(
                                                    new Celula(
                                                            celula.getId(), celula.getNome(), celula.getLider(), celula.getCreated_at(), celula.getCreated_at(), celula.getListMembro())
                                            );
                                        }

                                        Collections.reverse(celulaList);
                                        celulaCustomAdapter = new CelulaCustomAdapter(celulaList, getContext());

                                        if (celulaCustomAdapter != null) {
                                            listViewCelula.setAdapter(celulaCustomAdapter);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Celula>> call, Throwable t) {
                                    Log.e("INFOMEMBRO", t.toString());
                                }
                            });
                        }
                    }, 0, 6000, TimeUnit.SECONDS);

                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_chat, container, false);
                    mWebView = (WebView) rootView.findViewById(R.id.webView);
                    mWebView.getSettings().setJavaScriptEnabled(true);
                    mWebView.getSettings().setDomStorageEnabled(true);
                    mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                    mWebView.loadUrl("http://bankbox.net.br/ibvn/watson/index.htm");

                    break;
                default:
                    rootView = inflater.inflate(R.layout.fragment_evento, container, false);
                    break;
            }
            return rootView;


        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "EVENTOS";
                case 1:
                    return "CÉLULAS";
                case 2:
                    return "CHAT";
            }
            return null;
        }
    }
}
