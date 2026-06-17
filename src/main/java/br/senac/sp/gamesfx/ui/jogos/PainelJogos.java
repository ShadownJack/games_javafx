package br.senac.sp.gamesfx.ui.jogos;

import br.senac.sp.gamesfx.data.repository.JogoRepository;
import br.senac.sp.gamesfx.model.Jogo;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PainelJogos {

    private final Stage stage;
    private final JogoRepository repository = new JogoRepository();

    public PainelJogos(Stage stage) {
        this.stage = stage;
    }

    public VBox criarPainelJogos() {
        VBox painelJogos = new VBox();
        painelJogos.setSpacing(10);
        painelJogos.setPadding(new Insets(5, 20, 20, 20));
        painelJogos.setStyle("-fx-background-color: #0d253e");

        Label lblTitulo = new Label("Listagem de Jogos");
        lblTitulo.setStyle("-fx-font-size: 24; -fx-text-fill: #ffffff; -fx-font-weight: bold");

        Separator linha = new Separator();

        TableView<Jogo> tabelaJogos = new TableView<>();
        VBox.setVgrow(tabelaJogos, Priority.ALWAYS);

        TableColumn<Jogo, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Jogo, String> colunaTitulo = new TableColumn<>("TITULO");
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaTitulo.setPrefWidth(180);

        TableColumn<Jogo, String> colunaPlataforma = new TableColumn<>("PLATAFORMA");
        colunaPlataforma.setCellValueFactory(new PropertyValueFactory<>("nomePlataforma"));
        colunaPlataforma.setPrefWidth(120);

        TableColumn<Jogo, String> colunaEstudio = new TableColumn<>("ESTUDIO");
        colunaEstudio.setCellValueFactory(new PropertyValueFactory<>("nomeEstudio"));
        colunaEstudio.setPrefWidth(100);

        TableColumn<Jogo, String> colunaCategoria = new TableColumn<>("CATEGORIA");
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colunaCategoria.setPrefWidth(90);

        TableColumn<Jogo, Double> colunaPreco = new TableColumn<>("PRECO");
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaPreco.setPrefWidth(80);

        TableColumn<Jogo, String> colunaDataLancamento = new TableColumn<>("LANCAMENTO");
        colunaDataLancamento.setCellValueFactory(cellData -> {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = cellData.getValue().getDataLancamento().format(formatador);
            return new SimpleStringProperty(dataFormatada);
        });
        colunaDataLancamento.setPrefWidth(100);

        TableColumn<Jogo, String> colunaFinalizado = new TableColumn<>("FINALIZADO");
        colunaFinalizado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFinalizadoFormatado())
        );
        colunaFinalizado.setPrefWidth(80);

        tabelaJogos.getColumns().addAll(
                colunaId, colunaTitulo, colunaPlataforma, colunaEstudio,
                colunaCategoria, colunaPreco, colunaDataLancamento, colunaFinalizado
        );

        tabelaJogos.setItems(repository.getJogos());

        HBox painelBotoes = new HBox(30);
        painelBotoes.setPadding(new Insets(20, 0, 0, 0));
        painelBotoes.setAlignment(Pos.BASELINE_RIGHT);

        Button btnAdicionar = criarBotao("Adicionar", "/imagens/save-game.png");
        btnAdicionar.setOnAction(e -> {
            TelaJogo telaJogo = new TelaJogo();
            telaJogo.criarTela(stage);
            tabelaJogos.setItems(repository.getJogos());
        });

        Button btnEditar = criarBotao("Editar", "/imagens/pencil.png");
        btnEditar.setOnAction(e -> {
            Jogo jogoEditar = tabelaJogos.getSelectionModel().getSelectedItem();
            if (jogoEditar != null) {
                TelaJogo telaJogo = new TelaJogo(jogoEditar);
                telaJogo.criarTela(stage);
                tabelaJogos.setItems(repository.getJogos());
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Edicao de jogo");
                alerta.setHeaderText("Para editar um jogo, selecione-o na lista.");
                alerta.showAndWait();
            }
        });

        Button btnExcluir = criarBotao("Excluir", "/imagens/garbage.png");
        btnExcluir.setOnAction(e -> {
            Jogo jogoExcluir = tabelaJogos.getSelectionModel().getSelectedItem();

            if (jogoExcluir == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Exclusao de jogo");
                alerta.setHeaderText("Para excluir um jogo, selecione-o na lista.");
                alerta.showAndWait();
                return;
            }

            Alert confirmaExclusao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmaExclusao.setTitle("Exclusao de jogo");
            confirmaExclusao.setHeaderText("Voce esta prestes a excluir um jogo.");
            confirmaExclusao.setContentText("Tem certeza que deseja continuar?");

            Optional<ButtonType> resposta = confirmaExclusao.showAndWait();
            if (resposta.isPresent() && resposta.get() == ButtonType.OK) {
                repository.excluir(jogoExcluir.getId());
                tabelaJogos.setItems(repository.getJogos());
            }
        });

        painelBotoes.getChildren().addAll(btnAdicionar, btnEditar, btnExcluir);
        painelJogos.getChildren().addAll(lblTitulo, linha, tabelaJogos, painelBotoes);

        return painelJogos;
    }

    private Button criarBotao(String textoBotao, String urlImagem) {
        Button button = new Button(textoBotao);

        try {
            Image image = new Image(getClass().getResourceAsStream(urlImagem));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(24);
            imageView.setFitWidth(24);
            button.setGraphic(imageView);
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + urlImagem);
        }

        button.setPrefWidth(100);
        button.setPrefHeight(50);
        button.setContentDisplay(ContentDisplay.TOP);

        return button;
    }
}
