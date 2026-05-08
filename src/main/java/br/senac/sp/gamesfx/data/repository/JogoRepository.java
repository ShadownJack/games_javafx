package br.senac.sp.gamesfx.data.repository;

import br.senac.sp.gamesfx.model.Jogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class JogoRepository {

    public ObservableList<Jogo> getJogos() {
        ObservableList<Jogo> listaJogos = FXCollections.observableArrayList(
                // 1. Objeto: The Legend of Zelda: Tears of the Kingdom
                new Jogo(
                        1,
                        "The Legend of Zelda: Tears of the Kingdom",
                        "Nintendo Switch",
                        "Ação/Aventura",
                        "Nintendo",
                        299.90,
                        true,
                        LocalDate.of(2023, 5, 12)
                ),

                // 2. Objeto: God of War Ragnarök
                new Jogo(
                        2,
                        "God of War Ragnarök",
                        "PlayStation 5",
                        "Ação/Aventura",
                        "Santa Monica Studio",
                        349.90,
                        false,
                        LocalDate.of(2022, 11, 9)
                ),

                // 3. Objeto: Hollow Knight
                new Jogo(
                        3,
                        "Hollow Knight",
                        "PC",
                        "Metroidvania",
                        "Team Cherry",
                        46.99,
                        true,
                        LocalDate.of(2017, 2, 24)
                ) // Fechamento do construtor do Jogo 3
        ); // Fechamento do observableArrayList

        return listaJogos;
    }
}