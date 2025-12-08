package br.edu.imepac.clinica.entidades;

public class Medico {
    private long id;
    private String nome;
    private String crm;
    private String cpf;
    private String senha; // Login do Médico
    private Especialidade especialidade;

    public Medico() {}
    
    // Construtor completo
    public Medico(String nome, String crm, String cpf, String senha, Especialidade especialidade) {
        this.nome = nome;
        this.crm = crm;
        this.cpf = cpf;
        this.senha = senha;
        this.especialidade = especialidade;
    }

    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Especialidade getEspecialidade() { return especialidade; }
    public void setEspecialidade(Especialidade especialidade) { this.especialidade = especialidade; }
}