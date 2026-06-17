package br.senac.sp.gamesfx.data.repository;

import br.senac.sp.gamesfx.data.ConexaoSQLite;
import br.senac.sp.gamesfx.model.Jogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class JogoRepository {

    public ObservableList<Jogo> getJogos() {

        String sql = "SELECT g.*, e.nome AS nome_estudio, p.nome AS nome_plataforma " +
                "FROM tb_games g " +
                "INNER JOIN tb_estudios e ON g.id_estudio = e.id " +
                "INNER JOIN tb_plataformas p ON g.id_plataforma = p.id";

        ObservableList<Jogo> listaJogos = FXCollections.observableArrayList();

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Jogo jogo = new Jogo();

                jogo.setId(rs.getInt("id"));
                jogo.setTitulo(rs.getString("titulo"));
                jogo.setCategoria(rs.getString("categoria"));
                jogo.setPreco(rs.getDouble("preco"));
                jogo.setDataLancamento(LocalDate.parse(rs.getString("data_lancamento")));
                jogo.setFinalizado(rs.getInt("finalizado") == 1);
                jogo.setEstudioId(rs.getInt("id_estudio"));
                jogo.setPlataformaId(rs.getInt("id_plataforma"));
                jogo.setNomeEstudio(rs.getString("nome_estudio"));
                jogo.setNomePlataforma(rs.getString("nome_plataforma"));

                listaJogos.add(jogo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaJogos;
    }

    public boolean salvar(Jogo jogo) {
        String sql = "INSERT INTO tb_games (titulo, categoria, preco, data_lancamento, finalizado, id_estudio, id_plataforma) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setString(1, jogo.getTitulo());
            stm.setString(2, jogo.getCategoria());
            stm.setDouble(3, jogo.getPreco());
            stm.setString(4, jogo.getDataLancamento().toString());
            stm.setInt(5, jogo.isFinalizado() ? 1 : 0);
            stm.setInt(6, jogo.getEstudioId());
            stm.setInt(7, jogo.getPlataformaId());

            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editar(Jogo jogo) {
        String sql = "UPDATE tb_games SET titulo = ?, categoria = ?, preco = ?, data_lancamento = ?, finalizado = ?, id_estudio = ?, id_plataforma = ? WHERE id = ?";

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setString(1, jogo.getTitulo());
            stm.setString(2, jogo.getCategoria());
            stm.setDouble(3, jogo.getPreco());
            stm.setString(4, jogo.getDataLancamento().toString());
            stm.setInt(5, jogo.isFinalizado() ? 1 : 0);
            stm.setInt(6, jogo.getEstudioId());
            stm.setInt(7, jogo.getPlataformaId());
            stm.setInt(8, jogo.getId());

            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotalJogos() {
        String sql = "SELECT COUNT(id) AS total_games FROM tb_games";

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total_games");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM tb_games WHERE id = ?";

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setInt(1, id);
            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
