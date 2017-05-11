package com.project.impacta.ibvn.membro.ibvn_membro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.project.impacta.ibvn.membro.ibvn_membro.adapter.ReuniaoCustomAdapter;
import com.project.impacta.ibvn.membro.ibvn_membro.model.Reuniao;
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

public class ReuniaoActivity extends AppCompatActivity {


    public ArrayList<Reuniao> reuniaoList;
    ListView listViewReuniao;
    ReuniaoCustomAdapter reuniaoCustomAdapter;
    Call<List<Reuniao>> callReuniao;
    private String codigo;

    static final ScheduledThreadPoolExecutor EXECUTOR = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);
    static ScheduledFuture<?> sReuniao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuniao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listViewReuniao = (ListView) findViewById(R.id.listReuniao);

        Intent myIntent = getIntent();
        codigo = myIntent.getStringExtra("cod");

        sReuniao = EXECUTOR.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                APIInterface apiService = APIClient.getService().create(APIInterface.class);
                callReuniao = apiService.getReunioesByCelula(codigo);
                reuniaoList = new ArrayList<>();

                callReuniao.enqueue(new Callback<List<Reuniao>>() {
                    @Override
                    public void onResponse(Call<List<Reuniao>> call, Response<List<Reuniao>> response) {
                        if (response.raw().code() == 200) {

                            List<Reuniao> l = new ArrayList<Reuniao>();
                            l.addAll(response.body());

                            for (Reuniao reuniao : l) {
                                reuniaoList.add(
                                        new Reuniao(
                                                (int) reuniao.getId(),
                                                (String) reuniao.getData(),
                                                (String) reuniao.getTema(),
                                                (String) reuniao.getDescricao())
                                );
                            }

                            Collections.reverse(reuniaoList);
                            reuniaoCustomAdapter = new ReuniaoCustomAdapter(reuniaoList, ReuniaoActivity.this);

                            if (reuniaoCustomAdapter != null) {
                                listViewReuniao.setAdapter(reuniaoCustomAdapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Reuniao>> call, Throwable t) {
                        Log.e("INFOMEMBRO", t.toString());
                    }
                });
            }
        }, 0, 6000, TimeUnit.SECONDS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
