package com.example.appdoduduethiago;

public class Tarefa {
    private String titulo;
    private String Descricao;
    private boolean completo; // Estado da CheckBox
    private String data;

    public Tarefa(String titulo, String Descricao, boolean completo, String data) {
        this.titulo = titulo;
        this.Descricao = Descricao;
        this.completo = completo;
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompletado(boolean completo) {
        this.completo = completo;
    }

    public String getData() {
        return data;
    }
}
