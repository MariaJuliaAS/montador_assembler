import entity.Montador;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String caminho = "C:\\Dev\\UFERSA\\3 Semestre\\Arq. e Org. de Comp\\testeMontador.asm";

        // 1. ler arquivo
        List<String> linhas = Montador.lerArquivo(caminho);

        // 2. labels
        Map<String, Integer> tabelaLabels = Montador.identificarLabels(linhas);

        // 3. registradores
        Map<String, Integer> tabelaRegs = Montador.criarMapaRegistradores();

        System.out.println("=== TRADUÇÃO ===");

        // 4. percorrer instruções
        for (int i = 0; i < linhas.size(); i++) {

            String linha = linhas.get(i);

            String tipo = Montador.identificarTipo(linha);

            System.out.println("\nInstrucao: " + linha);
            System.out.println("Tipo: " + tipo);

            // 👉 aqui entra a tradução
        }
    }
}