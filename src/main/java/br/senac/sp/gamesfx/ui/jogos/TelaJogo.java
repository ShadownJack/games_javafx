package br.senac.sp.gamesfx.ui.jogos;

import br.senac.sp.gamesfx.data.repository.EstudioRepository;
import br.senac.sp.gamesfx.data.repository.JogoRepository;
import br.senac.sp.gamesfx.data.repository.PlataformaRepository;
import br.senac.sp.gamesfx.model.Estudio;
import br.senac.sp.gamesfx.model.Jogo;
import br.senac.sp.gamesfx.model.Plataforma;
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
import java.util.Optional;

public class TelaJogo {

    private TextField tfId = new TextField();
    private TextField tfTitulo = new TextField();
    private TextField tfValor = new TextField();
    private ComboBox<String> comboPlataforma = new ComboBox<>();
    private ComboBox<String> comboEstudio = new ComboBox<>();
    private DatePicker dpDataLancamento = new DatePicker(LocalDate.now());
    private CheckBox cbFinalizado = new CheckBox("Finalizado");

    private String tituloTela;

    // Construtor para EDIÇÃO
    public TelaJogo(Jogo jogo) {
        this.tituloTela = "Alterar Jogo";

        tfId.setText(String.valueOf(jogo.getId()));
        tfTitulo.setText(jogo.getTitulo());
        tfValor.setText(String.valueOf(jogo.getPreco()));
        comboPlataforma.setValue(jogo.getPlataforma());
        comboEstudio.setValue(jogo.getEstudio());
        dpDataLancamento.setValue(jogo.getDataLancamento());
        cbFinalizado.setSelected(jogo.isFinalizado());
    }

    // Construtor para NOVO CADASTRO
    public TelaJogo() {
        this.tituloTela = "Cadastro de Jogo";
    }

    public void criarTela(Stage stagePai) {
        Stage stage = new Stage();
        stage.initOwner(stagePai);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(this.tituloTela);

        BorderPane raiz = new BorderPane();

        raiz.setTop(criarPainelTitulo());
        raiz.setCenter(criarFormulario());
        raiz.setBottom(criarPainelBotoes(stage));

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

        try {
            Image image = new Image(getClass().getResourceAsStream("/imagens/game-save.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(32);
            imageView.setFitWidth(32);
            painelTitulo.getChildren().add(imageView);
        } catch (Exception e) {
            System.err.println("Aviso: Imagem do título não encontrada.");
        }

        Label lblTitulo = new Label(this.tituloTela);
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

        tfId.setEditable(false);
        tfId.setDisable(true);

        tfTitulo.setPromptText("Ex. Super Mario World");
        tfValor.setPromptText("Ex. 199.90");

        // 1. Instancia os repositórios para buscar os dados reais do banco
        PlataformaRepository plataformaRepo = new PlataformaRepository();
        EstudioRepository estudioRepo = new EstudioRepository();

        // 2. Criamos listas de Strings vazias para os ComboBoxes
        ObservableList<String> plataformasTexto = FXCollections.observableArrayList();
        ObservableList<String> estudiosTexto = FXCollections.observableArrayList();

        // 3. Varre a lista de objetos do banco e extrai APENAS os nomes (Strings)
        for (Plataforma p : plataformaRepo.getPlataformas()) {
            plataformasTexto.add(p.getTitulo()); // Adiciona o nome da plataforma na lista
        }

        for (Estudio e : estudioRepo.getEstudios()) {
            estudiosTexto.add(e.getTitulo()); // Adiciona o nome do estúdio na lista
        }

        // 4. Alimenta os ComboBoxes comuns de String com os dados dinâmicos do banco
        comboPlataforma.setItems(plataformasTexto);
        comboEstudio.setItems(estudiosTexto);

        comboPlataforma.setPromptText("Selecione...");
        comboEstudio.setPromptText("Selecione...");

        // Montagem do Grid igual ao seu original
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

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setTooltip(new Tooltip("Salvar dados do jogo"));

        try {
            Image imgSalvar = new Image(getClass().getResourceAsStream("/imagens/save.png"));
            ImageView ivSalvar = new ImageView(imgSalvar);
            ivSalvar.setFitHeight(20);
            ivSalvar.setFitWidth(20);
            btnSalvar.setGraphic(ivSalvar);
        } catch (Exception e) {
            System.err.println("Imagem save.png não encontrada.");
        }

        btnSalvar.setOnAction(evento -> {
            Jogo jogo = new Jogo();
            jogo.setTitulo(tfTitulo.getText());
            jogo.setPlataforma(comboPlataforma.getValue());
            jogo.setEstudio(comboEstudio.getValue());
            jogo.setDataLancamento(dpDataLancamento.getValue());
            jogo.setCategoria("Jogo");
            jogo.setFinalizado(cbFinalizado.isSelected());

            try {
                String valorTexto = tfValor.getText().replace(",", ".");
                jogo.setPreco(Double.parseDouble(valorTexto));
            } catch (NumberFormatException erro) {
                Alert valorIncorreto = new Alert(Alert.AlertType.ERROR);
                valorIncorreto.setTitle("Valor incorreto");
                valorIncorreto.setHeaderText("O valor digitado deve conter apenas números!\nUtilize ponto ou vírgula como separador de decimal.");
                valorIncorreto.showAndWait();
                tfValor.requestFocus();
                return;
            }

            JogoRepository repository = new JogoRepository();

            if (tfId.getText() == null || tfId.getText().equals("")) {
                // NOVO CADASTRO
                repository.salvar(jogo);

                Alert mensagemSalvar = new Alert(Alert.AlertType.CONFIRMATION);
                mensagemSalvar.setTitle("Cadastro de jogo");
                mensagemSalvar.setHeaderText("O jogo foi gravado com sucesso!");
                mensagemSalvar.setContentText("Deseja cadastrar outro jogo?");

                Optional<ButtonType> escolha = mensagemSalvar.showAndWait();

                if (escolha.get() == ButtonType.OK) {
                    limparCampos();
                } else {
                    stage.close();
                }

            } else {
                // EDIÇÃO
                jogo.setId(Integer.parseInt(tfId.getText()));
                repository.editar(jogo);

                Alert mensagemEditar = new Alert(Alert.AlertType.INFORMATION);
                mensagemEditar.setTitle("Atualização de jogo");
                mensagemEditar.setHeaderText("O jogo foi atualizado com sucesso!");
                mensagemEditar.showAndWait();

                stage.close();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        try {
            Image imgCancelar = new Image(getClass().getResourceAsStream("/imagens/cross-button.png"));
            ImageView ivCancelar = new ImageView(imgCancelar);
            ivCancelar.setFitHeight(20);
            ivCancelar.setFitWidth(20);
            btnCancelar.setGraphic(ivCancelar);
        } catch (Exception e) {
            System.err.println("Imagem cross-button.png não encontrada.");
        }

        btnCancelar.setOnAction(evento -> stage.close());

        painelBotoes.getChildren().addAll(btnSalvar, btnCancelar);
        return painelBotoes;
    }

    private void limparCampos() {
        tfId.clear();
        tfTitulo.clear();
        tfValor.clear();
        comboPlataforma.setValue(null);
        comboEstudio.setValue(null);
        dpDataLancamento.setValue(LocalDate.now());
        cbFinalizado.setSelected(false);
        tfTitulo.requestFocus();
    }
}
