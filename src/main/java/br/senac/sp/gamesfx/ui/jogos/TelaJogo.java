package br.senac.sp.gamesfx.ui.jogos;

import br.senac.sp.gamesfx.model.Jogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;

public class TelaJogo {

    private TextField tfId;
    private TextField tfTitulo;
    private TextField tfValor;
    private ComboBox<String> comboPlataforma;
    private ComboBox<String> comboEstudio;
    private DatePicker dpDataLancamento;
    private CheckBox cbFinalizado;

    public void criarTela(Stage stagePai) {
        Stage stage = new Stage();
        stage.initOwner(stagePai);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Cadastro de Jogo");

        BorderPane raiz = new BorderPane();

        // Organização das seções
        raiz.setTop(criarPainelTitulo());
        raiz.setCenter(criarFormulario());

        // Passamos o 'stage' como argumento para que o painel de botões possa fechá-lo
        raiz.setBottom(criarPainelBotoes(stage));

        // Ajuste no tamanho da cena para comportar o formulário confortavelmente
        Scene cena = new Scene(raiz, 550, 600);
        stage.setScene(cena);
        stage.setResizable(false);
        stage.showAndWait();
    }

    private HBox criarPainelTitulo() {
        HBox painelTitulo = new HBox(20);
        painelTitulo.setPadding(new Insets(20));
        painelTitulo.setStyle("-fx-background-color: #263275");
        painelTitulo.setAlignment(Pos.CENTER_LEFT);

        // Tratamento de erro para imagem: se não existir, o programa continua
        try {
            Image image = new Image(getClass().getResourceAsStream("/imagens/add16.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(32);
            imageView.setFitWidth(32);
            painelTitulo.getChildren().add(imageView);
        } catch (Exception e) {
            System.err.println("Aviso: Ícone do título não encontrado.");
        }

        Label lblTitulo = new Label("Cadastro de Jogos");
        lblTitulo.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 22; -fx-font-weight: bold");
        painelTitulo.getChildren().add(lblTitulo);

        return painelTitulo;
    }

    private VBox criarFormulario() {
        VBox formulario = new VBox();
        formulario.setPadding(new Insets(20));

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(25));
        grid.setStyle("-fx-border-width: 1; -fx-border-color: #6d80b6; -fx-border-radius: 8");

        // Inicialização dos campos
        tfId = new TextField();
        tfId.setEditable(false);
        tfId.setDisable(true); // O ID geralmente é gerado pelo banco

        tfTitulo = new TextField();
        tfTitulo.setPromptText("Ex. Super Mario World");

        tfValor = new TextField();
        tfValor.setPromptText("Ex. 199.90");

        dpDataLancamento = new DatePicker(LocalDate.now());
        cbFinalizado = new CheckBox("Finalizado");

        ObservableList<String> plataformas = FXCollections.observableArrayList(
                "Super Nintendo", "Mega Drive", "PC", "PlayStation 5", "Xbox Series", "Switch"
        );
        comboPlataforma = new ComboBox<>(plataformas);
        comboPlataforma.setPromptText("Selecione...");

        ObservableList<String> estudios = FXCollections.observableArrayList(
                "Konami", "Capcom", "Rare", "Nintendo", "Sega", "Ubisoft"
        );
        comboEstudio = new ComboBox<>(estudios);
        comboEstudio.setPromptText("Selecione...");

        // Adição ao Grid (Coluna, Linha)
        grid.add(new Label("ID:"), 0, 0);
        grid.add(tfId, 1, 0);

        grid.add(new Label("Título:"), 0, 1);
        grid.add(tfTitulo, 1, 1);

        grid.add(new Label("Plataforma:"), 0, 2);
        grid.add(comboPlataforma, 1, 2);

        grid.add(new Label("Estúdio:"), 0, 3);
        grid.add(comboEstudio, 1, 3);

        grid.add(new Label("Valor (R$):"), 0, 4);
        grid.add(tfValor, 1, 4);

        grid.add(new Label("Lançamento:"), 0, 5);
        grid.add(dpDataLancamento, 1, 5);

        grid.add(cbFinalizado, 1, 6);

        formulario.getChildren().add(grid);
        return formulario;
    }

    private HBox criarPainelBotoes(Stage stage) {
        HBox painelBotoes = new HBox(15);
        painelBotoes.setPadding(new Insets(15));
        painelBotoes.setAlignment(Pos.CENTER_RIGHT);
        painelBotoes.setStyle("-fx-background-color: #6d80b6");

        // Botão Salvar
        Button btnSalvar = new Button("Salvar");
        try {
            Image imgSalvar = new Image(getClass().getResourceAsStream("/imagens/save.png"));
            btnSalvar.setGraphic(new ImageView(imgSalvar));
        } catch (Exception e) {
            /* Mantém apenas texto se imagem falhar */
        }

        // --- AÇÃO DE SALVAR ---
        btnSalvar.setOnAction(e -> {
            // Aqui você capturaria os dados para salvar
            System.out.println("Salvando jogo: " + tfTitulo.getText());

            // Exemplo de como você passaria para o seu modelo:
            // Jogo j = new Jogo();
            // j.setTitulo(tfTitulo.getText());
            // j.setPlataforma(comboPlataforma.getValue());

            stage.close(); // Fecha a janela após salvar
        });

        // Botão Cancelar
        Button btnCancelar = new Button("Cancelar");
        try {
            Image imgCancelar = new Image(getClass().getResourceAsStream("/imagens/cross-button.png"));
            btnCancelar.setGraphic(new ImageView(imgCancelar));
        } catch (Exception e) { }

        // --- AÇÃO DE CANCELAR ---
        btnCancelar.setOnAction(e -> stage.close());

        painelBotoes.getChildren().addAll(btnSalvar, btnCancelar);
        return painelBotoes;
    }
}