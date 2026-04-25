package entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Montador {

    public static List<String> lerArquivo(String caminho){
        List<String> linhas = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(caminho))){
            String linha;

            while ((linha = br.readLine()) != null ){
                linha = linha.trim();

                if(!linha.isEmpty()){
                    linhas.add(linha);
                }
            }
        }catch (IOException e){
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        linhas = removerComentarios(linhas);
        return linhas;

    }

    public static List<String> removerComentarios(List<String> linhas){
        List<String> linhasLimpas = new ArrayList<>();

        for(String l: linhas){
            int indiceComentario = l.indexOf("#");

            if(indiceComentario != -1){
                l = l.substring(0, indiceComentario).trim();
            }

            if(!l.isEmpty()){
                linhasLimpas.add(l);
            }
        }
        return linhasLimpas;
    }

}
