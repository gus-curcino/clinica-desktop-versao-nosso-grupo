package br.edu.imepac.clinica.daos;

import br.edu.imepac.clinica.entidades.Especialidade;
import br.edu.imepac.clinica.entidades.Medico;
import br.edu.imepac.clinica.interfaces.Persistente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas operações CRUD da entidade Medico.
 * Trata o relacionamento N:1 com Especialidade.
 */
public class MedicoDAO extends BaseDao implements Persistente<Medico> {

    @Override
    public boolean salvar(Medico entidade) {
        // Precisamos do ID da especialidade para salvar na tabela médico
        String sql = "INSERT INTO medico (nome, crm, especialidade_id) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, entidade.getNome());
            stmt.setString(2, entidade.getCrm());
            
            // Assume que o objeto Especialidade dentro de Medico não é nulo e tem um ID válido
            if (entidade.getEspecialidade() != null) {
                stmt.setLong(3, entidade.getEspecialidade().getId());
            } else {
                throw new SQLException("Médico deve possuir uma especialidade selecionada.");
            }

            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao salvar médico: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos(conn, stmt);
        }
    }

    @Override
    public boolean atualizar(Medico entidade) {
        String sql = "UPDATE medico SET nome = ?, crm = ?, especialidade_id = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, entidade.getNome());
            stmt.setString(2, entidade.getCrm());
            
            if (entidade.getEspecialidade() != null) {
                stmt.setLong(3, entidade.getEspecialidade().getId());
            } else {
                throw new SQLException("Médico deve possuir uma especialidade.");
            }
            
            stmt.setLong(4, entidade.getId());

            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar médico: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos(conn, stmt);
        }
    }

    @Override
    public boolean excluir(long id) {
        String sql = "DELETE FROM medico WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir médico: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos(conn, stmt);
        }
    }

    @Override
    public Medico buscarPorId(long id) {
        // Query com JOIN para trazer os dados da Especialidade junto
        String sql = "SELECT m.id, m.nome, m.crm, m.especialidade_id, " +
                     "e.nome as nome_esp, e.descricao as desc_esp " +
                     "FROM medico m " +
                     "INNER JOIN especialidade e ON m.especialidade_id = e.id " +
                     "WHERE m.id = ?";
                     
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Medico medico = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                medico = montarMedicoDoResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar médico por ID: " + e.getMessage());
        } finally {
            fecharRecursos(conn, stmt, rs);
        }

        return medico;
    }

    @Override
    public List<Medico> listarTodos() {
        // Query com JOIN para listar médicos já com os nomes das suas especialidades
        String sql = "SELECT m.id, m.nome, m.crm, m.especialidade_id, " +
                     "e.nome as nome_esp, e.descricao as desc_esp " +
                     "FROM medico m " +
                     "INNER JOIN especialidade e ON m.especialidade_id = e.id " +
                     "ORDER BY m.nome";
                     
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Medico> lista = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(montarMedicoDoResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar médicos: " + e.getMessage());
        } finally {
            fecharRecursos(conn, stmt, rs);
        }

        return lista;
    }

    // Método auxiliar para evitar repetição de código no preenchimento do objeto
    private Medico montarMedicoDoResultSet(ResultSet rs) throws SQLException {
        Medico m = new Medico();
        m.setId(rs.getLong("id"));
        m.setNome(rs.getString("nome"));
        m.setCrm(rs.getString("crm"));

        // Montando o objeto Especialidade aninhado
        Especialidade e = new Especialidade();
        e.setId(rs.getLong("especialidade_id"));
        e.setNome(rs.getString("nome_esp"));
        e.setDescricao(rs.getString("desc_esp"));

        m.setEspecialidade(e);
        
        return m;
    }
}
