package br.edu.imepac.clinica.entidades;

public class Paciente {
    private long id;
    private String nome;
    private String cpf;
    private String convenio;

    public Paciente() {}

    public Paciente(String nome, String cpf, String convenio) {
        this.nome = nome;
        this.cpf = cpf;
        this.convenio = convenio;
    }

    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getConvenio() { return convenio; }
    public void setConvenio(String convenio) { this.convenio = convenio; }
}