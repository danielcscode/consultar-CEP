package projetos.java.ti.consultarcep;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var dados = new Scanner(System.in);
        var usuario = new Usuario();

        
        System.out.println("Digite seu nome.");
        System.out.print("Nome: ");
        usuario.setNome(dados.next());
        System.out.println();
        System.out.println("Digite seu telefone:");
        System.out.print("Telefone: ");
        usuario.setTelefone(dados.next());
        System.out.println();

        System.out.println("Digite seu CEP.");
        System.out.println("ATENÇÃO! Insira apenas numero!");
        System.out.print("CEP: ");
        usuario.setCep(dados.next());

        String endereco = "http://viacep.com.br/ws/" + usuario.getCep() + "/json";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
                    response.body();

            System.out.println();
            System.out.println("nome: ''"+ usuario.getNome()+"''" );
            System.out.println("telefone: ''"+ usuario.getTelefone()+"''" );
            System.out.println(response.body());

            FileWriter escrita = new FileWriter(usuario.getNome()+".json");
            escrita.write((response.body()));
            escrita.close();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Não foi possivel verificar CEP!");

        }
    }
}