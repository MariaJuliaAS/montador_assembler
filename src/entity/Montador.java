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

        return linhas;

    }

}
