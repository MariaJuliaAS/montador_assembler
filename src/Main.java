import entity.Montador;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        String caminho;
        String tipo;

        if(args.length >= 2){
            caminho = args[0];
            tipo = args[1];
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Digite: nomeDoArquivo.asm -b ou -h → ");
            String entrada = sc.nextLine();

            String[] partes = entrada.split("\\s+");

            if (partes.length < 2) {
                System.out.println("Entrada inválida!");
                return;
            }

            caminho = partes[0];
            tipo = partes[1];

            sc.close();
        }

        if (!tipo.equals("-b") && !tipo.equals("-h")) {
            System.out.println("Parâmetro inválido! Use -b ou -h");
            return;
        }

        List<String> linhas = Montador.lerArquivo(caminho);
        if (linhas == null) return;

        Map<String, Integer> labels = Montador.identificarLabels(linhas);
        Map<String, Integer> reg = Montador.criarMapaRegistradores();
        Map<String, Integer> opcode = Montador.criarMapaOpcode();
        Map<String, Integer> funct = Montador.criarMapaFunct();

        List<String> binarios = new ArrayList<>();
        Map<String, Integer> contagem = new HashMap<>();

        for (int i = 0; i < linhas.size(); i++) {

            String linha = linhas.get(i);
            String op = linha.split("\\s+")[0];

            contagem.put(op, contagem.getOrDefault(op, 0) + 1);

            String bin = Montador.traduzirInstrucao(
                    linha, reg, labels, opcode, funct, i
            );

            binarios.add(bin);
        }

        Montador.gerarArquivo(binarios, caminho, tipo);

        System.out.println("\nQuantidades por tipo de instruções:");
        for(String inst : contagem.keySet()){
            System.out.println(inst + ": " + contagem.get(inst));
        }

        Map<String, Integer> ciclos = Montador.lerCSV();
        Montador.calcularCPI(contagem, ciclos);
    }
}