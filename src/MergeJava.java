import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class MergeJava {
    private static String nomeArquivo;

    public static void main(String[] args) {
        File f = new File("./entrada");
        File[] arquivos = f.listFiles(); // retorna um array de Files
        String[] nomes = f.list(); // retorna o nome dos arquivos em Strings

        List<Arquivo> listaArquivosFinal = new ArrayList<>();
        List<String> imports = new ArrayList<>();

        for (File arquivo : arquivos) {
            try {
                String dados = new String(Files.readAllBytes(arquivo.toPath()));

                if(dados.contains("public static void main(String[] args)")) {
                    nomeArquivo = arquivo.getName();
                } else {
                    dados = dados.replace("public class", "class");
                }

                // array que le linha por linha
                String[] linhas = dados.split("\\R");

                // cria uma nova string para remover os packages
                StringBuilder sb = new StringBuilder();

                sb.append("//===== INICIO DO ARQUIVO: ").append(arquivo.getName()).append(" =====").append(System.lineSeparator());

                for (String linha : linhas) {
                    if (!linha.trim().startsWith("package ") && !linha.startsWith("import ")) {
                        sb.append(linha).append(System.lineSeparator());
                    } else if (linha.startsWith("import ")) {
                        if (!imports.contains(linha)) {
                            imports.add(linha);
                        }
                    }
                }

                sb.append("//=====FIM DO ARQUIVO: ").append(arquivo.getName()).append(" =====").append(System.lineSeparator());

                listaArquivosFinal.add(new Arquivo(arquivo.getName(), sb.toString()));
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

        StringBuilder conteudoFinal = new StringBuilder();

        conteudoFinal.append("// Arquivo gerado automaticamente, Feito por Gabriel Evangelista Massara").append(System.lineSeparator());

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