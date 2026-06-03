package br.senac.sp.gamesfx;

import br.senac.sp.gamesfx.data.repository.JogoRepository;
import br.senac.sp.gamesfx.model.Jogo;
import javafx.application.Application;

import java.time.LocalDate;

public class Launcher {
    public static void main(String[] args) {
        // 2. Parte do professor: Inicia a interface gráfica
        // Importante: Application.launch é um método bloqueante,
        // ele deve ser chamado por último.
        Application.launch(TelaPrincipal.class, args);
    }
}