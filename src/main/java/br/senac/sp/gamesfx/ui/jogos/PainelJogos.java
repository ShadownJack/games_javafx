package br.senac.sp.gamesfx.ui.jogos;

import br.senac.sp.gamesfx.data.repository.JogoRepository;
import br.senac.sp.gamesfx.model.Jogo;
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

import java.time.LocalDate;

public class PainelJogos {

    private Stage stage;

    public  PainelJogos(Stage stage){
        this.stage = stage;
    }


    public VBox criarPainelJogos(){
        VBox painelJogos = new VBox();

        // Aumentei o espaçamento entre os itens (10) para não ficar tudo colado
        painelJogos.setSpacing(10);
        painelJogos.setPadding(new Insets(10, 20, 20, 20));
        painelJogos.setStyle("-fx-background-color: transparent");

        // Título do painel jogos
        Label lblTitulo = new Label("Listagem de Jogos");
        lblTitulo.setStyle("-fx-font-size: 24; -fx-text-fill: #000000; -fx-font-weight: bold");

        // Linha abaixo do label
        Separator linha = new Separator();

        // Tabela com a lista de jogos
        TableView<Jogo> tabelaJogos = new TableView<>();

        // Ajustar tabel para ocupar todo o espaço
        VBox.setVgrow(tabelaJogos, Priority.ALWAYS);

        // Criar colunas da tabela
        TableColumn<Jogo, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);

        TableColumn<Jogo, String> colunaTitulo = new TableColumn<>("TÍTULO");
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaTitulo.setPrefWidth(235);

        TableColumn<Jogo, String> colunaPlataforma = new TableColumn<>("PLATAFORMA");
        colunaPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        colunaPlataforma.setPrefWidth(100);

        TableColumn<Jogo, String> colunaCategoria = new TableColumn<>("CATEGORIA");
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colunaCategoria.setPrefWidth(100);

        TableColumn<Jogo, String> colunaEstudio = new TableColumn<>("ESTÚDIO");
        colunaEstudio.setCellValueFactory(new PropertyValueFactory<>("estudio"));
        colunaEstudio.setPrefWidth(100);

        TableColumn<Jogo, Double> colunaPreco = new TableColumn<>("PREÇO");
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaPreco.setPrefWidth(70);

        TableColumn<Jogo, LocalDate> colunaDataLancamento = new TableColumn<>("LANÇAMENTO");
        colunaDataLancamento.setCellValueFactory(new PropertyValueFactory<>("dataLancamento"));
        colunaDataLancamento.setPrefWidth(100);

        TableColumn<Jogo, Boolean> colunaFinalizado = new TableColumn<>("FINALIZADO");
        colunaFinalizado.setCellValueFactory(new PropertyValueFactory<>("finalizadoFormatado"));
        colunaFinalizado.setPrefWidth(80);

        // Obter os dados que serão exibidos
        JogoRepository repository = new JogoRepository();

        // Adicionar as colunas na tabela ANTES de setar os itens (boa prática)
        tabelaJogos.getColumns().addAll(colunaId, colunaTitulo, colunaPlataforma, colunaCategoria, colunaEstudio, colunaPreco, colunaDataLancamento, colunaFinalizado);

        // Adicionando a lista de jogos na tabela
        tabelaJogos.setItems(repository.getJogos());

        // Criar botões de ações
        HBox painelBotoes = new HBox(10);
        painelBotoes.setPadding(new Insets(0, 0, 0, 0));

        Button botaoAdicionar = criarBotao("Adicionar", "/imagens/game.png");
        botaoAdicionar.setOnAction(event -> {
            TelaJogo telaJogo = new TelaJogo();
            telaJogo.criarTela(stage);
        });

        Button botaoVisualizar = criarBotao("Visualizar", "/imagens/visual.png");

        Button botaoEditar = criarBotao("Editar", "/imagens/pencil.png");

        Button botaoDeletar = criarBotao("Deletar", "/imagens/garbage.png");

        painelBotoes.setAlignment(Pos.BOTTOM_RIGHT);

        painelBotoes.getChildren().addAll(botaoAdicionar, botaoVisualizar, botaoEditar, botaoDeletar);

        // Adicionar os componentes no painel
        painelJogos.getChildren().addAll(lblTitulo, linha, tabelaJogos, painelBotoes);

        return painelJogos;
    }
    private Button criarBotao (String textoBotao, String urlImagem){
        Image image = new Image(getClass().getResourceAsStream(urlImagem));
        ImageView imageView = new ImageView(image);


        Button button = new Button();
        button.setPrefWidth(110);
        button.setPrefHeight(50);
        button.setText(textoBotao);
        button.setGraphic(imageView);
        button.setContentDisplay(ContentDisplay.BOTTOM);

        return button;
    }

}