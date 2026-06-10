package br.senac.sp.gamesfx.ui.estudios;

import br.senac.sp.gamesfx.data.repository.EstudioRepository;
import br.senac.sp.gamesfx.model.Estudio;
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

public class TelaEstudio {

    private TextField tfId = new TextField();
    private TextField tfNome = new TextField();
    private TextField tfPaisOrigem = new TextField();
    private DatePicker tfAnoFundacao = new DatePicker(LocalDate.now());
    private TextField tfSite = new TextField();
    private CheckBox cbAtivo = new CheckBox("Ativo");

    private String tituloTela;

    // Construtor para EDIÇÃO
    public TelaEstudio(Estudio estudio) {
        this.tituloTela = "Alterar Estúdio";

        tfId.setText(String.valueOf(estudio.getId()));
        tfNome.setText(estudio.getTitulo());
        tfPaisOrigem.setText(estudio.getPaisOrigem());
        tfAnoFundacao.setValue(estudio.getAnoFundacao());
        tfSite.setText(estudio.getSite());
        cbAtivo.setSelected(estudio.isAtivo());
    }

    // Construtor para NOVO CADASTRO
    public TelaEstudio() {
        this.tituloTela = "Cadastro de Estúdio";
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

        Scene cena = new Scene(raiz, 550, 500);
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

        tfNome.setPromptText("Ex. Konami");
        tfPaisOrigem.setPromptText("Ex. Japão");
        tfAnoFundacao.setPromptText("Ex. 1969");
        tfSite.setPromptText("Ex. www.konami.com");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(tfId, 1, 0);
        grid.add(new Label("Nome:"), 0, 1);
        grid.add(tfNome, 1, 1);
        grid.add(new Label("País de Origem:"), 0, 2);
        grid.add(tfPaisOrigem, 1, 2);
        grid.add(new Label("Ano de Fundação:"), 0, 3);
        grid.add(tfAnoFundacao, 1, 3);
        grid.add(new Label("Site:"), 0, 4);
        grid.add(tfSite, 1, 4);
        grid.add(cbAtivo, 1, 5);

        formulario.getChildren().add(grid);
        return formulario;
    }

    private HBox criarPainelBotoes(Stage stage) {
        HBox painelBotoes = new HBox(15);
        painelBotoes.setPadding(new Insets(15));
        painelBotoes.setAlignment(Pos.CENTER_RIGHT);
        painelBotoes.setStyle("-fx-background-color: #6d80b6");

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setTooltip(new Tooltip("Salvar dados do estúdio"));

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
            // 1. Validação de campos obrigatórios
            if (tfNome.getText().isEmpty() || tfPaisOrigem.getText().isEmpty()
                    || tfAnoFundacao.getValue() == null){
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Campos obrigatórios");
                alerta.setHeaderText("Nome, País de Origem e Ano de Fundação são obrigatórios.");
                alerta.showAndWait();
                return;
            }

            // 2. Criação do objeto e população dos dados
            Estudio estudio = new Estudio();
            estudio.setTitulo(tfNome.getText());
            estudio.setPaisOrigem(tfPaisOrigem.getText());

            estudio.setAnoFundacao(tfAnoFundacao.getValue());

            estudio.setSite(tfSite.getText());
            estudio.setAtivo(cbAtivo.isSelected());

            EstudioRepository repository = new EstudioRepository();

            if (tfId.getText() == null || tfId.getText().equals("")) {
                // NOVO CADASTRO
                repository.salvar(estudio);

                Alert mensagemSalvar = new Alert(Alert.AlertType.CONFIRMATION);
                mensagemSalvar.setTitle("Cadastro de estúdio");
                mensagemSalvar.setHeaderText("O estúdio foi gravado com sucesso!");
                mensagemSalvar.setContentText("Deseja cadastrar outro estúdio?");

                Optional<ButtonType> escolha = mensagemSalvar.showAndWait();
                if (escolha.get() == ButtonType.OK) {
                    limparCampos();
                } else {
                    stage.close();
                }

            } else {
                // EDIÇÃO
                estudio.setId(Integer.parseInt(tfId.getText()));

                repository.editar(estudio);

                Alert mensagemEditar = new Alert(Alert.AlertType.INFORMATION);
                mensagemEditar.setTitle("Atualização de estúdio");
                mensagemEditar.setHeaderText("O estúdio foi atualizado com sucesso!");
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
        tfNome.clear();
        tfPaisOrigem.clear();
        tfAnoFundacao.setValue(LocalDate.now());
        tfSite.clear();
        cbAtivo.setSelected(true);
        tfNome.requestFocus();
    }
}