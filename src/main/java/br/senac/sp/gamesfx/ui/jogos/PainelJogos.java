package br.senac.sp.gamesfx.ui.jogos;

import br.senac.sp.gamesfx.model.Jogo;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class PainelJogos {
    public VBox criarPainelJogos(){
        VBox painelJogos = new VBox();
        painelJogos.setPadding(new Insets(5,20,20,20));
        painelJogos.setStyle("-fx-background-color: transparent");

        // Título do painel jogos
        Label lblTitulo = new Label("Listagem de Jogos");
        lblTitulo.setStyle("-fx-font-size: 24; -fx-text-fill: #000000; -fx-font-weight: bold");

        // Linha abaixo do label
        Separator linha = new Separator();




        // Tabela com a lista de jogos
        TableView<Jogo> tabelaJogos = new TableView<>();

        // Criar colunas da tabela
        TableColumn<Jogo, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Jogo, String> colunaTitulo = new TableColumn<>("TÍTULO");
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaTitulo.setPrefWidth(70);

        TableColumn<Jogo, String> colunaPlataforma = new TableColumn<>("PLATAFORMA");
        colunaPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        colunaPlataforma.setPrefWidth(100);

        TableColumn<Jogo, String> colunaCategoria = new TableColumn<>("CATEGORIA");
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colunaCategoria.setPrefWidth(80);

        TableColumn<Jogo, String> colunaEstudio = new TableColumn<>("ESTUDIO");
        colunaEstudio.setCellValueFactory(new PropertyValueFactory<>("estudio"));
        colunaEstudio.setPrefWidth(75);

        TableColumn<Jogo, Integer> colunaPreco = new TableColumn<>("PREÇO");
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaPreco.setPrefWidth(60);

        TableColumn<Jogo, LocalDate> colunaDataLancamento = new TableColumn<>("LANÇAMENTO");
        colunaDataLancamento.setCellValueFactory(new PropertyValueFactory<>("lancamento"));
        colunaDataLancamento.setPrefWidth(100);

        TableColumn<Jogo, Boolean> colunaFinalizado = new TableColumn<>("FINALIZADO");
        colunaFinalizado.setCellValueFactory(new PropertyValueFactory<>("finalizado"));
        colunaFinalizado.setPrefWidth(80);

        // Adicionar as colunas na tabela
        tabelaJogos.getColumns().addAll(colunaId, colunaTitulo, colunaPlataforma, colunaCategoria, colunaEstudio, colunaDataLancamento, colunaPreco, colunaFinalizado);

        // Adicionar o label no painel
        painelJogos.getChildren().addAll(lblTitulo, linha, tabelaJogos);

        return painelJogos;
    }
}
