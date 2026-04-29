package br.senac.sp.gamesfx.ui.home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PainelHome {
    Image imagem = new Image(getClass().getResourceAsStream("/imagens/game-folder.png"));
        public VBox criarPainelHome(){

            // Painel principal. representa a tela toda
            VBox painelPrincipal  = new VBox();
            painelPrincipal.setAlignment(Pos.TOP_CENTER);
            painelPrincipal.setPadding(new Insets(5, 20, 20, 20));


            // Painel de título
            VBox painelTitulo = new VBox();
            Label lblTitulo = new Label("Seja Bem-vindo!");
            lblTitulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold");
            painelTitulo.getChildren().addAll(lblTitulo, new Separator());

            // Imagem da aplicação
            Image imgLogo = new Image(getClass().getResourceAsStream("/imagens/game-folder.png"));

            ImageView ivLogo = new ImageView(imgLogo);

            Label lblNomeApp = new Label("GameSky PRO");
            lblNomeApp.setStyle("-fx-font-weight: bold; -fx-font-size: 32; -fx-text-fill: #ffffff");

            Label lbl = new Label("");
            lblNomeApp.setStyle("-fx-font-weight: bold; -fx-font-size: 32; -fx-text-fill: #ffffff");

            painelPrincipal.getChildren().addAll(
                    painelTitulo,
                    ivLogo,
                    lblNomeApp);


            return  painelPrincipal;
        }

}
