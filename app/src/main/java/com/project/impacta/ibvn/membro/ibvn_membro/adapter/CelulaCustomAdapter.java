package com.project.impacta.ibvn.membro.ibvn_membro.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.impacta.ibvn.membro.ibvn_membro.R;
import com.project.impacta.ibvn.membro.ibvn_membro.ReuniaoActivity;
import com.project.impacta.ibvn.membro.ibvn_membro.helper.DownloadImageTask;
import com.project.impacta.ibvn.membro.ibvn_membro.model.Celula;
import com.project.impacta.ibvn.membro.ibvn_membro.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matheuscatossi on 5/10/17.
 */

public class CelulaCustomAdapter extends ArrayAdapter<Celula> implements View.OnClickListener {

    private Context mContext;
    private List<Celula> celulaList;

    private static class ViewHolder {
        TextView tv_text_celula;
        ImageView img_celula;
        LinearLayout ll_linha;
    }

    public CelulaCustomAdapter(ArrayList<Celula> data, Context context) {
        super(context, R.layout.row_item_celula, data);

        this.mContext = context;
        this.celulaList = data;
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Celula celula = (Celula) object;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


        final Celula celula = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_celula, parent, false);

            viewHolder.img_celula = (ImageView) convertView.findViewById(R.id.img_celula);
            viewHolder.tv_text_celula = (TextView) convertView.findViewById(R.id.tv_text_celula);
            viewHolder.ll_linha = (LinearLayout) convertView.findViewById(R.id.ll_linha);

            viewHolder.tv_text_celula.setText(celula.getNome());
            viewHolder.img_celula.setImageResource(R.drawable.celula);

            viewHolder.ll_linha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, ReuniaoActivity.class);

                    i.putExtra("cod", "" + celula.getId());

                    mContext.startActivity(i);

                }

            });

            convertView.setTag(viewHolder);
        }


        return convertView;
    }


}
