package br.senac.sp.gamesfx.ui.estudios;

import br.senac.sp.gamesfx.data.repository.EstudioRepository;
import br.senac.sp.gamesfx.model.Estudio;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class PainelEstudios {

    private Stage stage;
    private EstudioRepository repository = new EstudioRepository();

    public PainelEstudios(Stage stage) {
        this.stage = stage;
    }

    public VBox criarPainelEstudios() {
        VBox painelEstudios = new VBox();
        painelEstudios.setSpacing(10);
        painelEstudios.setPadding(new Insets(5, 20, 20, 20));
        painelEstudios.setStyle("-fx-background-color: #0d253e");

        Label lblTitulo = new Label("Listagem de Estúdios");
        lblTitulo.setStyle("-fx-font-size: 24; -fx-text-fill: #ffffff; -fx-font-weight: bold");

        Separator linha = new Separator();

        TableView<Estudio> tabelaEstudios = new TableView<>();
        VBox.setVgrow(tabelaEstudios, Priority.ALWAYS);

        TableColumn<Estudio, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Estudio, String> colunaNome = new TableColumn<>("NOME");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(200);

        TableColumn<Estudio, String> colunaPais = new TableColumn<>("PAÍS DE ORIGEM");
        colunaPais.setCellValueFactory(new PropertyValueFactory<>("paisOrigem"));
        colunaPais.setPrefWidth(150);

        TableColumn<Estudio, Integer> colunaAno = new TableColumn<>("ANO FUNDAÇÃO");
        colunaAno.setCellValueFactory(new PropertyValueFactory<>("anoFundacao"));
        colunaAno.setPrefWidth(120);

        TableColumn<Estudio, String> colunaSite = new TableColumn<>("SITE");
        colunaSite.setCellValueFactory(new PropertyValueFactory<>("site"));
        colunaSite.setPrefWidth(180);

        TableColumn<Estudio, String> colunaAtivo = new TableColumn<>("ATIVO");
        colunaAtivo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isAtivo() ? "Sim" : "Não")
        );
        colunaAtivo.setPrefWidth(70);

        tabelaEstudios.getColumns().addAll(
                colunaId, colunaNome, colunaPais, colunaAno, colunaSite, colunaAtivo
        );

        tabelaEstudios.setItems(repository.getEstudios());

        HBox painelBotoes = new HBox(30);
        painelBotoes.setPadding(new Insets(20, 0, 0, 0));
        painelBotoes.setAlignment(Pos.BASELINE_RIGHT);

        Button btnAdicionar = criarBotao("Adicionar", "/imagens/save-game.png");
        btnAdicionar.setOnAction(e -> {
            TelaEstudio telaEstudio = new TelaEstudio();
            telaEstudio.criarTela(stage);
            tabelaEstudios.setItems(repository.getEstudios());
        });

        Button btnEditar = criarBotao("Editar", "/imagens/pencil.png");
        btnEditar.setOnAction(e -> {
            Estudio estudioEditar = tabelaEstudios.getSelectionModel().getSelectedItem();
            if (estudioEditar != null) {
                TelaEstudio telaEstudio = new TelaEstudio(estudioEditar);
                telaEstudio.criarTela(stage);
                tabelaEstudios.setItems(repository.getEstudios());
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Edição de estúdio");
                alerta.setHeaderText("Para editar um estúdio, você deve selecioná-lo na lista.");
                alerta.showAndWait();
            }
        });

        Button btnExibir = criarBotao("Exibir", "/imagens/visual.png");

        Button btnExcluir = criarBotao("Excluir", "/imagens/garbage.png");
        btnExcluir.setOnAction(e -> {
            Estudio estudioExcluir = tabelaEstudios.getSelectionModel().getSelectedItem();

            if (estudioExcluir == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Exclusão de estúdio");
                alerta.setHeaderText("Para excluir um estúdio, você deve selecioná-lo na lista.");
                alerta.showAndWait();
                return;
            }

            Alert confirmaExclusao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmaExclusao.setTitle("Exclusão de estúdio");
            confirmaExclusao.setHeaderText("Você está prestes a excluir um estúdio.");
            confirmaExclusao.setContentText("Tem certeza que deseja continuar?");

            Optional<ButtonType> resposta = confirmaExclusao.showAndWait();
            if (resposta.isPresent() && resposta.get() == ButtonType.OK) {
                repository.excluir(estudioExcluir.getId());
                tabelaEstudios.setItems(repository.getEstudios());
            }
        });

        painelBotoes.getChildren().addAll(btnAdicionar, btnEditar, btnExibir, btnExcluir);
        painelEstudios.getChildren().addAll(lblTitulo, linha, tabelaEstudios, painelBotoes);

        return painelEstudios;
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