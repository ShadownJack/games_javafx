package br.senac.sp.gamesfx.data.repository;

import br.senac.sp.gamesfx.data.ConexaoSQLite;
import br.senac.sp.gamesfx.model.Estudio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EstudioRepository {

    public ObservableList<Estudio> getEstudios() {
        String sql = "SELECT * FROM tb_estudios";
        ObservableList<Estudio> listaEstudios = FXCollections.observableArrayList();

        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Estudio estudio = new Estudio();

                estudio.setId(rs.getInt("id"));
                estudio.setTitulo(rs.getString("nome"));
                estudio.setPaisOrigem(rs.getString("pais_origem"));
                estudio.setAnoFundacao(LocalDate.parse(rs.getString("ano_fundacao")));
                estudio.setSite(rs.getString("site"));
                estudio.setAtivo(rs.getInt("ativo") == 1);

                listaEstudios.add(estudio);
            }

            ConexaoSQLite.fecharConexao();
            return listaEstudios;

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro na leitura dos dados do banco.");
            e.printStackTrace();
            return null;
        }
    }

    public void salvar(Estudio estudio) {
        String sql = "INSERT INTO tb_estudios (nome, pais_origem, ano_fundacao, site, ativo) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);

            stm.setString(1, estudio.getTitulo());
            stm.setString(2, estudio.getPaisOrigem());
            stm.setString(3, estudio.getAnoFundacao().toString());
            stm.setString(4, estudio.getSite());
            stm.setInt(5, estudio.isAtivo() ? 1 : 0);

            stm.executeUpdate();

            ConexaoSQLite.fecharConexao();

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro na gravação.");
            e.printStackTrace();
        }
    }

    public int getTotalEstudios() {
        String sql = "SELECT COUNT(id) AS total_estudios FROM tb_estudios";
        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            rs.next();
            int total = rs.getInt("total_estudios");

            ConexaoSQLite.fecharConexao();
            return total;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int excluir(int id) {
        String sql = "DELETE FROM tb_estudios WHERE id = ?";
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

    public void editar(Estudio estudio) {
        String sql = "UPDATE tb_estudios SET " +
                "nome = ?, " +
                "pais_origem = ?, " +
                "ano_fundacao = ?, " +
                "site = ?, " +
                "ativo = ? " +
                "WHERE id = ?";

        try {
            PreparedStatement stm = ConexaoSQLite.getConexao().prepareStatement(sql);

            stm.setString(1, estudio.getTitulo());
            stm.setString(2, estudio.getPaisOrigem());
            stm.setString(3, estudio.getAnoFundacao().toString());
            stm.setString(4, estudio.getSite());
            stm.setInt(5, estudio.isAtivo() ? 1 : 0);
            stm.setInt(6, estudio.getId());

            stm.executeUpdate();

            ConexaoSQLite.fecharConexao();
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro na alteração do registro.");
            e.printStackTrace();
        }
    }
}