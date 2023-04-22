package com.donay.orcamento;

public class Orcamento {
    private int id;
    private String loja;
    private double valor;
    private String formaPagto;
    private String descricao;
    private String foto;
    private int idCategoria;

    public Orcamento(int id, String loja, double valor, String formaPagto, String descricao, String foto, int idCategoria) {
        this.id = id;
        this.loja = loja;
        this.valor = valor;
        this.formaPagto = formaPagto;
        this.descricao = descricao;
        this.foto = foto;
        this.idCategoria = idCategoria;
    }

    public int getId() {
        return id;
    }

    public String getLoja() {
        return loja;
    }

    public double getValor() {
        return valor;
    }

    public String getFormaPagto() {
        return formaPagto;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getFoto() {
        return foto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }
}