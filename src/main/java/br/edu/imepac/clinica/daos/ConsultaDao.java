package br.edu.imepac.clinica.daos;

import br.edu.imepac.clinica.entidades.Consulta;
import br.edu.imepac.clinica.entidades.Medico;
import br.edu.imepac.clinica.entidades.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDao extends BaseDao {

    // Funcionalidade da SECRETÁRIA: Agendar
    public boolean salvar(Consulta c) {
        String sql = "INSERT INTO consulta (data_hora, motivo, medico_id, paciente_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, c.getDataHora());
            stmt.setString(2, c.getMotivo());
            stmt.setLong(3, c.getMedico().getId());
            stmt.setLong(4, c.getPaciente().getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao agendar: " + e.getMessage());
            return false;
        }
    }

    // Funcionalidade do MÉDICO: Ver sua Agenda
    public List<Consulta> listarPorMedico(long idMedico) {
        String sql = "SELECT c.id, c.data_hora, c.motivo, " +
                     "p.nome as pac_nome, p.cpf as pac_cpf, p.convenio " +
                     "FROM consulta c " +
                     "INNER JOIN paciente p ON c.paciente_id = p.id " +
                     "WHERE c.medico_id = ? " +
                     "ORDER BY c.data_hora";
        
        List<Consulta> agenda = new ArrayList<>();
        
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idMedico);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getLong("id"));
                c.setDataHora(rs.getTimestamp("data_hora"));
                c.setMotivo(rs.getString("motivo"));

                // Monta objeto Paciente para exibição na tabela do Médico
                Paciente p = new Paciente();
                p.setNome(rs.getString("pac_nome"));
                p.setCpf(rs.getString("pac_cpf"));
                p.setConvenio(rs.getString("convenio"));
                
                c.setPaciente(p);
                agenda.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar agenda: " + e.getMessage());
        }
        return agenda;
    }
}