package cotuba.config;

import org.apache.commons.cli.*;

import java.io.IOException;

public class CommandOptions {
    private final Options options;
    private final String appName;

    public CommandOptions(String appName) throws ParseException, IOException {
        this.options = new Options();
        this.appName = appName;
        createOptions();
    }

    private void createOptions() {
        var opcaoDeDiretorioDosMD = new Option("d", "dir", true,
                "Diretório que contém os arquivos md. Default: diretório atual.");
        this.options.addOption(opcaoDeDiretorioDosMD);

        var opcaoDeFormatoDoEbook = new Option("f", "format", true,
                "Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf");
        options.addOption(opcaoDeFormatoDoEbook);

        var opcaoDeArquivoDeSaida = new Option("o", "output", true,
                "Arquivo de saída do ebook. Default: book.{formato}.");
        options.addOption(opcaoDeArquivoDeSaida);

        var opcaoModoVerboso = new Option("v", "verbose", false,
                "Habilita modo verboso.");
        options.addOption(opcaoModoVerboso);
    }

    public Options getOptions() {
        return options;
    }

    public String getAppName() {
        return appName;
    }
}