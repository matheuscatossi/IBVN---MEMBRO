package com.project.impacta.ibvn.membro.ibvn_membro.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.impacta.ibvn.membro.ibvn_membro.InfoEventoActivity;
import com.project.impacta.ibvn.membro.ibvn_membro.R;
import com.project.impacta.ibvn.membro.ibvn_membro.helper.DownloadImageTask;
import com.project.impacta.ibvn.membro.ibvn_membro.model.Evento;
import com.project.impacta.ibvn.membro.ibvn_membro.utils.Constants;

import java.util.List;

/**
 * Created by matheuscatossi on 5/10/17.
 */

public class EventoCustomAdapter extends RecyclerView.Adapter<EventoCustomAdapter.MyViewHolder> {

    private Context mContext;
    private List<Evento> eventoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public LinearLayout ll_card_evento;

        public MyViewHolder(View view) {
            super(view);
            title         = (TextView) view.findViewById(R.id.title);
            count         = (TextView) view.findViewById(R.id.count);
            thumbnail     = (ImageView) view.findViewById(R.id.thumbnail);
            overflow      = (ImageView) view.findViewById(R.id.overflow);
            ll_card_evento = (LinearLayout) view.findViewById(R.id.newsfeeds_card);


        }
    }

    public EventoCustomAdapter(Context mContext, List<Evento> eventoList) {
        this.mContext = mContext;
        this.eventoList = eventoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newsfeed_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Evento evento = eventoList.get(position);
        holder.title.setText(evento.getNome());
        holder.count.setText("Info : "  + evento.getNome());

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, InfoEventoActivity.class);

                i.putExtra("cod", "" + evento.getId());
                i.putExtra("data",evento.getData());
                i.putExtra("nome",evento.getNome());
                i.putExtra("descricao",evento.getDescricao());
                i.putExtra("tipo",evento.getTipo());
                i.putExtra("imagem",evento.getLink_imagem());
                i.putExtra("link",evento.getLink());
                i.putExtra("create_at",evento.getCreated_at());
                i.putExtra("update_at",evento.getUpdate_at());

                mContext.startActivity(i);
            }
        });

        new DownloadImageTask(holder.thumbnail).execute(Constants.URL + evento.getLink_imagem());
        //Glide.with(mContext).load(evento.getLink_imagem()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

}