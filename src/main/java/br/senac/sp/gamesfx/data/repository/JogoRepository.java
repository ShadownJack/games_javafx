package br.senac.sp.gamesfx.data.repository;

import br.senac.sp.gamesfx.model.Jogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class JogoRepository {
    public ObservableList<Jogo> getJogos(){
        // 1. Objeto: The Legend of Zelda: Tears of the Kingdom
        Jogo jogo1 = new Jogo(
                1,
                "The Legend of Zelda: Tears of the Kingdom",
                "Nintendo Switch",
                "Ação/Aventura",
                "Nintendo",
                299.90,
                true,
                LocalDate.of(2023, 5, 12)
        );

        // 2. Objeto: God of War Ragnarök
        Jogo jogo2 = new Jogo(
                2,
                "God of War Ragnarök",
                "PlayStation 5",
                "Ação/Aventura",
                "Santa Monica Studio",
                349.90,
                false,
                LocalDate.of(2022, 11, 9)
        );

        // 3. Objeto: Hollow Knight
        Jogo jogo3 = new Jogo(
                3,
                "Hollow Knight",
                "PC",
                "Metroidvania",
                "Team Cherry",
                46.99,
                true,
                LocalDate.of(2017, 2, 24)
        );


            ObservableList<Jogo> listaJogos = FXCollections
                    .observableArrayList();
        return null;
    }
}
