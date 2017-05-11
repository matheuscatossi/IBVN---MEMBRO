package com.project.impacta.ibvn.membro.ibvn_membro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.impacta.ibvn.membro.ibvn_membro.helper.DownloadImageTask;
import com.project.impacta.ibvn.membro.ibvn_membro.utils.Constants;

public class InfoEventoActivity extends AppCompatActivity {

    ImageView img_evento;
    TextView tv_nome_evento;
    TextView tv_descricao_evento;
    TextView tv_data_evento;
    TextView tv_data_criacao;
    TextView tv_link_video;
    LinearLayout ll_link_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_evento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent myIntent = getIntent();
        String id = myIntent.getStringExtra("cod");
        String data = myIntent.getStringExtra("data");
        String nome = myIntent.getStringExtra("nome");
        String descricao = myIntent.getStringExtra("descricao");
        String tipo = myIntent.getStringExtra("tipo");
        String imagem = myIntent.getStringExtra("imagem");
        final String link = myIntent.getStringExtra("link");
        String create_at = myIntent.getStringExtra("create_at");
        String update_at = myIntent.getStringExtra("update_at");

        img_evento = (ImageView) findViewById(R.id.img_evento);
        tv_nome_evento = (TextView) findViewById(R.id.tv_nome_evento);
        tv_descricao_evento = (TextView) findViewById(R.id.tv_descricao_evento);
        tv_data_evento = (TextView) findViewById(R.id.tv_data_evento);
        tv_data_criacao = (TextView) findViewById(R.id.tv_data_criacao);
        tv_link_video = (TextView) findViewById(R.id.tv_link_video);
        ll_link_video = (LinearLayout) findViewById(R.id.ll_link_video);

        if(tipo.equals("imagem")) {
            new DownloadImageTask(img_evento).execute(Constants.URL + imagem);

            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(300,300);
            img_evento.setLayoutParams(parms);
        } else {
            img_evento.setVisibility(View.INVISIBLE);
        }

        Log.e("tipo", ""+ tipo);
        Log.e("nome", ""+ nome);

        tv_nome_evento.setText(nome);
        tv_descricao_evento.setText(descricao);
        tv_data_evento.setText(data);
        tv_data_criacao.setText(create_at);
        tv_link_video.setText(link);

        if(tipo.equals("imagem")) {
            ll_link_video.setVisibility(View.INVISIBLE);
        } else {
            tv_link_video.setPaintFlags(tv_link_video.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        tv_link_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
