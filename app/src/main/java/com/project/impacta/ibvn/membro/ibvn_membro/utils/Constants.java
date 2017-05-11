package com.project.impacta.ibvn.membro.ibvn_membro.utils;

/**
 * Created by matheuscatossi on 5/10/17.
 */

public class Constants {

    public static final String TAG = "STATUS_TAG";
    public static final String MESSAGE = "STATUS_MESSAGE";
    public static final String STATUSCONNECTION = "STATUS_CONNECTION";

    // Membros
    public static final String BASE_URL = "http://newprogsoftwares.com.br/api/";
    public static final String URL = "http://newprogsoftwares.com.br/";
    public static final String GET_MEMBROS = "membros/";
    public static final String GET_MEMBROS_BY_ID = "membros/{id}";
    public static final String GET_MEMBROS_BY_CELULA = "membros/byCelula/{id}";
    public static final String POST_MEMBROS = "membros";

    //Reuniões
    public static final String GET_REUNIOES = "reunioes/";
    public static final String GET_REUNIOES_BY_ID = "reunioes/{id}";
    public static final String GET_REUNIOES_BY_CELULA = "reunioes/byCelula/{id}";
    public static final String POST_REUNIOES = "reunioes";

    // Eventos
    public static final String GET_EVENTOS  = "posts/";

    // Presenca
    public static final String GET_MEMBROS_REUNIAO = "presencas/{id}";
    public static final String POST_PRESENCA_MEMBRO_REUNIAO = "presencas/up";

    //Mensagem
    public static final String POST_MENSAGEM = "mensagem/";

    // Login
    public static final String POST_LOGIN = "usuario/";

    // Celula

    public static final String GET_CELULA = "celulas/";

    public static String CELULA = "";
    public static int ID = 0;

}

