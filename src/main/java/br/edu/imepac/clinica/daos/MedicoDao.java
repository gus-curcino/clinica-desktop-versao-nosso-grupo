package br.edu.imepac.clinica.daos;

import br.edu.imepac.clinica.entidades.Especialidade;
import br.edu.imepac.clinica.entidades.Medico;
import br.edu.imepac.clinica.interfaces.Persistente; // Assumindo interface CRUD padrão
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDao extends BaseDao implements Persistente<Medico> {

    /**
     * Usado na tela de Login Inicial.
     * Valida CRM e Senha.
     */
    public Medico autenticar(String crm, String senha) {
        String sql = "SELECT m.*, e.nome as esp_nome FROM medico m " +
                     "INNER JOIN especialidade e ON m.especialidade_id = e.id " +
                     "WHERE m.crm = ? AND m.senha = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Medico medico = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, crm);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();

            if (rs.next()) {
                medico = new Medico();
                medico.setId(rs.getLong("id"));
                medico.setNome(rs.getString("nome"));
                medico.setCrm(rs.getString("crm"));
                medico.setSenha(rs.getString("senha"));
                // Preenche especialidade
                Especialidade esp = new Especialidade();
                esp.setId(rs.getLong("especialidade_id"));
                esp.setNome(rs.getString("esp_nome"));
                medico.setEspecialidade(esp);
            }
        } catch (SQLException e) {
            System.err.println("Erro login médico: " + e.getMessage());
        } finally {
            fecharRecursos(conn, stmt, rs);
        }
        return medico;
    }

    // Usado pela SECRETÁRIA na tela "Cadastrar Médico"
    @Override
    public boolean salvar(Medico m) {
        String sql = "INSERT INTO medico (nome, crm, cpf, senha, especialidade_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getCrm());
            stmt.setString(3, m.getCpf());
            stmt.setString(4, m.getSenha());
            stmt.setLong(5, m.getEspecialidade().getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar médico: " + e.getMessage());
            return false;
        }
    }
    
    // Métodos atualizar, excluir, buscarPorId, listarTodos (CRUD padrão omitido para brevidade, mas seguem o padrão do BaseDao)
    @Override public boolean atualizar(Medico m) { return false; /* Implementar update SQL */ }
    @Override public boolean excluir(long id) { return false; /* Implementar delete SQL */ }
    @Override public Medico buscarPorId(long id) { return null; /* Implementar select SQL */ }
    
    @Override
    public List<Medico> listarTodos() {
        String sql = "SELECT * FROM medico"; // Simplificado
        List<Medico> lista = new ArrayList<>();
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getLong("id"));
                m.setNome(rs.getString("nome"));
                m.setCrm(rs.getString("crm"));
                lista.add(m);
            }
        } catch (SQLException e) {}
        return lista;
    }
}