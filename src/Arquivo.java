public class Arquivo {
    private String nome;
    private String conteudo;
    private Boolean temMain;

    public Arquivo(String nome, String conteudo) {
        this.nome = nome;
        this.conteudo = conteudo;
        this.temMain = conteudo.contains("public static void main(String[] args)");
    }

    public String getNome() {
        return nome;
    }

    public Boolean getTemMain() {
        return temMain;
    }

    public String getConteudo() {
        return conteudo;
    }
}
