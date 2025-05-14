package cotuba.config;

import cotuba.converters.EpubConverter;
import cotuba.converters.PDFConverter;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class CommandInstructions {
    private final String[] args;
    private CommandOptions cmdOptions;
    private CommandHelp ajuda;
    private CommandLine cmd;
    private Path diretorioDosMD;
    private String formato;
    private Path arquivoDeSaida;
    boolean modoVerboso = false;

    public CommandInstructions(CommandOptions cmdOptions, String[] args) throws IOException {
        this.args = args;
        CommandLineParser cmdParser = new DefaultParser();

        try {
            cmd = cmdParser.parse(cmdOptions.getOptions(), args);
            instruct();
            execute();
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            ajuda.getAjuda().printHelp(ajuda.getAppName(), cmdOptions.getOptions());
            System.exit(1);
        }
    }

    public Path getDiretorioDosMD() {
        return diretorioDosMD;
    }

    public boolean isModoVerboso() {
        return modoVerboso;
    }

    public String getFormato() {
        return formato;
    }

    public Path getArquivoDeSaida() {
        return arquivoDeSaida;
    }

    private void instruct() throws IOException {
        try {

            /**
             * Define o diretório onde os arquivos .md devem ser buscados, caso a String
             * passada pelo usuário não seja um diretório retorna uma exceção de IllegalArgumentException.
             * Caso a String esteja em branco o default é usar o diretório atual.
             */
            String nomeDoDiretorioDosMD = cmd.getOptionValue("dir");

            if (nomeDoDiretorioDosMD != null) {
                diretorioDosMD = Paths.get(nomeDoDiretorioDosMD);
                if (!Files.isDirectory(diretorioDosMD)) {
                    throw new IllegalArgumentException(nomeDoDiretorioDosMD + " não é um diretório.");
                }
            } else {
                Path diretorioAtual = Paths.get("");
                diretorioDosMD = diretorioAtual;
            }

            /**
             * Define o formato do ebook. Pode ser: pdf ou epub. Default: pdf
             */
            String nomeDoFormatoDoEbook = cmd.getOptionValue("format");

            if (nomeDoFormatoDoEbook != null) {
                formato = nomeDoFormatoDoEbook.toLowerCase();
            } else {
                formato = "pdf";
            }

            /**
             * Define o nome do arquivo de saída do ebook. Default: book.{formato}.
             */
            String nomeDoArquivoDeSaidaDoEbook = cmd.getOptionValue("output");
            if (nomeDoArquivoDeSaidaDoEbook != null) {
                arquivoDeSaida = Paths.get(nomeDoArquivoDeSaidaDoEbook);
            } else {
                arquivoDeSaida = Paths.get("book." + formato.toLowerCase());
            }
            if (Files.isDirectory(arquivoDeSaida)) {
                // deleta arquivos do diretório recursivamente
                Files.walk(arquivoDeSaida).sorted(Comparator.reverseOrder())
                        .map(Path::toFile).forEach(File::delete);
            } else {
                Files.deleteIfExists(arquivoDeSaida);
            }
            modoVerboso = cmd.hasOption("verbose");
        }catch (Exception ex) {
            throw new IllegalStateException("Erro ao montar o comando.", ex);
        }
    }

    private void execute() throws IOException {
        if("pdf".equals(formato)){
            PDFConverter.converter(this);
        } else if("ebook".equals(formato)){
            EpubConverter.converter(this);
        }
    }
}
