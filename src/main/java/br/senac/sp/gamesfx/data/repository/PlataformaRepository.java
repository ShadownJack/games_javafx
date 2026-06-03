package br.senac.sp.gamesfx.data.repository;

import br.senac.sp.gamesfx.data.ConexaoSQLite;
import br.senac.sp.gamesfx.model.Plataforma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlataformaRepository {

    public ObservableList<Plataforma> getPlataformas() {
        String sql = "SELECT * FROM tb_plataformas";
        ObservableList<Plataforma> listaPlataformas = FXCollections.observableArrayList();

        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Plataforma plataforma = new Plataforma();

                plataforma.setId(rs.getInt("id"));
                plataforma.setTitulo(rs.getString("nome"));
                plataforma.setFabricante(rs.getString("fabricante"));
                plataforma.setAnoLancamento(LocalDate.parse(rs.getString("ano_lancamento")));
                plataforma.setGeracao(rs.getInt("geracao"));
                plataforma.setAtivo(rs.getInt("ativo") == 1);

                listaPlataformas.add(plataforma);
            }

            ConexaoSQLite.fecharConexao();
            return listaPlataformas;

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro na leitura dos dados do banco.");
            e.printStackTrace();
            return null;
        }
    }

    public void salvar(Plataforma plataforma) {
        String sql = "INSERT INTO tb_plataformas (nome, fabricante, ano_lancamento, geracao, ativa) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);

            stm.setString(1, plataforma.getTitulo());
            stm.setString(2, plataforma.getFabricante());
            stm.setString(3, plataforma.getAnoLancamento().toString());
            stm.setInt(4, plataforma.getGeracao());
            stm.setInt(5, plataforma.isAtivo() ? 1 : 0);

            stm.executeUpdate();

            ConexaoSQLite.fecharConexao();

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro na gravação.");
            e.printStackTrace();
        }
    }

    public int getTotalPlataformas() {
        String sql = "SELECT COUNT(id) AS total_plataformas FROM tb_plataformas";
        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            rs.next();
            int total = rs.getInt("total_plataformas");

            ConexaoSQLite.fecharConexao();
            return total;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int excluir(int id) {
        String sql = "DELETE FROM tb_plataformas WHERE id = ?";
        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);
            stm.setInt(1, id);
            int resultado = stm.executeUpdate();

            ConexaoSQLite.fecharConexao();
            return resultado;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void editar(Plataforma plataforma) {
        String sql = "UPDATE tb_plataformas SET " +
                "nome = ?, " +
                "fabricante = ?, " +
                "ano_lancamento = ?, " +
                "geracao = ?, " +
                "ativa = ? " +
                "WHERE id = ?";

        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);

            stm.setString(1, plataforma.getTitulo());
            stm.setString(2, plataforma.getFabricante());
            stm.setString(3, plataforma.getAnoLancamento().toString());
            stm.setInt(4, plataforma.getGeracao());
            stm.setInt(5, plataforma.isAtivo() ? 1 : 0);
            stm.setInt(6, plataforma.getId());

            stm.executeUpdate();

            ConexaoSQLite.fecharConexao();
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro na alteração do registro.");
            e.printStackTrace();
        }
    }
}