package br.com.alura.screenmatch;

import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {
    HttpClient client = null;
    try{
      client = HttpClient.newHttpClient();

      String baseURL = "https://www.omdbapi.com/?t=%s&apikey=358d9190";
      String nomeTitulo = "top gun";

      String uriFormatada = String.format(baseURL, nomeTitulo.replace(" ", "+"));

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uriFormatada))
          .build();

      HttpResponse<String> response = client
          .send(request, HttpResponse.BodyHandlers.ofString());

      String json = response.body();


      Gson gson = new GsonBuilder()
          .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
          .create();
      TituloOmdb meuTitulo = gson.fromJson(json, TituloOmdb.class);

      Titulo tituloRecebido = new Titulo(meuTitulo);
      System.out.println(tituloRecebido);
    }catch (Error e){
      System.out.println(e.getMessage());
      System.out.println(e.getStackTrace());
    }finally {
      client.close();
    }
  }
}