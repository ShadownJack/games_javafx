package br.senac.sp.gamesfx.data.repository;

import br.senac.sp.gamesfx.data.ConexaoSQLite;
import br.senac.sp.gamesfx.model.Fabricante;
import br.senac.sp.gamesfx.model.Plataforma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class PlataformaRepository {

    public ObservableList<Fabricante> getFabricantes() {
        String sql = "SELECT id, nome FROM tb_fabricantes ORDER BY nome ASC";
        ObservableList<Fabricante> lista = FXCollections.observableArrayList();

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Fabricante fabricante = new Fabricante();
                fabricante.setId(rs.getInt("id"));
                fabricante.setNome(rs.getString("nome"));
                lista.add(fabricante);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public ObservableList<Plataforma> getPlataformas() {
        String sql = "SELECT p.*, f.nome AS nome_fabricante FROM tb_plataformas p LEFT JOIN tb_fabricantes f ON p.id_fabricante = f.id";
        ObservableList<Plataforma> listaPlataformas = FXCollections.observableArrayList();

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Plataforma plataforma = new Plataforma();
                plataforma.setId(rs.getInt("id"));
                plataforma.setNome(rs.getString("nome"));
                plataforma.setFabricante(rs.getString("nome_fabricante"));
                plataforma.setFabricanteId(rs.getInt("id_fabricante"));
                plataforma.setAnoLancamento(LocalDate.parse(rs.getString("ano_lancamento")));
                plataforma.setGeracao(rs.getInt("geracao"));
                plataforma.setAtivo(rs.getInt("ativa") == 1);
                listaPlataformas.add(plataforma);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaPlataformas;
    }

    public boolean salvar(Plataforma plataforma) {
        String sql = "INSERT INTO tb_plataformas (nome, id_fabricante, ano_lancamento, geracao, ativa) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setString(1, plataforma.getNome());
            stm.setInt(2, plataforma.getFabricanteId());
            stm.setString(3, plataforma.getAnoLancamento().toString());
            stm.setInt(4, plataforma.getGeracao());
            stm.setInt(5, plataforma.isAtivo() ? 1 : 0);

            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editar(Plataforma plataforma) {
        String sql = "UPDATE tb_plataformas SET nome = ?, id_fabricante = ?, ano_lancamento = ?, geracao = ?, ativa = ? WHERE id = ?";

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setString(1, plataforma.getNome());
            stm.setInt(2, plataforma.getFabricanteId());
            stm.setString(3, plataforma.getAnoLancamento().toString());
            stm.setInt(4, plataforma.getGeracao());
            stm.setInt(5, plataforma.isAtivo() ? 1 : 0);
            stm.setInt(6, plataforma.getId());

            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotalPlataformas() {
        String sql = "SELECT COUNT(id) AS total_plataformas FROM tb_plataformas";

        try (Connection conexao = ConexaoSQLite.getConexao();
             PreparedStatement stm = conexao.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total_plataformas");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM tb_plataformas WHERE id = ?";

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
