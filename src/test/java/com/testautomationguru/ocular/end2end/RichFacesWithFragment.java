package com.testautomationguru.ocular.end2end;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.testautomationguru.ocular.Ocular;
import com.testautomationguru.ocular.comparator.OcularResult;
import com.testautomationguru.ocular.snapshot.Snap;

@Snap("RichFaces-LeftMenu.png")
public class RichFacesWithFragment {

    private final WebDriver driver;
    
    public RichFacesWithFragment(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @FindBy(css="div.left-menu")
    private WebElement leftmenu;

    public void goTo(String demo, String skin){
        driver.get("http://showcase.richfaces.org/richfaces/component-sample.jsf?demo=" + demo + "&skin=" + skin);
    }
    
    
    public OcularResult compareElement() {

        return Ocular.snapshot()
                     .from(this)
                     .sample()
                     .using(driver)
                     .element(leftmenu)
                     .compare();
    }
    
    public OcularResult compareDriver() {

        return Ocular.snapshot()
                     .from(this)
                     .sample()
                     .using(driver)
                     .compare();
    }
}
