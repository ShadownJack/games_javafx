package br.senac.sp.gamesfx.model;

import java.time.LocalDate;

public class Plataforma {
    private int id;
    private String nome;
    private String fabricante;
    private LocalDate anoLancamento;
    private int geracao;
    private boolean ativo;
    private int fabricanteId;

    public Plataforma() {
    }

    public Plataforma(int id, String nome, String fabricante, LocalDate anoLancamento, int geracao, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
        this.anoLancamento = anoLancamento;
        this.geracao = geracao;
        this.ativo = ativo;
    }

    public int getFabricanteId() {
        return fabricanteId;
    }

    public void setFabricanteId(int fabricanteId) {
        this.fabricanteId = fabricanteId;
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

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
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

    @Override
    public String toString() {
        return this.getNome();
    }
}
