package br.senac.sp.gamesfx.ui.jogos;

import br.senac.sp.gamesfx.data.repository.EstudioRepository;
import br.senac.sp.gamesfx.data.repository.JogoRepository;
import br.senac.sp.gamesfx.data.repository.PlataformaRepository;
import br.senac.sp.gamesfx.model.Estudio;
import br.senac.sp.gamesfx.model.Jogo;
import br.senac.sp.gamesfx.model.Plataforma;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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

    private final TextField tfId = new TextField();
    private final TextField tfTitulo = new TextField();
    private final TextField tfValor = new TextField();
    private final ComboBox<Plataforma> comboPlataforma = new ComboBox<>();
    private final ComboBox<Estudio> comboEstudio = new ComboBox<>();
    private final DatePicker dpDataLancamento = new DatePicker(LocalDate.now());
    private final CheckBox cbFinalizado = new CheckBox("Finalizado");

    private final String tituloTela;

    public TelaJogo(Jogo jogo) {
        this.tituloTela = "Alterar Jogo";

        tfId.setText(String.valueOf(jogo.getId()));
        tfTitulo.setText(jogo.getTitulo());
        tfValor.setText(String.valueOf(jogo.getPreco()));

        PlataformaRepository plataformaRepo = new PlataformaRepository();
        EstudioRepository estudioRepo = new EstudioRepository();
        comboPlataforma.setItems(plataformaRepo.getPlataformas());
        comboEstudio.setItems(estudioRepo.getEstudios());

        for (Plataforma p : comboPlataforma.getItems()) {
            if (p.getId() == jogo.getPlataformaId()) {
                comboPlataforma.getSelectionModel().select(p);
                break;
            }
        }

        for (Estudio e : comboEstudio.getItems()) {
            if (e.getId() == jogo.getEstudioId()) {
                comboEstudio.getSelectionModel().select(e);
                break;
            }
        }

        dpDataLancamento.setValue(jogo.getDataLancamento());
        cbFinalizado.setSelected(jogo.isFinalizado());
    }

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
            System.err.println("Aviso: Imagem do titulo nao encontrada.");
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

        if (comboPlataforma.getItems().isEmpty()) {
            PlataformaRepository plataformaRepo = new PlataformaRepository();
            comboPlataforma.setItems(plataformaRepo.getPlataformas());
        }

        if (comboEstudio.getItems().isEmpty()) {
            EstudioRepository estudioRepo = new EstudioRepository();
            comboEstudio.setItems(estudioRepo.getEstudios());
        }

        comboPlataforma.setPromptText("Selecione...");
        comboEstudio.setPromptText("Selecione...");
        comboPlataforma.setMaxWidth(Double.MAX_VALUE);
        comboEstudio.setMaxWidth(Double.MAX_VALUE);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(tfId, 1, 0);
        grid.add(new Label("Titulo:"), 0, 1);
        grid.add(tfTitulo, 1, 1);
        grid.add(new Label("Plataforma:"), 0, 2);
        grid.add(comboPlataforma, 1, 2);
        grid.add(new Label("Estudio:"), 0, 3);
        grid.add(comboEstudio, 1, 3);
        grid.add(new Label("Valor (R$):"), 0, 4);
        grid.add(tfValor, 1, 4);
        grid.add(new Label("Lancamento:"), 0, 5);
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
            System.err.println("Imagem save.png nao encontrada.");
        }

        btnSalvar.setOnAction(evento -> {
            if (tfTitulo.getText().isBlank() || tfValor.getText().isBlank()
                    || comboPlataforma.getSelectionModel().isEmpty()
                    || comboEstudio.getSelectionModel().isEmpty()
                    || dpDataLancamento.getValue() == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Campos obrigatorios");
                alerta.setHeaderText("Preencha todos os campos obrigatorios antes de salvar.");
                alerta.showAndWait();
                return;
            }

            Jogo jogo = new Jogo();
            if (!tfId.getText().isBlank()) {
                jogo.setId(Integer.parseInt(tfId.getText()));
            }

            jogo.setTitulo(tfTitulo.getText());

            Plataforma plataformaSelecionada = comboPlataforma.getSelectionModel().getSelectedItem();
            Estudio estudioSelecionado = comboEstudio.getSelectionModel().getSelectedItem();

            jogo.setPlataformaId(plataformaSelecionada.getId());
            jogo.setEstudioId(estudioSelecionado.getId());
            jogo.setNomePlataforma(plataformaSelecionada.getNome());
            jogo.setNomeEstudio(estudioSelecionado.getNome());
            jogo.setDataLancamento(dpDataLancamento.getValue());
            jogo.setCategoria("Jogo");
            jogo.setFinalizado(cbFinalizado.isSelected());

            try {
                String valorTexto = tfValor.getText().replace(",", ".");
                jogo.setPreco(Double.parseDouble(valorTexto));
            } catch (NumberFormatException erro) {
                Alert valorIncorreto = new Alert(Alert.AlertType.ERROR);
                valorIncorreto.setTitle("Valor incorreto");
                valorIncorreto.setHeaderText("O valor deve conter apenas numeros.");
                valorIncorreto.showAndWait();
                tfValor.requestFocus();
                return;
            }

            JogoRepository repository = new JogoRepository();

            if (tfId.getText().isBlank()) {
                repository.salvar(jogo);

                Alert mensagemSalvar = new Alert(Alert.AlertType.CONFIRMATION);
                mensagemSalvar.setTitle("Cadastro de jogo");
                mensagemSalvar.setHeaderText("O jogo foi gravado com sucesso.");
                mensagemSalvar.setContentText("Deseja cadastrar outro jogo?");

                boolean cadastrarOutro = mensagemSalvar.showAndWait()
                        .filter(botao -> botao == ButtonType.OK)
                        .isPresent();

                if (cadastrarOutro) {
                    limparCampos();
                } else {
                    stage.close();
                }

            } else {
                repository.editar(jogo);

                Alert mensagemEditar = new Alert(Alert.AlertType.INFORMATION);
                mensagemEditar.setTitle("Atualizacao de jogo");
                mensagemEditar.setHeaderText("O jogo foi atualizado com sucesso.");
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
            System.err.println("Imagem cross-button.png nao encontrada.");
        }

        btnCancelar.setOnAction(evento -> stage.close());

        painelBotoes.getChildren().addAll(btnSalvar, btnCancelar);
        return painelBotoes;
    }

    private void limparCampos() {
        tfId.clear();
        tfTitulo.clear();
        tfValor.clear();
        comboPlataforma.getSelectionModel().clearSelection();
        comboEstudio.getSelectionModel().clearSelection();
        dpDataLancamento.setValue(LocalDate.now());
        cbFinalizado.setSelected(false);
        tfTitulo.requestFocus();
    }
}
