import entity.Montador;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String caminho = "C:\\Dev\\UFERSA\\3 Semestre\\Arq. e Org. de Comp\\testeMontador.asm";

        // ETAPA 1 + 2 (já estão juntas no seu código)
        List<String> linhas = Montador.lerArquivo(caminho);
        
        // Verificar se houve erro na leitura do arquivo
        if (linhas == null) {
            System.err.println("Falha crítica: não foi possível ler o arquivo. Encerrando programa.");
            System.exit(1);
        }

    }
}