package br.senac.sp.gamesfx.data.repository;

import br.senac.sp.gamesfx.data.ConexaoSQLite;
import br.senac.sp.gamesfx.model.Jogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class JogoRepository {

    public ObservableList<Jogo> getJogos() {
        // AJUSTADO: Busca os nomes dos estúdios e plataformas usando LEFT JOIN
        String sql = "SELECT g.*, e.nome AS nome_estudio, p.nome AS nome_plataforma " +
                "FROM tb_games g " +
                "LEFT JOIN tb_estudios e ON g.id_estudio = e.id " +
                "LEFT JOIN tb_plataformas p ON g.id_plataforma = p.id";

        ObservableList<Jogo> listaJogos = FXCollections.observableArrayList();

        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Jogo jogo = new Jogo();

                jogo.setId(rs.getInt("id"));
                jogo.setTitulo(rs.getString("titulo"));
                jogo.setCategoria(rs.getString("categoria"));
                jogo.setPreco(rs.getDouble("preco"));
                jogo.setDataLancamento(LocalDate.parse(rs.getString("data_lancamento")));
                jogo.setFinalizado(rs.getInt("finalizado") == 1);

                // Popula o modelo com o texto vindo dos relacionamentos obtidos no JOIN
                jogo.setEstudio(rs.getString("nome_estudio"));
                jogo.setPlataforma(rs.getString("nome_plataforma"));

                listaJogos.add(jogo);
            }

            ConexaoSQLite.fecharConexao();
            return listaJogos;

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro na leitura dos dados do banco.");
            e.printStackTrace();
            return null;
        }
    }

    public void salvar(Jogo jogo) {
        // AJUSTADO: Insere os IDs nas colunas de restrição correspondentes
        String sql = "INSERT INTO tb_games (titulo, categoria, preco, data_lancamento, finalizado, id_estudio, id_plataforma) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);

            stm.setString(1, jogo.getTitulo());
            stm.setString(2, jogo.getCategoria());
            stm.setDouble(3, jogo.getPreco());
            stm.setString(4, jogo.getDataLancamento().toString());
            stm.setInt(5, jogo.isFinalizado() ? 1 : 0);
            stm.setInt(6, Integer.parseInt(jogo.getEstudio())); // ID vindo da tela convertido em Integer
            stm.setInt(7, Integer.parseInt(jogo.getPlataforma())); // ID vindo da tela convertido em Integer

            stm.executeUpdate();

            ConexaoSQLite.fecharConexao();

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro na gravação.");
            e.printStackTrace();
        }
    }

    public int getTotalJogos(){
        String sql = "SELECT COUNT(id) AS total_games FROM tb_games";
        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            rs.next();
            int total = rs.getInt("total_games");

            ConexaoSQLite.fecharConexao();
            return total;
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public int excluir(int id){
        String sql = "DELETE FROM tb_games WHERE id = ?";
        try {
            PreparedStatement stm = ConexaoSQLite
                    .getConexao()
                    .prepareStatement(sql);
            stm.setInt(1, id);
            int resultado = stm.executeUpdate();

            ConexaoSQLite.fecharConexao();

            return resultado;
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public void editar(Jogo jogo) {
        // AJUSTADO: Altera o registro atualizando as colunas id_estudio e id_plataforma
        String sql = "UPDATE tb_games SET " +
                "titulo = ?, " +
                "categoria = ?, " +
                "preco = ?, " +
                "data_lancamento = ?, " +
                "finalizado = ?, " +
                "id_estudio = ?, " +
                "id_plataforma = ? " +
                "WHERE id = ?";

        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);
            stm.setString(1, jogo.getTitulo());
            stm.setString(2, jogo.getCategoria());
            stm.setDouble(3, jogo.getPreco());
            stm.setString(4, jogo.getDataLancamento().toString());
            stm.setInt(5, jogo.isFinalizado() ? 1 : 0);
            stm.setInt(6, Integer.parseInt(jogo.getEstudio()));
            stm.setInt(7, Integer.parseInt(jogo.getPlataforma()));
            stm.setInt(8, jogo.getId());

            stm.executeUpdate();

            ConexaoSQLite.fecharConexao();
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro na alteração do registro.");
            e.printStackTrace();
        }
    }
}