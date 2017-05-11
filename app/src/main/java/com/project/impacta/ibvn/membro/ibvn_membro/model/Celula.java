package com.project.impacta.ibvn.membro.ibvn_membro.model;

import java.util.List;

/**
 * Created by matheuscatossi on 5/10/17.
 */

public class Celula {

    int id;
    String nome;
    int lider;
    String created_at;
    String update_at;
    List<Membro> listMembro;

    public Celula() {

    }

    public Celula(int id, String nome, int lider, String created_at, String update_at, List<Membro> listMembro){
        this.id = id;
        this.nome = nome;
        this.lider = lider;
        this.created_at = created_at;
        this.update_at = update_at;
        this.listMembro = listMembro;
    }

    public Celula(String nome, int lider, String created_at, String update_at, Membro membro) {
        this.id = id;
        this.lider = lider;
        this.created_at = created_at;
        this.update_at = update_at;
        this.listMembro = listMembro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getLider() {
        return lider;
    }

    public void setLider(int lider) {
        this.lider = lider;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public List<Membro> getListMembro() {
        return listMembro;
    }

    public void setListMembro(List<Membro> listMembro) {
        this.listMembro = listMembro;
    }
}
