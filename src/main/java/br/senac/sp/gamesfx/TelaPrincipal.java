package br.senac.sp.gamesfx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TelaPrincipal extends Application {
    private static final String COR_PADRAO = "-fx-background-color:#0e1c6a; -fx-text-fill: ffffff; -fx-alignment: center; -fx-cursor: hand ";
    private static final String COR_HOVER = "-fx-background-color:  #1A01FFFF ; -fx-text-fill: ffffff; -fx-alignment: center; -fx-cursor: hand";

    @Override
    public void start(Stage stage) throws Exception {
        Image imagem = new Image(getClass().getResourceAsStream("/imagens/game-console.png"));
        BorderPane raiz = new BorderPane();
        VBox painelLateral = new VBox();
        painelLateral.setSpacing(10);
        painelLateral.setPrefWidth(200);
        painelLateral.setStyle("-fx-background-color: #6d80b6");
        raiz.setStyle("-fx-background-color: #8aa1ca");
        painelLateral.setPadding(new Insets(10));
        Button btnJogos = criarBotaoMenu("Jogos");
        Button btnPlataformas = criarBotaoMenu("Plataforma");
        Button btnEstudio = criarBotaoMenu("Estudios");
        Button  btnHome = criarBotaoMenu("Home");

        aplicarEfeitoHover(btnHome, btnJogos, btnPlataformas, btnEstudio);
        painelLateral.getChildren().addAll(
                btnHome,
                btnJogos,
                btnPlataformas,
                btnEstudio
        );

        raiz.setLeft(painelLateral);

        Scene cena = new Scene(raiz, 900, 700);
        stage.setScene(cena);
//        stage.setResizable(false);
        stage.setTitle("Sistema de Gestão de Jogos V1.0");
        stage.setMaximized(true);
        stage.getIcons().add(imagem);
        stage.show();
    }

    private Button criarBotaoMenu (String textoDoBotao){
        Button button = new Button(textoDoBotao);
        button.setPadding(new Insets(10));
        button.setPrefWidth(Double.MAX_VALUE);
        return button;
    }

    private  void aplicarEfeitoHover (Button... botoes){
        for(Button button : botoes){
            button.setStyle(COR_PADRAO);

            button.setOnMouseEntered(event -> {

                button.setStyle(COR_HOVER);

            });

            button.setOnMouseExited(event -> {
                button.setStyle(COR_PADRAO);
            });
        }
    }


}

