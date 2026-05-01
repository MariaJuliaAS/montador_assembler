

import entity.Montador;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o caminho do arquivo .asm: ");
        String caminho = sc.nextLine();

        System.out.print("Digite o tipo (-b para binário | -h para hex): ");
        String tipo = sc.nextLine();

        List<String> linhas = Montador.lerArquivo(caminho);
        if (linhas == null) return;

        Map<String, Integer> labels = Montador.identificarLabels(linhas);
        Map<String, Integer> reg = Montador.criarMapaRegistradores();
        Map<String, Integer> opcode = Montador.cirarMapaOpcode();
        Map<String, Integer> funct = Montador.criarMapaFunct();

        List<String> binarios = new ArrayList<>();

        for (int i = 0; i < linhas.size(); i++) {
            binarios.add(Montador.traduzirInstrucao(
                    linhas.get(i), reg, labels, opcode, funct, i
            ));
        }

        Montador.gerarArquivo(binarios, caminho, tipo);
        Montador.contarInstrucoes(linhas);
    }
}