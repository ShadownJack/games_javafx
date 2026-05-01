package br.senac.sp.gamesfx.ui.home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PainelHome {
    private static final String COR_PADRAO = "-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: black";
    private static final String COR_SECUNDARIA = "-fx-font-size: 14; -fx-text-fill: #4255f1; -fx-underline: true";

    Image imagem = new Image(getClass().getResourceAsStream("/imagens/game-folder.png"));
        public VBox criarPainelHome(){

            // Painel principal. representa a tela toda
            VBox painelPrincipal  = new VBox();
            painelPrincipal.setAlignment(Pos.TOP_CENTER);

            painelPrincipal.setPadding(new Insets(5, 20, 20, 20));


            // Painel de título
            VBox painelTitulo = new VBox();
            Label lblTitulo = new Label("Seja Bem-vindo!");
            lblTitulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #000000");
            painelTitulo.getChildren().addAll(lblTitulo, new Separator());

            VBox painelLogo = new VBox();
            painelLogo.setAlignment(Pos.CENTER);
            VBox.setVgrow(painelLogo, Priority.ALWAYS);

            // Imagem da aplicação
            Image imgLogo = new Image(getClass().getResourceAsStream("/imagens/game-folder.png"));
            ImageView ivLogo = new ImageView(imgLogo);
            ivLogo.setScaleX(1);
            ivLogo.setScaleY(1) ;


            // Textos com o nome e descrição do App
            Label lblNomeApp = new Label("GameSky PRO");
            lblNomeApp.setStyle("-fx-font-weight: bold; -fx-font-size: 32; -fx-text-fill: #ffffff");

            Label lblDescApp = new Label("Software para gestão de Jogos | Versão 1.0(build 37)");
            lblDescApp.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-text-fill: #ffffff");


            //Criar o painel de contatos
            VBox painelContatos = new VBox(5);
            painelContatos.setMaxWidth(600);
            painelContatos.setAlignment(Pos.CENTER);
            painelContatos.setPadding(new Insets(20));
            painelContatos.setStyle("-fx-background-color: #ffff;-fx-border-color: #000000; -fx-border-width: 3; -fx-background-radius: 22; -fx-border-radius: 20");
            VBox.setMargin(painelContatos, new Insets(30, 10, 30, 10));

            Label lblTituloEmail = new Label("E-mail para suporte:");
            Label lblEmail = new Label("gamesky@gameapp.com.br");
            Label lblTituloTelefone = new Label("Telefone para suporte:");
            Label lblTelefone = new Label("(99) 91999-9999 ");
            lblTituloEmail.setStyle(COR_PADRAO);
            lblEmail.setStyle(COR_SECUNDARIA);
            lblTituloTelefone.setStyle(COR_PADRAO);
            lblTelefone.setStyle(COR_SECUNDARIA);

            Label lblTextoFinal = new Label("Desenvolvido por GameSky© - 2026");
            lblTextoFinal.setStyle(COR_PADRAO);


            painelLogo.getChildren().addAll(ivLogo, lblNomeApp, lblDescApp, painelContatos, lblTextoFinal);
            painelContatos.getChildren().addAll(
                    lblTituloEmail,
                    lblEmail,
                    lblTituloTelefone,
                    lblTelefone
            );

            painelPrincipal.getChildren().addAll(
                    painelTitulo,
                    painelLogo);


            return  painelPrincipal;
        }

}
