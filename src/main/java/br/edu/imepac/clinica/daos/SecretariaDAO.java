package br.edu.imepac.clinica.daos;

import br.edu.imepac.clinica.entidades.Secretaria;
import br.edu.imepac.clinica.interfaces.Persistente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SecretariaDAO extends BaseDao implements Persistente<Secretaria> {

    @Override
    public boolean salvar(Secretaria entidade) {
        String sql = "INSERT INTO secretarias (nome, cpf, login, senha) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, entidade.getNome());
            stmt.setString(2, entidade.getCpf());
            stmt.setString(3, entidade.getLogin());
            stmt.setString(4, entidade.getSenha());

            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao salvar secretaria: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos(conn, stmt);
        }
    }

    @Override
    public boolean atualizar(Secretaria entidade) {
        String sql = "UPDATE secretarias SET nome = ?, cpf = ?, login = ?, senha = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, entidade.getNome());
            stmt.setString(2, entidade.getCpf());
            stmt.setString(3, entidade.getLogin());
            stmt.setString(4, entidade.getSenha());
            stmt.setLong(5, entidade.getId());

            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar secretaria: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos(conn, stmt);
        }
    }

    @Override
    public boolean excluir(long id) {
        String sql = "DELETE FROM secretarias WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir secretaria: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos(conn, stmt);
        }
    }

    @Override
    public Secretaria buscarPorId(long id) {
        String sql = "SELECT id, nome, cpf, login, senha FROM secretarias WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Secretaria secretaria = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

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
            System.err.println("Erro ao buscar secretaria por ID: " + e.getMessage());
        } finally {
            fecharRecursos(conn, stmt, rs);
        }

        return secretaria;
    }

    @Override
    public List<Secretaria> listarTodos() {
        String sql = "SELECT id, nome, cpf, login, senha FROM secretarias";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Secretaria> lista = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Secretaria secretaria = new Secretaria();
                secretaria.setId(rs.getLong("id"));
                secretaria.setNome(rs.getString("nome"));
                secretaria.setCpf(rs.getString("cpf"));
                secretaria.setLogin(rs.getString("login"));
                secretaria.setSenha(rs.getString("senha"));
                lista.add(secretaria);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar secretarias: " + e.getMessage());
        } finally {
            fecharRecursos(conn, stmt, rs);
        }

        return lista;
    }
    
    // Método adicional para login
    public Secretaria autenticar(String login, String senha) {
        String sql = "SELECT id, nome, cpf FROM secretarias WHERE login = ? AND senha = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                Secretaria s = new Secretaria();
                s.setId(rs.getLong("id"));
                s.setNome(rs.getString("nome"));
                s.setCpf(rs.getString("cpf"));
                s.setLogin(login);
                return s;
            }
        } catch (SQLException e) {
            System.err.println("Erro na autenticação: " + e.getMessage());
        } finally {
            fecharRecursos(conn, stmt, rs);
        }
        return null;
    }
}