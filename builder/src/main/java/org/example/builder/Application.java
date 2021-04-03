package org.example.builder;

import org.apache.commons.cli.*;
import org.example.builder.parser.ScenarioParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Application implements ApplicationRunner {

    private final ScenarioParser scenarioParser;

    private static String scenarioFileLocation;

    public Application(ScenarioParser scenarioParser) {
        this.scenarioParser = scenarioParser;
    }

    public static void main(String[] args) throws ParseException {
        CommandLineParser commandLineParser = new DefaultParser();
        Options options = new Options();
        options.addOption("f", "file", true, "Bpmn file location");
        CommandLine commandLine = commandLineParser.parse(options, args);

        scenarioFileLocation = commandLine.getOptionValue("f");

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        scenarioParser.parse(new File(scenarioFileLocation));
    }

}
