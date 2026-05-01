package entity;

import java.io.*;
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

    public static Map<String, Integer> cirarMapaOpcode(){
        Map<String, Integer> op = new HashMap<>();

        op.put("beq", 4);
        op.put("bne", 5);
        op.put("addi", 8);
        op.put("addiu", 9);
        op.put("slti", 10);
        op.put("sltiu", 11);
        op.put("andi", 12);
        op.put("ori", 13);
        op.put("lui", 15);
        op.put("lw", 35);
        op.put("sw", 43);

        op.put("j", 2);
        op.put("jal", 3);

        return op;
    }

    public static Map<String, Integer> criarMapaFunct() {
        Map<String, Integer> funct = new HashMap<>();

        funct.put("sll", 0);
        funct.put("srl", 2);
        funct.put("jr", 8);
        funct.put("mfhi", 16);
        funct.put("mflo", 18);
        funct.put("mult", 24);
        funct.put("multu", 25);
        funct.put("div", 26);
        funct.put("divu", 27);
        funct.put("add", 32);
        funct.put("addu", 33);
        funct.put("sub", 34);
        funct.put("subu", 35);
        funct.put("and", 36);
        funct.put("or", 37);
        funct.put("slt", 42);
        funct.put("sltu", 43);

        funct.put("mul", 2);

        return funct;
    }

    public static String paraBinario(int valor, int bits){
        String bin = Integer.toBinaryString(valor);

        if(bin.length() > bits){
            bin = bin.substring(bin.length() - bits);
        }

        while (bin.length() < bits){
            bin = "0" + bin;
        }

        return bin;
    }

    public static String traduzirInstrucao(String linha,
                                           Map<String, Integer> reg,
                                           Map<String, Integer> labels,
                                           Map<String, Integer> opcodeMap,
                                           Map<String, Integer> functMap,
                                           int linhaAtual){

        String[] partes = linha.replace(",", "").split("\\s+");
        String op = partes[0];

        String tipo = identificarTipo(linha);

        int opcode;
        if(tipo.equals("R")){
            opcode = op.equals("mul") ?28 : 0;
        }else{
            opcode = opcodeMap.get(op);
        }

        if(tipo.equals("R")){
            int rd = getNumeroRegstrador(partes[1], reg);
            int rs =  getNumeroRegstrador(partes[2], reg);
            int rt = getNumeroRegstrador(partes[3], reg);

            int shamt = 0;
            int funct = functMap.get(op);

            return paraBinario(opcode, 6) +
                    paraBinario(rs, 5) +
                    paraBinario(rt, 5) +
                    paraBinario(rd, 5) +
                    paraBinario(shamt, 5) +
                    paraBinario(funct, 6);
        }

        if(tipo.equals("I")){
            int rs = getNumeroRegstrador(partes[2], reg);
            int rt = getNumeroRegstrador(partes[1], reg);
            int imediato;

            if(op.equals("beq") || op.equals("bne")){
                int linhaLabel = labels.get(partes[3]);
                imediato = linhaLabel - (linhaAtual + 1);
            }else{
                imediato = Integer.parseInt(partes[3]);
            }

            return paraBinario(opcode, 6) +
                    paraBinario(rs, 5) +
                    paraBinario(rt, 5) +
                    paraBinario(imediato, 16);
        }

        if(tipo.equals("J")){
            int endereco = labels.get(partes[1]);

            return paraBinario(opcode, 6) +
                    paraBinario(endereco, 26);
        }

        return "";

    }

    public static void gerarArquivo(List<String> binarios, String nomeSaida, String tipo){
        String base = nomeSaida.substring(0, nomeSaida.lastIndexOf("."));
        nomeSaida = tipo.equals("-b") ? base + ".bin" : base + ".hex";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeSaida))){
            if(!tipo.equals("-b") && !tipo.equals("-h")){
                System.out.println("Pârametro inválidp. Use -b ou -h");
                return;
            }

            if(tipo.equals("-h")){
                bw.write("v2.0 raw");
                bw.newLine();
            }

            for(String linhaBin: binarios){
                if(tipo.equals("-b")){
                    bw.write(linhaBin);
                }else{
                    String hex = String.format("%08x", Integer.parseUnsignedInt(linhaBin, 2));
                    bw.write(hex);
                }

                bw.newLine();
            }

            System.out.println("Arquivo gerado: " + nomeSaida);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
