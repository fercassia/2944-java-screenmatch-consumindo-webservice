package br.com.alura.screenmatch.modelos;

import com.google.gson.annotations.SerializedName;

import javax.xml.datatype.Duration;

public class Titulo implements Comparable<Titulo> {
    @SerializedName("Title") //Forma de nomear os campos utilizando serializedName e
                            // receber os valores dos parametros
    private String nome;
    @SerializedName("Year")
    private Integer anoDeLancamento;
    private Integer anoFinalizacao;
    private boolean incluidoNoPlano;
    private double somaDasAvaliacoes;
    private int totalDeAvaliacoes;
    private int duracaoEmMinutos;

    public Titulo(String nome, int anoDeLancamento) {
        this.nome = nome;
        this.anoDeLancamento = anoDeLancamento;
    }

    public Titulo(TituloOmdb meuTitulo) {
        this.nome = meuTitulo.title();
        this.anoDeLancamento = formataAno(meuTitulo.year())[0];
        this.anoFinalizacao = formataAno(meuTitulo.year())[1];
        this.duracaoEmMinutos = formataTempoDuracao(meuTitulo.runtime());
    }

    public String getNome() {
        return nome;
    }

    public int getAnoDeLancamento() {
        return anoDeLancamento;
    }

    public boolean isIncluidoNoPlano() {
        return incluidoNoPlano;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public int getTotalDeAvaliacoes() {
        return totalDeAvaliacoes;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAnoDeLancamento(int anoDeLancamento) {
        this.anoDeLancamento = anoDeLancamento;
    }

    public void setIncluidoNoPlano(boolean incluidoNoPlano) {
        this.incluidoNoPlano = incluidoNoPlano;
    }

    public void setDuracaoEmMinutos(int duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public void exibeFichaTecnica(){
        System.out.println("Nome do filme: " + nome);
        System.out.println("Ano de lançamento: " + anoDeLancamento);
    }

    public void avalia(double nota){
        somaDasAvaliacoes += nota;
        totalDeAvaliacoes++;
    }

    private int formataTempoDuracao (String duracaoTitulo){
        if (duracaoTitulo == null || duracaoTitulo.isBlank()) {
            throw new IllegalArgumentException("Duração do título está vazia ou nula");
        }

        String[] partesDuracao = duracaoTitulo.trim().split(" ");

        if(partesDuracao.length == 0 || !partesDuracao[0].matches("\\d+") && !partesDuracao[0].equalsIgnoreCase("N/A")){
            throw new IllegalArgumentException("Formato de duração inválido: " + duracaoTitulo);
        }

        if(partesDuracao[0].equalsIgnoreCase("N/A")){
            return 0;
        }

        return Integer.valueOf(partesDuracao[0]);
    }

    private Integer[] formataAno (String anoLancamento){
        if (anoLancamento.isBlank()) {
            throw new IllegalArgumentException("Ano do título está vazia ou nula");
        }

        String[] anosDuracao = anoLancamento.trim().split("[–-]");

        if(anosDuracao.length == 3 && (!anosDuracao[0].matches("\\d+") || !anosDuracao[2].matches("\\d+"))){
            throw new IllegalArgumentException("Ano do título invalido");
        }

        if(anosDuracao.length <= 2 && !anosDuracao[0].matches("\\d+")){
            throw new IllegalArgumentException("Ano do título ");
        }

        if(anosDuracao.length <= 2){
            return new Integer[]{Integer.valueOf(anosDuracao[0]), null};
        }

        return new Integer[]{Integer.valueOf(anosDuracao[0]), Integer.valueOf(anosDuracao[2])};
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
        return "{\n 'nome:'" + nome + '\'' +
                ",\n 'anoDeLancamento':" + anoDeLancamento + "," +
                "\n 'duração': " + duracaoEmMinutos +"\n}";
    }
}
