package br.senac.sp.gamesfx.ui.plataformas;

import br.senac.sp.gamesfx.data.repository.PlataformaRepository;
import br.senac.sp.gamesfx.model.Fabricante;
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

public class TelaPlataforma {

    private final TextField tfId = new TextField();
    private final TextField tfNome = new TextField();
    private final ComboBox<Fabricante> cbFabricante = new ComboBox<>();
    private final DatePicker dpAnoLancamento = new DatePicker(LocalDate.now());
    private final TextField tfGeracao = new TextField();
    private final CheckBox cbAtiva = new CheckBox("Ativa");

    private final String tituloTela;

    public TelaPlataforma(Plataforma plataforma) {
        this.tituloTela = "Alterar Plataforma";

        tfId.setText(String.valueOf(plataforma.getId()));
        tfNome.setText(plataforma.getNome());

        PlataformaRepository repo = new PlataformaRepository();
        cbFabricante.setItems(repo.getFabricantes());

        for (Fabricante f : cbFabricante.getItems()) {
            if (f.getId() == plataforma.getFabricanteId()) {
                cbFabricante.getSelectionModel().select(f);
                break;
            }
        }

        dpAnoLancamento.setValue(plataforma.getAnoLancamento());
        tfGeracao.setText(String.valueOf(plataforma.getGeracao()));
        cbAtiva.setSelected(plataforma.isAtivo());
    }

    public TelaPlataforma() {
        this.tituloTela = "Cadastro de Plataforma";
        cbAtiva.setSelected(true);
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

        if (cbFabricante.getItems().isEmpty()) {
            PlataformaRepository repo = new PlataformaRepository();
            cbFabricante.setItems(repo.getFabricantes());
        }

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(25));
        grid.setStyle("-fx-border-width: 1; -fx-border-color: #6d80b6; -fx-border-radius: 8");

        tfId.setEditable(false);
        tfId.setDisable(true);

        tfNome.setPromptText("Ex. PlayStation 5");
        cbFabricante.setPromptText("Selecione...");
        dpAnoLancamento.setPromptText("Ex. 2020");
        tfGeracao.setPromptText("Ex. 9");

        cbFabricante.setMaxWidth(Double.MAX_VALUE);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(tfId, 1, 0);
        grid.add(new Label("Nome:"), 0, 1);
        grid.add(tfNome, 1, 1);
        grid.add(new Label("Fabricante:"), 0, 2);
        grid.add(cbFabricante, 1, 2);
        grid.add(new Label("Ano de Lancamento:"), 0, 3);
        grid.add(dpAnoLancamento, 1, 3);
        grid.add(new Label("Geracao:"), 0, 4);
        grid.add(tfGeracao, 1, 4);
        grid.add(cbAtiva, 1, 5);

        formulario.getChildren().add(grid);
        return formulario;
    }

    private HBox criarPainelBotoes(Stage stage) {
        HBox painelBotoes = new HBox(15);
        painelBotoes.setPadding(new Insets(15));
        painelBotoes.setAlignment(Pos.CENTER_RIGHT);
        painelBotoes.setStyle("-fx-background-color: #6d80b6");

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setTooltip(new Tooltip("Salvar dados da plataforma"));

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

            if (tfNome.getText().isBlank() || cbFabricante.getSelectionModel().isEmpty()
                    || dpAnoLancamento.getValue() == null || tfGeracao.getText().isBlank()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Campos obrigatorios");
                alerta.setHeaderText("Preencha todos os campos antes de salvar.");
                alerta.showAndWait();
                return;
            }

            Plataforma plataforma = new Plataforma();
            plataforma.setNome(tfNome.getText());

            Fabricante fabSelecionada = cbFabricante.getSelectionModel().getSelectedItem();
            plataforma.setFabricanteId(fabSelecionada.getId());
            plataforma.setFabricante(fabSelecionada.getNome());

            plataforma.setAnoLancamento(dpAnoLancamento.getValue());
            plataforma.setAtivo(cbAtiva.isSelected());

            try {
                plataforma.setGeracao(Integer.parseInt(tfGeracao.getText()));
            } catch (NumberFormatException e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Valor incorreto");
                alerta.setHeaderText("A geracao deve conter apenas numeros.");
                alerta.showAndWait();
                tfGeracao.requestFocus();
                return;
            }

            PlataformaRepository repository = new PlataformaRepository();

            if (tfId.getText().isBlank()) {
                boolean sucesso = repository.salvar(plataforma);
                if (!sucesso) {
                    Alert erro = new Alert(Alert.AlertType.ERROR);
                    erro.setTitle("Erro");
                    erro.setHeaderText("Nao foi possivel salvar a plataforma.");
                    erro.showAndWait();
                    return;
                }

                Alert mensagemSalvar = new Alert(Alert.AlertType.CONFIRMATION);
                mensagemSalvar.setTitle("Cadastro de plataforma");
                mensagemSalvar.setHeaderText("A plataforma foi gravada com sucesso.");
                mensagemSalvar.setContentText("Deseja cadastrar outra plataforma?");

                boolean cadastrarOutra = mensagemSalvar.showAndWait()
                        .filter(botao -> botao == ButtonType.OK)
                        .isPresent();

                if (cadastrarOutra) {
                    limparCampos();
                } else {
                    stage.close();
                }

            } else {
                plataforma.setId(Integer.parseInt(tfId.getText()));
                boolean sucesso = repository.editar(plataforma);

                if (!sucesso) {
                    Alert erro = new Alert(Alert.AlertType.ERROR);
                    erro.setTitle("Erro");
                    erro.setHeaderText("Nao foi possivel atualizar a plataforma.");
                    erro.showAndWait();
                    return;
                }

                Alert mensagemEditar = new Alert(Alert.AlertType.INFORMATION);
                mensagemEditar.setTitle("Atualizacao de plataforma");
                mensagemEditar.setHeaderText("A plataforma foi atualizada com sucesso.");
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
        tfNome.clear();
        cbFabricante.getSelectionModel().clearSelection();
        dpAnoLancamento.setValue(LocalDate.now());
        tfGeracao.clear();
        cbAtiva.setSelected(true);
        tfNome.requestFocus();
    }
}
