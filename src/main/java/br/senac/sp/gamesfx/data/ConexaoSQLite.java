package br.senac.sp.gamesfx.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQLite {

    public static Connection getConexaoSQLite() {
        // String de conexão - URL do banco de dados
        String url = "jdbc:sqlite:/C:\\Users\\david.sribeiro4\\OneDrive - SENAC - SP\\banco_de_dados\\db_games";

        try {
            Connection conexao = DriverManager.getConnection(url);
            return conexao;
        }catch (SQLException erro){
            System.out.println("Ocorreu um erro durante a conexão com o banco.");
            erro.printStackTrace();
            return null;
        }
    }

}
