package com.testautomationguru.ocular;

import java.io.File;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.testautomationguru.ocular.comparator.OcularResult;
import com.testautomationguru.ocular.end2end.RichFaces;
import com.testautomationguru.ocular.end2end.RichFacesPoll;
import com.testautomationguru.ocular.end2end.RichFacesWithFragment;
//import com.testautomationguru.ocular.Oc

public class OcularTest {

    private WebDriver driver;

    @BeforeTest
    public void ocularConfigSetup() {
        Ocular.config().reset();
        Ocular.config().snapshotPath(Paths.get(System.getProperty("user.dir"), "src/test/resources/end2end/snapshot"))
            .resultPath(Paths.get(".", "target"))
            .globalSimilarity(90)
            .saveSnapshot(true);

        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
        driver = new ChromeDriver();
//        System.setProperty("phantomjs.binary.path", "E:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
//        driver = new PhantomJSDriver();
//        System.setProperty("webdriver.gecko.driver","e:\\geckodriver.exe");
//        driver = new FirefoxDriver();
    }

    @Test
    public void ocularPageCompareUsingWebDriver() throws Exception {
        
        RichFaces richFacePage = new RichFaces(driver);

        richFacePage.goTo("repeat", "ruby");
        this.takeSnapShot(driver, "C:\\Users\\Admin\\Desktop\\clone_ocular\\ocular\\target\\Yasuo1.png") ;

        OcularResult result = richFacePage.comparePage();
        
        System.out.println("compare page & page:  " + result.getSimilarity());
        
    }

    @Test(dependsOnMethods = { "ocularPageCompareUsingWebDriver" })
    public void ocularFragmentCompareUsingWebDriver(){
        RichFacesWithFragment richFacePage = new RichFacesWithFragment(driver);

        richFacePage.goTo("repeat", "ruby");
        
        OcularResult result = richFacePage.compareElement();
        System.out.println("compare element & element: " + result.getSimilarity());
        Assert.assertTrue(result.isEqualsImages());
//        Assert.assertEquals(99, result.getSimilarity());
        

    }

    @Test(dependsOnMethods = { "ocularPageCompareUsingWebDriver" })
    public void ocularCompareExcludingElement() throws InterruptedException {

        RichFacesPoll richFacePage = new RichFacesPoll(driver);

        richFacePage.goTo("poll", "wine");

        OcularResult result = richFacePage.compare();

        Assert.assertTrue(result.isEqualsImages());
        System.out.println("richface Poll 1: " + result.getSimilarity());

        // Wait for sometime to get the server date time changed
        Thread.sleep(4000);

        result = richFacePage.compare();
        System.out.println("richface Poll 2: " + result.getSimilarity());
        
//        Assert.assertEquals(100, result.getSimilarity());
        System.out.println("richface Poll 2: " + result.getSimilarity());

    }
    
    @Test
    public void compareElementAndWeb() {
        RichFacesWithFragment richFacePage = new RichFacesWithFragment(driver);

        richFacePage.goTo("repeat", "ruby");
        
        OcularResult result = richFacePage.compareDriver();
        System.out.println("compare page & element: " + result.getSimilarity());
        Assert.assertTrue(result.isEqualsImages());
    }

    @AfterTest
    public void quitDriver() {
        driver.quit();
    }
    
    public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file

                File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

            //Move image file to new destination

                File DestFile=new File(fileWithPath);

                //Copy file at destination

                FileUtils.copyFile(SrcFile, DestFile);

    }
}
