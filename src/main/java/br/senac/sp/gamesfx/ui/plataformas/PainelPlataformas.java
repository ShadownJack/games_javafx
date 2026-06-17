package br.senac.sp.gamesfx.ui.plataformas;

import br.senac.sp.gamesfx.data.repository.PlataformaRepository;
import br.senac.sp.gamesfx.model.Plataforma;
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

public class PainelPlataformas {

    private final Stage stage;
    private final PlataformaRepository repository = new PlataformaRepository();

    public PainelPlataformas(Stage stage) {
        this.stage = stage;
    }

    public VBox criarPainelPlataformas() {
        VBox painelPlataformas = new VBox();
        painelPlataformas.setSpacing(10);
        painelPlataformas.setPadding(new Insets(5, 20, 20, 20));
        painelPlataformas.setStyle("-fx-background-color: #0d253e");

        Label lblTitulo = new Label("Listagem de Plataformas");
        lblTitulo.setStyle("-fx-font-size: 24; -fx-text-fill: #ffffff; -fx-font-weight: bold");

        Separator linha = new Separator();

        TableView<Plataforma> tabelaPlataformas = new TableView<>();
        VBox.setVgrow(tabelaPlataformas, Priority.ALWAYS);

        TableColumn<Plataforma, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Plataforma, String> colunaNome = new TableColumn<>("NOME");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(200);

        TableColumn<Plataforma, String> colunaFabricante = new TableColumn<>("FABRICANTE");
        colunaFabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        colunaFabricante.setPrefWidth(150);

        TableColumn<Plataforma, String> colunaAno = new TableColumn<>("ANO LANCAMENTO");
        colunaAno.setCellValueFactory(cellData -> {
            if (cellData.getValue().getAnoLancamento() == null) {
                return new SimpleStringProperty("");
            }
            String data = cellData.getValue().getAnoLancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return new SimpleStringProperty(data);
        });
        colunaAno.setPrefWidth(130);

        TableColumn<Plataforma, Integer> colunaGeracao = new TableColumn<>("GERACAO");
        colunaGeracao.setCellValueFactory(new PropertyValueFactory<>("geracao"));
        colunaGeracao.setPrefWidth(80);

        TableColumn<Plataforma, String> colunaAtiva = new TableColumn<>("ATIVA");
        colunaAtiva.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isAtivo() ? "Sim" : "Nao")
        );
        colunaAtiva.setPrefWidth(70);

        tabelaPlataformas.getColumns().addAll(
                colunaId, colunaNome, colunaFabricante, colunaAno, colunaGeracao, colunaAtiva
        );

        tabelaPlataformas.setItems(repository.getPlataformas());

        HBox painelBotoes = new HBox(30);
        painelBotoes.setPadding(new Insets(20, 0, 0, 0));
        painelBotoes.setAlignment(Pos.BASELINE_RIGHT);

        Button btnAdicionar = criarBotao("Adicionar", "/imagens/save-game.png");
        btnAdicionar.setOnAction(e -> {
            TelaPlataforma telaPlataforma = new TelaPlataforma();
            telaPlataforma.criarTela(stage);
            tabelaPlataformas.setItems(repository.getPlataformas());
        });

        Button btnEditar = criarBotao("Editar", "/imagens/pencil.png");
        btnEditar.setOnAction(e -> {
            Plataforma plataformaEditar = tabelaPlataformas.getSelectionModel().getSelectedItem();
            if (plataformaEditar != null) {
                TelaPlataforma telaPlataforma = new TelaPlataforma(plataformaEditar);
                telaPlataforma.criarTela(stage);
                tabelaPlataformas.setItems(repository.getPlataformas());
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Edicao de plataforma");
                alerta.setHeaderText("Para editar uma plataforma, selecione-a na lista.");
                alerta.showAndWait();
            }
        });

        Button btnExcluir = criarBotao("Excluir", "/imagens/garbage.png");
        btnExcluir.setOnAction(e -> {
            Plataforma plataformaExcluir = tabelaPlataformas.getSelectionModel().getSelectedItem();

            if (plataformaExcluir == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Exclusao de plataforma");
                alerta.setHeaderText("Para excluir uma plataforma, selecione-a na lista.");
                alerta.showAndWait();
                return;
            }

            Alert confirmaExclusao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmaExclusao.setTitle("Exclusao de plataforma");
            confirmaExclusao.setHeaderText("Voce esta prestes a excluir uma plataforma.");
            confirmaExclusao.setContentText("Tem certeza que deseja continuar?");

            Optional<ButtonType> resposta = confirmaExclusao.showAndWait();
            if (resposta.isPresent() && resposta.get() == ButtonType.OK) {
                repository.excluir(plataformaExcluir.getId());
                tabelaPlataformas.setItems(repository.getPlataformas());
            }
        });

        painelBotoes.getChildren().addAll(btnAdicionar, btnEditar, btnExcluir);
        painelPlataformas.getChildren().addAll(lblTitulo, linha, tabelaPlataformas, painelBotoes);

        return painelPlataformas;
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
