import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class MergeJava {
    private static String nomeArquivo;

    public static void removePackageImport(StringBuilder str) {
        String[] linhas = str.toString().split("\\R");
        str.setLength(0);
        for (String linha : linhas) {
            if (!linha.startsWith("package ") && !linha.startsWith("import ")) {
                str.append(linha).append(System.lineSeparator());
            }
        }
    }

    public static void main(String[] args) {
        File f = new File("./entrada");
        File[] arquivos = f.listFiles(); // retorna um array de Files
        String[] nomes = f.list(); // retorna o nome dos arquivos em Strings

        List<Arquivo> listaArquivosFinal = new ArrayList<>();
        List<String> imports = new ArrayList<>();

        for (File arquivo : arquivos) {
            try {
                //leitura completa do arquivo
                String dados = new String(Files.readAllBytes(arquivo.toPath()));

                //Controladores para remover o public das classes que não são main
                Boolean trocouPublic = false;
                Boolean appendTrocouPublic = false;
                StringBuilder auxRemoverPublic = new StringBuilder();

                //define o nome do arquivo principal
                if (dados.contains("public static void main(String[] args)")) {
                    nomeArquivo = arquivo.getName();
                }

                // array que le linha por linha
                String[] linhas = dados.split("\\R");

                // cria uma nova string para remover os packages
                StringBuilder sb = new StringBuilder();

                sb.append("//===== INICIO DO ARQUIVO: ").append(arquivo.getName()).append(" =====").append(System.lineSeparator());

                //le as linhas individualmente
                for (String linha : linhas) {
                    //se nao for o arquivo principal, isso é feito, pois só a classe principal pode ser public
                    if (!dados.contains("public static void main(String[] args)")) {
                        //Junta em uma String o conteudo até achar o primeiro "{", e ao final remove a anotacao public
                        if (!trocouPublic) {
                            auxRemoverPublic.append(linha).append(System.lineSeparator());
                        }
                        if (linha.contains("{") && !trocouPublic) {
                                trocouPublic = true;
                                auxRemoverPublic = new StringBuilder(auxRemoverPublic.toString().replace("public ", ""));
                        }
                    } else {
                        //pula essa verificacao se for a main
                        appendTrocouPublic = true;
                    }

                    //se ele ja tiver feito a primeira parte de remover o public, os packages e os imports só adiciona a linha
                    if (appendTrocouPublic) {
                        sb.append(linha).append(System.lineSeparator());
                    } else if (trocouPublic) {
                        //senao, se ele ja tiver feito a primeira parte de remover o public, ele remove os packages e imports, e adiciona tudo ao codigo
                        removePackageImport(auxRemoverPublic);
                        sb.append(auxRemoverPublic);
                        appendTrocouPublic = true;
                    }
                }

                sb.append("//=====FIM DO ARQUIVO: ").append(arquivo.getName()).append(" =====")
                        .append(System.lineSeparator());

                //gera o arquivo final
                listaArquivosFinal.add(new Arquivo(arquivo.getName(), sb.toString()));
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

        StringBuilder conteudoFinal = new StringBuilder();

        conteudoFinal.append("// Arquivo gerado automaticamente, Feito por Gabriel Evangelista Massara")
                .append(System.lineSeparator());

        for (String imp : imports) {
            conteudoFinal.append(imp).append(System.lineSeparator());
        }

        for (Arquivo arq : listaArquivosFinal) {
            conteudoFinal.append(arq.getConteudo()).append(System.lineSeparator());
        }

        try {
            Files.writeString(Paths.get("saida", nomeArquivo), conteudoFinal.toString(), StandardCharsets.UTF_8);
            System.out.println("Arquivo gerado com sucesso: " + nomeArquivo);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
}