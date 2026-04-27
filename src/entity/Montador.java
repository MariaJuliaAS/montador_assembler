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
}
