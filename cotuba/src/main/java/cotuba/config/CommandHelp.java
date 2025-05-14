package cotuba.config;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

public class CommandHelp {
    private final String appName;
    private final HelpFormatter ajuda;

    public CommandHelp(CommandOptions commands) throws ParseException {
        this.appName = commands.getAppName();
        this.ajuda = new HelpFormatter();
    }

    public HelpFormatter getAjuda() {
        return ajuda;
    }

    public String getAppName() {
        return appName;
    }
}
