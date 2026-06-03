package br.senac.sp.gamesfx;

import br.senac.sp.gamesfx.ui.estudios.PainelEstudios;
import br.senac.sp.gamesfx.ui.home.PainelHome;
import br.senac.sp.gamesfx.ui.jogos.PainelJogos;
import br.senac.sp.gamesfx.ui.plataformas.PainelPlataformas;
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
        painelLateral.setStyle("-fx-background-color: #6d80b6; -fx-border-color: #000000; -fx-border-width: 3");
        raiz.setStyle("-fx-background-color: #8aa1ca");
        painelLateral.setPadding(new Insets(10));

        // Botão PainelHome
        Button  btnHome = criarBotaoMenu("Home");
        btnHome.setOnAction(click ->{
            PainelHome painelHome = new PainelHome();
            raiz.setCenter(painelHome.criarPainelHome());
        } );

        // Botão PainelJogos
        Button btnJogos = criarBotaoMenu("Jogos");
        btnJogos.setOnAction(click -> {
            PainelJogos painelJogos = new PainelJogos(stage);
            raiz.setCenter(painelJogos.criarPainelJogos());
        } );

        Button btnPlataformas = criarBotaoMenu("Plataforma");
        btnPlataformas.setOnAction(click -> {
            PainelPlataformas painelPlataforma = new PainelPlataformas(stage);
            raiz.setCenter(painelPlataforma.criarPainelPlataformas());
        });

        Button btnEstudio = criarBotaoMenu("Estudios");
        btnEstudio.setOnAction(event -> {
            PainelEstudios painelEstudios = new PainelEstudios(stage);
            raiz.setCenter(painelEstudios.criarPainelEstudios());
        });




        aplicarEfeitoHover(btnHome, btnJogos, btnPlataformas, btnEstudio);
        painelLateral.getChildren().addAll(
                btnHome,
                btnJogos,
                btnPlataformas,
                btnEstudio
        );

        raiz.setLeft(painelLateral);

        PainelHome painelHome = new PainelHome();

        raiz.setCenter(painelHome.criarPainelHome());

        Scene cena = new Scene(raiz, 900, 700);
        stage.setScene(cena);
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

