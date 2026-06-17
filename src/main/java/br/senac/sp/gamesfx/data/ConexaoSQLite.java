package br.senac.sp.gamesfx.data;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexaoSQLite {

    private static final String DB_URL = System.getProperty("gamesfx.db.url", montarUrlPadrao());

    private ConexaoSQLite() {
    }

    private static String montarUrlPadrao() {
        Path caminho = Path.of(System.getProperty("user.home"), "banco_de_dados", "db_games.db");
        return "jdbc:sqlite:" + caminho.toString().replace('\\', '/');
    }

    public static Connection getConexao() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException erro) {
            throw new IllegalStateException("Falha ao conectar no banco SQLite: " + DB_URL, erro);
        }
    }
}