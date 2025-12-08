package br.edu.imepac.clinica.daos;

import br.edu.imepac.clinica.entidades.Secretaria;
import br.edu.imepac.clinica.interfaces.Persistente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SecretariaDao extends BaseDao implements Persistente<Secretaria> {

    /**
     * Método essencial para o LOGIN da Secretária.
     * Verifica se o login e senha existem no banco.
     */
    public Secretaria autenticar(String login, String senha) {
        String sql = "SELECT * FROM secretaria WHERE login = ? AND senha = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Secretaria secretaria = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();

            if (rs.next()) {
                secretaria = new Secretaria();
                secretaria.setId(rs.getLong("id"));
                secretaria.setNome(rs.getString("nome"));
                secretaria.setCpf(rs.getString("cpf"));
                secretaria.setLogin(rs.getString("login"));
                secretaria.setSenha(rs.getString("senha"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao autenticar secretaria: " + e.getMessage());
        } finally {
            fecharRecursos(conn, stmt, rs);
        }
        return secretaria;
    }

    @Override
    public boolean salvar(Secretaria s) {
        String sql = "INSERT INTO secretaria (nome, cpf, login, senha) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, s.getNome());
            stmt.setString(2, s.getCpf());
            stmt.setString(3, s.getLogin());
            stmt.setString(4, s.getSenha());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar secretaria: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean atualizar(Secretaria s) {
        String sql = "UPDATE secretaria SET nome = ?, cpf = ?, login = ?, senha = ? WHERE id = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, s.getNome());
            stmt.setString(2, s.getCpf());
            stmt.setString(3, s.getLogin());
            stmt.setString(4, s.getSenha());
            stmt.setLong(5, s.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar secretaria: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean excluir(long id) {
        String sql = "DELETE FROM secretaria WHERE id = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir secretaria: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Secretaria buscarPorId(long id) {
        String sql = "SELECT * FROM secretaria WHERE id = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Secretaria s = new Secretaria();
                s.setId(rs.getLong("id"));
                s.setNome(rs.getString("nome"));
                s.setCpf(rs.getString("cpf"));
                s.setLogin(rs.getString("login"));
                s.setSenha(rs.getString("senha"));
                return s;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar secretaria: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Secretaria> listarTodos() {
        String sql = "SELECT * FROM secretaria";
        List<Secretaria> lista = new ArrayList<>();
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Secretaria s = new Secretaria();
                s.setId(rs.getLong("id"));
                s.setNome(rs.getString("nome"));
                s.setCpf(rs.getString("cpf"));
                s.setLogin(rs.getString("login"));
                s.setSenha(rs.getString("senha"));
                lista.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar secretarias: " + e.getMessage());
        }
        return lista;
    }
}