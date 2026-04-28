package entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Montador {

    static Set<String> tipoR = new HashSet<>(Arrays.asList(
            "sll", "srl", "jr", "mfhi", "mflo", "mult", "multu", "div", "divu",
            "add", "addu", "sub", "subu", "and", "or", "slt", "sltu", "mul"
    ));

    static Set<String> tipoI = new HashSet<>(Arrays.asList(
            "beq", "bne", "addi", "addiu", "slti", "sltiu", "andi", "ori", "lui", "lw", "sw"
    ));

    static Set<String> tipoJ = new HashSet<>(Arrays.asList(
            "j", "jal"
    ));

    public static List<String> lerArquivo(String caminho){
        List<String> linhas = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(caminho))){
            String linha;

            while ((linha = br.readLine()) != null ){
                linha = linha.trim();

                int indiceComentario = linha.indexOf("#");
                if(indiceComentario != -1){
                    linha = linha.substring(0, indiceComentario).trim();
                }

                if(!linha.isEmpty() && !linha.startsWith(".")){
                    linhas.add(linha);
                }
            }
        }catch (IOException e){
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return null;
        }

        return linhas;
    }

    public static Map<String, Integer> identificarLabels(List<String> linhas){
        Map<String, Integer> tabela = new HashMap<>();
        int contadorInstrucoes = 0;

        for(int i = 0; i < linhas.size(); i++){
            String linha = linhas.get(i);

            if(linha.contains(":")){
                String[] partes = linha.split(":");
                tabela.put(partes[0].trim(), contadorInstrucoes);

                if(partes.length > 1 && !partes[1].trim().isEmpty()){
                    linhas.set(i, partes[1].trim());
                    contadorInstrucoes++;
                }else{
                    linhas.set(i, "");
                }

            }else{
                contadorInstrucoes++;
            }
        }

        linhas.removeIf(String::isEmpty);
        return tabela;
    }

    public static Map<String, Integer> criarMapaRegistradores(){
        Map<String, Integer> reg = new HashMap<>();

        reg.put("$zero", 0);
        reg.put("$at", 1);
        reg.put("$v0", 2);
        reg.put("$v1", 3);

        reg.put("$a0", 4);
        reg.put("$a1", 5);
        reg.put("$a2", 6);
        reg.put("$a3", 7);

        reg.put("$t0", 8);
        reg.put("$t1", 9);
        reg.put("$t2", 10);
        reg.put("$t3", 11);
        reg.put("$t4", 12);
        reg.put("$t5", 13);
        reg.put("$t6", 14);
        reg.put("$t7", 15);

        reg.put("$s0", 16);
        reg.put("$s1", 17);
        reg.put("$s2", 18);
        reg.put("$s3", 19);
        reg.put("$s4", 20);
        reg.put("$s5", 21);
        reg.put("$s6", 22);
        reg.put("$s7", 23);

        reg.put("$t8", 24);
        reg.put("$t9", 25);

        reg.put("$gp", 28);
        reg.put("$sp", 29);
        reg.put("$fp", 30);
        reg.put("$ra", 31);

        return reg;
    }

    public static int getNumeroRegstrador(String reg, Map<String, Integer> tabelaReg){
        reg = reg.trim();

        if(reg.matches("\\$\\d+")){
            int numero = Integer.parseInt(reg.substring(1));

            if(numero >= 0 && numero <= 31){
                return numero;
            }
        }

        if(tabelaReg.containsKey(reg)){
            return tabelaReg.get(reg);
        }

        System.out.println("Registrador inválido: " + reg);
        return -1;
    }

    public static String identificarTipo(String instrucao){
        String op = instrucao.split("\\s+")[0];

        if(tipoR.contains(op)) return "R";
        if(tipoI.contains(op)) return "I";
        if(tipoJ.contains(op)) return "J";

        return "Desconhecido";
    }

}
