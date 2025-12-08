/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.imepac.clinica.daos;

/**
 *
 * @author luscas
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        // Ajuste estes valores conforme o seu banco no MySQL Workbench
        String url = "jdbc:mysql://localhost:3306/?user=root"; 
        String usuario = "root";
        String senha = "8741";

        try {
            // Tenta estabelecer a conexão
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("✅ Sucesso! Conexão realizada com o banco de dados.");
            conexao.close();
        } catch (SQLException e) {
            System.out.println("❌ Falha na conexão!");
            e.printStackTrace(); // Mostra o erro detalhado no console
        }
    }
}
