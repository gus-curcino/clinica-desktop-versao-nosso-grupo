package br.edu.imepac.clinica.entidades;
import java.sql.Timestamp; // Para data e hora

public class Consulta {
    private long id;
    private Timestamp dataHora;
    private String motivo;
    private Paciente paciente;
    private Medico medico;

    public Consulta() {}

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Timestamp getDataHora() { return dataHora; }
    public void setDataHora(Timestamp dataHora) { this.dataHora = dataHora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
}