package com.donay.orcamento;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String descricao;

    public Item(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }



    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
