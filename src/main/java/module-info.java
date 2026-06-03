module br.senac.sp.gamesfx {
    // Dependências de Interface e Controle
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    // Dependências de Banco de Dados
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;

    // Permite que o FXML acesse sua classe principal e controllers
    opens br.senac.sp.gamesfx to javafx.fxml;

    // Permite que o TableView do JavaFX acesse seus Models (Essencial!)
    opens br.senac.sp.gamesfx.model to javafx.base;

    // Abre os pacotes de dados/repositório caso você use frameworks de reflexão
    // ou precise de acesso do java.sql
    opens br.senac.sp.gamesfx.data.repository to java.base;
    opens br.senac.sp.gamesfx.data to java.base;

    exports br.senac.sp.gamesfx;
}