package com.project.impacta.ibvn.membro.ibvn_membro.webservice;

import com.project.impacta.ibvn.membro.ibvn_membro.model.Celula;
import com.project.impacta.ibvn.membro.ibvn_membro.model.Evento;
import com.project.impacta.ibvn.membro.ibvn_membro.model.Membro;
import com.project.impacta.ibvn.membro.ibvn_membro.model.Mensagem;
import com.project.impacta.ibvn.membro.ibvn_membro.model.Reuniao;
import com.project.impacta.ibvn.membro.ibvn_membro.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by matheuscatossi on 5/10/17.
 */

public interface APIInterface {

    @POST(Constants.POST_MENSAGEM)
    Call<Mensagem> postMensagem(@Body Mensagem mensagem);

    @GET(Constants.GET_EVENTOS)
    Call<List<Evento>> getEventos();

    @GET(Constants.GET_CELULA)
    Call<List<Celula>> getCelula();


    @GET(Constants.GET_REUNIOES_BY_ID)
    Call<Reuniao> getReunioesByID(@Path("id") String id);

    @GET(Constants.GET_REUNIOES_BY_CELULA)
    Call<List<Reuniao>> getReunioesByCelula(@Path("id") String id);
}
