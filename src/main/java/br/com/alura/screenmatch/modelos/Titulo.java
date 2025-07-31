package br.com.alura.screenmatch.modelos;

import com.google.gson.annotations.SerializedName;

public class Titulo implements Comparable<Titulo> {
  @SerializedName("Title") //Forma de nomear os campos utilizando serializedName e
  // receber os valores dos parametros
  private String nome;
  @SerializedName("Year")
  private Integer anoDeLancamento;
  private Integer anoEncerramento;
  private Integer duracaoEmMinutos;
  private Integer totalDeAvaliacoes;
  private boolean incluidoNoPlano;
  private double somaDasAvaliacoes;

  public Titulo(String nome, int anoDeLancamento) {
    this.nome = nome;
    this.anoDeLancamento = anoDeLancamento;
  }

  public Titulo(TituloOmdb meuTitulo) {
    this.nome = meuTitulo.title();
    this.anoDeLancamento = formataAno(meuTitulo.year())[0];
    this.anoEncerramento = formataAno(meuTitulo.year())[1];
    this.duracaoEmMinutos = formataTempoDuracao(meuTitulo.runtime());
  }

  public String getNome() {
    return nome;
  }

  public Integer getAnoDeLancamento() {
    return anoDeLancamento;
  }

  public Integer getDuracaoEmMinutos() {
    return duracaoEmMinutos;
  }

  public Integer getTotalDeAvaliacoes() {
    return totalDeAvaliacoes;
  }

  public boolean isIncluidoNoPlano() {
    return incluidoNoPlano;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setAnoDeLancamento(Integer anoDeLancamento) {
    this.anoDeLancamento = anoDeLancamento;
  }

  public void setDuracaoEmMinutos(Integer duracaoEmMinutos) {
    this.duracaoEmMinutos = duracaoEmMinutos;
  }

  public void setIncluidoNoPlano(boolean incluidoNoPlano) {
    this.incluidoNoPlano = incluidoNoPlano;
  }

  public void exibeFichaTecnica(){
    System.out.println("Nome do filme: " + nome);
    System.out.println("Ano de lançamento: " + anoDeLancamento);
  }

  public void avalia(double nota){
    somaDasAvaliacoes += nota;
    totalDeAvaliacoes++;
  }

  private Integer formataTempoDuracao (String duracaoTitulo){
    if (duracaoTitulo == null || duracaoTitulo.isBlank()) {
      return null;
    }

    String[] partesDuracao = duracaoTitulo.trim().split(" ");

    if(partesDuracao.length == 0 ||
        !partesDuracao[0].matches("\\d+") && !partesDuracao[0].equalsIgnoreCase("N/A")){
      throw new IllegalArgumentException("Formato de duração inválido: " + duracaoTitulo);
    }

    if(partesDuracao[0].equalsIgnoreCase("N/A")){
      return null;
    }
    return Integer.valueOf(partesDuracao[0]);
  }

  private Integer[] formataAno (String anoLancamento){
    if(anoLancamento == null){
      return new Integer[]{null, null};
    }
    int QUANTIDADE_DE_ANOS_LIMITE = 2;

    String[] anosDuracao = anoLancamento.trim().split("[–-]");

    String anoInicio = anosDuracao[0].matches("\\d+") ? anosDuracao[0] : "N/A";
    String anoFim = (anosDuracao.length == QUANTIDADE_DE_ANOS_LIMITE) &&
        anosDuracao[anosDuracao.length-1].matches("\\d+") ? anosDuracao[1] : "N/A";

    Integer anoInicioFormatado = !anoInicio.equals("N/A") ? Integer.valueOf(anoInicio) : null;
    Integer anoFimFormatado = !anoFim.equals("N/A") ? Integer.valueOf(anoFim) : null;

    return new Integer[]{anoInicioFormatado, anoFimFormatado};
  }

  public double pegaMedia(){
    return somaDasAvaliacoes / totalDeAvaliacoes;
  }

  @Override
  public int compareTo(Titulo outroTitulo) {
    return this.getNome().compareTo(outroTitulo.getNome());
  }

  @Override
  public String toString() {
    return "{\n 'nome': " + '\'' + nome + '\'' +
        ",\n 'anoDeLancamento': " + anoDeLancamento +
        ",\n 'anoFinalizacao': " + anoEncerramento + "," +
        "\n 'duração': " + duracaoEmMinutos + "\n}";
  }
}
