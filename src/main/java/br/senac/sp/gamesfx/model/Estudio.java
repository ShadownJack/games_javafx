package br.senac.sp.gamesfx.model;

import java.time.LocalDate;

public class Estudio {
    private int id;
    private String nome;
    private String paisOrigem;
    private LocalDate anoFundacao;
    private String site;
    private boolean ativo;
    private int fabricanteId;

    public Estudio() {
    }

    public Estudio(int id, String nome, String paisOrigem, LocalDate anoFundacao, String site, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.paisOrigem = paisOrigem;
        this.anoFundacao = anoFundacao;
        this.site = site;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getAnoFundacao() {
        return anoFundacao;
    }

    public void setAnoFundacao(LocalDate anoFundacao) {
        this.anoFundacao = anoFundacao;
    }

    public int getFabricanteId() { return fabricanteId; }

    public void setFabricanteId(int fabricanteId) { this.fabricanteId = fabricanteId; }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
