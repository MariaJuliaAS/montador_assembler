import entity.Montador;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> linhas = Montador.lerArquivo("filePath");
        for (int i = 0; i < linhas.size(); i++){
            System.out.println((i + 1) + ": " + linhas.get(i));
        }

    }
}