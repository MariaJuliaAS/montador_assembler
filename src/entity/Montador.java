package entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Montador {

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

}
