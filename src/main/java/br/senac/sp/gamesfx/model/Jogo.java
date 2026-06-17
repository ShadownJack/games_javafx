package br.senac.sp.gamesfx.model;

import java.time.LocalDate;

public class Jogo {

    private int id;
    private int plataformaId;
    private int estudioId;
    private String titulo;
    private String nomePlataforma;
    private String categoria;
    private String nomeEstudio;
    private double preco;
    private LocalDate dataLancamento;
    private boolean finalizado;

    public Jogo() {
    }

    public Jogo(int id, String titulo, String nomePlataforma) {
        this.id = id;
        this.titulo = titulo;
        this.nomePlataforma = nomePlataforma;
    }

    public Jogo(int id, String titulo, String nomePlataforma, String categoria, String nomeEstudio,
                double preco, boolean finalizado, LocalDate dataLancamento) {
        this.id = id;
        this.titulo = titulo;
        this.nomePlataforma = nomePlataforma;
        this.categoria = categoria;
        this.nomeEstudio = nomeEstudio;
        this.preco = preco;
        this.finalizado = finalizado;
        this.dataLancamento = dataLancamento;
    }

    public String getFinalizadoFormatado() {
        return this.finalizado ? "Sim" : "Nao";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlataformaId() {
        return plataformaId;
    }

    public void setPlataformaId(int plataformaId) {
        this.plataformaId = plataformaId;
    }

    public int getEstudioId() {
        return estudioId;
    }

    public void setEstudioId(int estudioId) {
        this.estudioId = estudioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNomePlataforma() {
        return nomePlataforma;
    }

    public void setNomePlataforma(String nomePlataforma) {
        this.nomePlataforma = nomePlataforma;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNomeEstudio() {
        return nomeEstudio;
    }

    public void setNomeEstudio(String nomeEstudio) {
        this.nomeEstudio = nomeEstudio;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
}
