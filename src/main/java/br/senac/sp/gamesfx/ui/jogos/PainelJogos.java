package br.senac.sp.gamesfx.ui.jogos;

import br.senac.sp.gamesfx.data.repository.JogoRepository;
import br.senac.sp.gamesfx.model.Jogo;
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

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PainelJogos {

    private Stage stage;
    private JogoRepository repository = new JogoRepository();

    public PainelJogos(Stage stage) {
        this.stage = stage;
    }

    public VBox criarPainelJogos() {
        VBox painelJogos = new VBox();
        painelJogos.setSpacing(10);
        painelJogos.setPadding(new Insets(5, 20, 20, 20));
        painelJogos.setStyle("-fx-background-color: #0d253e");

        // Título do painel jogos
        Label lblTitulo = new Label("Listagem de Jogos");
        lblTitulo.setStyle("-fx-font-size: 24; -fx-text-fill: #ffffff; -fx-font-weight: bold");

        // Linha abaixo do label
        Separator linha = new Separator();

        // Tabela com a lista de jogos
        TableView<Jogo> tabelaJogos = new TableView<>();
        VBox.setVgrow(tabelaJogos, Priority.ALWAYS);

        // Criar colunas da tabela
        TableColumn<Jogo, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Jogo, String> colunaTitulo = new TableColumn<>("TÍTULO");
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaTitulo.setPrefWidth(180);

        TableColumn<Jogo, String> colunaPlataforma = new TableColumn<>("PLATAFORMA");
        colunaPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        colunaPlataforma.setPrefWidth(120);

        TableColumn<Jogo, String> colunaEstudio = new TableColumn<>("ESTÚDIO");
        colunaEstudio.setCellValueFactory(new PropertyValueFactory<>("estudio"));
        colunaEstudio.setPrefWidth(100);

        TableColumn<Jogo, String> colunaCategoria = new TableColumn<>("CATEGORIA");
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colunaCategoria.setPrefWidth(90);

        TableColumn<Jogo, Double> colunaPreco = new TableColumn<>("PREÇO");
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaPreco.setPrefWidth(80);

        TableColumn<Jogo, String> colunaDataLancamento = new TableColumn<>("LANÇAMENTO");
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

        // Adicionar as colunas na tabela
        tabelaJogos.getColumns().addAll(
                colunaId, colunaTitulo, colunaPlataforma, colunaEstudio,
                colunaCategoria, colunaPreco, colunaDataLancamento, colunaFinalizado
        );

        // Adiciona a lista de jogos na tabela
        tabelaJogos.setItems(repository.getJogos());

        // Criar o painel de botões de ação
        HBox painelBotoes = new HBox(30);
        painelBotoes.setPadding(new Insets(20, 0, 0, 0));
        painelBotoes.setAlignment(Pos.BASELINE_RIGHT);

        // Criar os botões
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
                alerta.setTitle("Edição de jogo");
                alerta.setHeaderText("Para editar um jogo, você deve selecioná-lo na lista.");
                alerta.showAndWait();
            }
        });

        Button btnExibir = criarBotao("Exibir", "/imagens/visual.png");

        Button btnExcluir = criarBotao("Excluir", "/imagens/garbage.png");
        btnExcluir.setOnAction(e -> {
            Jogo jogoExcluir = tabelaJogos.getSelectionModel().getSelectedItem();

            if (jogoExcluir == null) {
                Alert alertaJogoNaoSelecionado = new Alert(Alert.AlertType.WARNING);
                alertaJogoNaoSelecionado.setTitle("Exclusão de jogo");
                alertaJogoNaoSelecionado.setHeaderText("Para excluir um jogo, você deve selecioná-lo na lista.");
                alertaJogoNaoSelecionado.showAndWait();
                return;
            }

            Alert confirmaExclusao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmaExclusao.setTitle("Exclusão de jogo");
            confirmaExclusao.setHeaderText("Você está prestes a excluir um jogo.");
            confirmaExclusao.setContentText("Tem certeza que deseja continuar?");

            Optional<ButtonType> resposta = confirmaExclusao.showAndWait();
            if (resposta.isPresent() && resposta.get() == ButtonType.OK) {
                repository.excluir(jogoExcluir.getId());
                tabelaJogos.setItems(repository.getJogos());
            }
        });

        painelBotoes.getChildren().addAll(btnAdicionar, btnEditar, btnExibir, btnExcluir);

        // Adicionar os componentes no painel
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