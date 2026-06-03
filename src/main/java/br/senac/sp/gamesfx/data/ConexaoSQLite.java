package br.senac.sp.gamesfx.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQLite {

    private static Connection conexao;

    public static Connection getConexao() {
        // Mantido o seu caminho de banco de dados
        String url = "jdbc:sqlite:C:/Users/david.sribeiro4/banco_de_dados/db_games.db";

        try {
            conexao = DriverManager.getConnection(url);
            System.out.println("Conexão REALIZADA COM SUCESSO!");
            return conexao;
        } catch (SQLException erro) {
            System.out.println("Ocorreu um erro durante a conexão com o banco.");
            erro.printStackTrace();
            return null;
        }
    }

    // Atualizado com a lógica limpa e correta do professor
    public static void fecharConexao() {
        try {
            // ADICIONADO/CORRIGIDO: Verifica se existe uma conexão ativa antes de tentar fechar
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            // ADICIONADO DO PROFESSOR: Apenas imprime o rastro do erro sem estourar um RuntimeException
            e.printStackTrace();
        }
    }
}