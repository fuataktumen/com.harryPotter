package com.HarryPotter.stepdefinitions;

import com.HarryPotter.utilities.Driver;
import cucumber.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before

    public void setuP(){


    }


    @After
    public void tearDown(Scenario scenario){
        //if the scenario fails take the screenshot
        if(scenario.isFailed()){
            final byte[] screenshot = ((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot,"image/png");
        }

    }

    @After("@Fleet")

    public void TearDownStoreManager(){


        System.out.println("This is from AFTER SPECİAL");
    }

}

