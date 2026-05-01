

import entity.Montador;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        // 🔹 caminho do arquivo ASM
        String caminho = "C:\\Dev\\UFERSA\\3 Semestre\\Arq. e Org. de Comp\\testeMontador.asm"; // coloca seu arquivo aqui

        // 🔹 ler arquivo
        List<String> linhas = Montador.lerArquivo(caminho);

        if (linhas == null) {
            System.out.println("Erro ao ler arquivo.");
            return;
        }

        // 🔹 identificar labels
        Map<String, Integer> labels = Montador.identificarLabels(linhas);

        // 🔹 mapas necessários
        Map<String, Integer> registradores = Montador.criarMapaRegistradores();
        Map<String, Integer> opcodeMap = Montador.cirarMapaOpcode();
        Map<String, Integer> functMap = Montador.criarMapaFunct();

        System.out.println("===== LABELS =====");
        for (String label : labels.keySet()) {
            System.out.println(label + " -> " + labels.get(label));
        }

        System.out.println("\n===== BINÁRIO =====");

        // 🔹 traduzir instruções
        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i);

            String binario = Montador.traduzirInstrucao(
                    linha,
                    registradores,
                    labels,
                    opcodeMap,
                    functMap,
                    i
            );

            System.out.println(linha);
            System.out.println(binario);
            System.out.println();
        }
    }
}