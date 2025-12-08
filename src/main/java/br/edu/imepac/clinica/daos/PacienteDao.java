package br.edu.imepac.clinica.daos;

import br.edu.imepac.clinica.entidades.Paciente;
import br.edu.imepac.clinica.interfaces.Persistente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDao extends BaseDao implements Persistente<Paciente> {

    @Override
    public boolean salvar(Paciente p) {
        String sql = "INSERT INTO paciente (nome, cpf, convenio) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getConvenio());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar paciente: " + e.getMessage());
            return false;
        }
    }
    
    // Método auxiliar para buscar paciente pelo CPF (Útil para Agendar Consulta)
    public Paciente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM paciente WHERE cpf = ?";
        // ... implementação padrão JDBC ...
        return null; // Simplificado para exemplo
    }

    @Override public boolean atualizar(Paciente p) { return false; }
    @Override public boolean excluir(long id) { return false; }
    @Override public Paciente buscarPorId(long id) { return null; }
    
    @Override
    public List<Paciente> listarTodos() {
        String sql = "SELECT * FROM paciente";
        List<Paciente> lista = new ArrayList<>();
        // ... implementação padrão com While(rs.next()) ...
        return lista;
    }
}