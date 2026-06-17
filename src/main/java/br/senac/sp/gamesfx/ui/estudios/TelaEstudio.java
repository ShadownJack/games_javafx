package br.senac.sp.gamesfx.ui.estudios;

import br.senac.sp.gamesfx.data.repository.EstudioRepository;
import br.senac.sp.gamesfx.model.Estudio;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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

public class TelaEstudio {

    private final TextField tfId = new TextField();
    private final TextField tfNome = new TextField();
    private final TextField tfPaisOrigem = new TextField();
    private final DatePicker tfAnoFundacao = new DatePicker(LocalDate.now());
    private final TextField tfSite = new TextField();
    private final CheckBox cbAtivo = new CheckBox("Ativo");

    private final String tituloTela;

    public TelaEstudio(Estudio estudio) {
        this.tituloTela = "Alterar Estudio";

        tfId.setText(String.valueOf(estudio.getId()));
        tfNome.setText(estudio.getNome());
        tfPaisOrigem.setText(estudio.getPaisOrigem());
        tfAnoFundacao.setValue(estudio.getAnoFundacao());
        tfSite.setText(estudio.getSite());
        cbAtivo.setSelected(estudio.isAtivo());
    }

    public TelaEstudio() {
        this.tituloTela = "Cadastro de Estudio";
        cbAtivo.setSelected(true);
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

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(25));
        grid.setStyle("-fx-border-width: 1; -fx-border-color: #6d80b6; -fx-border-radius: 8");

        tfId.setEditable(false);
        tfId.setDisable(true);

        tfNome.setPromptText("Ex. Konami");
        tfPaisOrigem.setPromptText("Ex. Japao");
        tfAnoFundacao.setPromptText("Ex. 1969");
        tfSite.setPromptText("Ex. www.konami.com");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(tfId, 1, 0);
        grid.add(new Label("Nome:"), 0, 1);
        grid.add(tfNome, 1, 1);
        grid.add(new Label("Pais de Origem:"), 0, 2);
        grid.add(tfPaisOrigem, 1, 2);
        grid.add(new Label("Ano de Fundacao:"), 0, 3);
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
        btnSalvar.setTooltip(new Tooltip("Salvar dados do estudio"));

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
            if (tfNome.getText().isBlank() || tfPaisOrigem.getText().isBlank() || tfAnoFundacao.getValue() == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Campos obrigatorios");
                alerta.setHeaderText("Nome, pais de origem e ano de fundacao sao obrigatorios.");
                alerta.showAndWait();
                return;
            }

            Estudio estudio = new Estudio();
            estudio.setNome(tfNome.getText());
            estudio.setPaisOrigem(tfPaisOrigem.getText());
            estudio.setAnoFundacao(tfAnoFundacao.getValue());
            estudio.setSite(tfSite.getText());
            estudio.setAtivo(cbAtivo.isSelected());

            EstudioRepository repository = new EstudioRepository();

            if (tfId.getText().isBlank()) {
                boolean sucesso = repository.salvar(estudio);
                if (!sucesso) {
                    Alert erro = new Alert(Alert.AlertType.ERROR);
                    erro.setTitle("Erro");
                    erro.setHeaderText("Nao foi possivel salvar o estudio.");
                    erro.showAndWait();
                    return;
                }

                Alert mensagemSalvar = new Alert(Alert.AlertType.CONFIRMATION);
                mensagemSalvar.setTitle("Cadastro de estudio");
                mensagemSalvar.setHeaderText("O estudio foi gravado com sucesso.");
                mensagemSalvar.setContentText("Deseja cadastrar outro estudio?");

                boolean cadastrarOutro = mensagemSalvar.showAndWait()
                        .filter(botao -> botao == ButtonType.OK)
                        .isPresent();

                if (cadastrarOutro) {
                    limparCampos();
                } else {
                    stage.close();
                }

            } else {
                estudio.setId(Integer.parseInt(tfId.getText()));
                boolean sucesso = repository.editar(estudio);

                if (!sucesso) {
                    Alert erro = new Alert(Alert.AlertType.ERROR);
                    erro.setTitle("Erro");
                    erro.setHeaderText("Nao foi possivel atualizar o estudio.");
                    erro.showAndWait();
                    return;
                }

                Alert mensagemEditar = new Alert(Alert.AlertType.INFORMATION);
                mensagemEditar.setTitle("Atualizacao de estudio");
                mensagemEditar.setHeaderText("O estudio foi atualizado com sucesso.");
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
        tfPaisOrigem.clear();
        tfAnoFundacao.setValue(LocalDate.now());
        tfSite.clear();
        cbAtivo.setSelected(true);
        tfNome.requestFocus();
    }
}
