package br.senac.sp.gamesfx.model;

public class Fabricante {
    private int id;
    private String nome;
    private String paisOrigem;

    public Fabricante() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getPaisOrigem() { return paisOrigem; }
    public void setPaisOrigem(String paisOrigem) { this.paisOrigem = paisOrigem; }

    @Override
    public String toString() {
        return this.nome;
    }
}