package cotuba;

import java.io.IOException;
import cotuba.config.CommandInstructions;
import cotuba.config.CommandOptions;
import cotuba.config.CommandHelp;
import org.apache.commons.cli.ParseException;


public class Main {
  public static void main(String[] args) {
      try {
          var cmdOptions = new CommandOptions("cotuba");
          var cmdHelp = new CommandHelp(cmdOptions);
          var cmdInstructions = new CommandInstructions(cmdOptions, args);
          System.exit(0);
      } catch (ParseException | IOException e) {
          throw new RuntimeException(e);
      }
  }

}
