package br.senac.sp.gamesfx.data.repository;

import br.senac.sp.gamesfx.data.ConexaoSQLite;
import br.senac.sp.gamesfx.model.Estudio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class EstudioRepository {

    public ObservableList<Estudio> getEstudios() {
        String sql = "SELECT * FROM tb_estudios";
        ObservableList<Estudio> listaEstudios = FXCollections.observableArrayList();

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Estudio estudio = new Estudio();
                estudio.setId(rs.getInt("id"));
                estudio.setNome(rs.getString("nome"));
                estudio.setPaisOrigem(rs.getString("pais_origem"));
                estudio.setAnoFundacao(LocalDate.parse(rs.getString("ano_fundacao")));
                estudio.setSite(rs.getString("site"));
                estudio.setAtivo(rs.getInt("ativo") == 1);
                listaEstudios.add(estudio);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaEstudios;
    }

    public boolean salvar(Estudio estudio) {
        String sql = "INSERT INTO tb_estudios (nome, pais_origem, ano_fundacao, site, ativo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setString(1, estudio.getNome());
            stm.setString(2, estudio.getPaisOrigem());
            stm.setString(3, estudio.getAnoFundacao().toString());
            stm.setString(4, estudio.getSite());
            stm.setInt(5, estudio.isAtivo() ? 1 : 0);

            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotalEstudios() {
        String sql = "SELECT COUNT(id) AS total_estudios FROM tb_estudios";

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total_estudios");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM tb_estudios WHERE id = ?";

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setInt(1, id);
            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editar(Estudio estudio) {
        String sql = "UPDATE tb_estudios SET nome = ?, pais_origem = ?, ano_fundacao = ?, site = ?, ativo = ? WHERE id = ?";

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setString(1, estudio.getNome());
            stm.setString(2, estudio.getPaisOrigem());
            stm.setString(3, estudio.getAnoFundacao().toString());
            stm.setString(4, estudio.getSite());
            stm.setInt(5, estudio.isAtivo() ? 1 : 0);
            stm.setInt(6, estudio.getId());

            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
