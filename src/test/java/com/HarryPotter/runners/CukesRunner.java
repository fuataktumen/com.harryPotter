package com.HarryPotter.runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(

        plugin = {"json:target/cucumber.json"},
        features = "src/test/resources/features/",
        glue = "com/HarryPotter/stepdefinitions",
        dryRun =false,
        tags="@TestAll"
)
public class CukesRunner {


}
