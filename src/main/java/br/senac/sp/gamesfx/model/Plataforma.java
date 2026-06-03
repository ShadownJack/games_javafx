package br.senac.sp.gamesfx.model;

import java.time.LocalDate;

public class Plataforma {
    private int id;
    private String titulo;
    private String fabricante;
    private LocalDate anoLancamento;
    private int geracao;
    private boolean ativo;

    public Plataforma() {
    }

    public Plataforma(int id, String titulo, String frabricante, LocalDate anoLancamento, int geracao, boolean ativo) {
        this.id = id;
        this.titulo = titulo;
        this.fabricante = frabricante;
        this.anoLancamento = anoLancamento;
        this.geracao = geracao;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String faabricante) {
        this.fabricante = fabricante;
    }

    public LocalDate getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(LocalDate anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public int getGeracao() {
        return geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
