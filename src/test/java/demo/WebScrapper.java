package demo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.logging.Level;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebScrapper {

    ChromeDriver driver;

    @BeforeClass(enabled = true)
    public void createDriver() {

        System.out.println("Create a Driver");

        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }

    @Test(enabled = true)
    public void testCase01() {
        try {

            System.out.println("Start Test Case 01");

            String fileName = "hockey-team-data.json";
            String removeFilePath = "src/test/resources/hockey-team-data.json";

            JavaUtility.navigateTo(driver, "https://www.scrapethissite.com/pages/");

            WebElement hockeyPage = driver.findElement(By.partialLinkText("Hockey"));
            JavaUtility.button_clikeble(driver, hockeyPage);

            String[] info = { "name", "year", "pct" };

            // Get the List of WebElement has value percentage
            List<WebElement> pct = driver.findElements(By.xpath("//tr//td[contains(@class,'pct')]"));
            // Declare the Listof LiknkedHashMap so that the insertion order is maintain
            ArrayList<LinkedHashMap<String, String>> arrayListOfHashmaps = new ArrayList<>();

            // Iterate over the percentage
            for (WebElement element : pct) {
                float winPCT = Float.parseFloat(element.getText().trim());
                // if the Percentage is less than 40% then store the value in LinkedHashMap
                if (winPCT < 0.40) {
                    LinkedHashMap<String, String> hash = new LinkedHashMap<>();
                    hash.clear();
                    for (int i = 0; i < 3; i++) {
                        // store each value of name,year and percentage and get by the webElement
                        WebElement keyVal = driver.findElement(By.xpath("//tr//td[contains(text(),'"
                                + element.getText().trim() + "')]/../td[contains(@class,'" + info[i] + "')]"));

                        hash.put(info[i], keyVal.getText().trim());

                    }
                    long epochTime = JavaUtility.epochTime();
                    hash.put("Epoch Time", Long.toString(epochTime));
                    // new hash add to hash array
                    arrayListOfHashmaps.add(hash);

                }
            }
            // Define the desired order of keys
            String[] keyOrder = { "Epoch Time", "name", "year", "pct" };
            JavaUtility.printData(keyOrder, arrayListOfHashmaps);

            // Convert ArrayList<HashMap> to JSON
            JavaUtility.saveToJSONFile(fileName, removeFilePath, arrayListOfHashmaps);

            System.out.println("End TestCase 01");

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    @Test(enabled = true)
    public void testCase02() {

        try {
            System.out.println("Start TestCase 02");

            String fileName = "oscar-winner-data.json";
            String removeFilePath = "src/test/resources/oscar-winner-data.json";

            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(30));

            // Navigate to the URL
            JavaUtility.navigateTo(driver, "https://www.scrapethissite.com/pages/");

            String[] Year = { "2015", "2014", "2013", "2012", "2011", "2010" };

            WebElement oscarPage = driver.findElement(By.partialLinkText("Oscar"));
            JavaUtility.button_clikeble(driver, oscarPage);

            // Create the LinkedHashMap so that the insertion order is mentain
            ArrayList<LinkedHashMap<String, String>> oscarWinner = new ArrayList<>();
            // Iterate the String array of Year and Store the value
            for (String str : Year) {

                WebElement year = driver.findElement(By.id(str));
                // year.click();
                JavaUtility.button_clikeble(driver, year);

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[1]")));

                // Store the Title,Nomination,Awared, and BestFilm (true or false) in the List
                // of WebElement
                List<WebElement> filmtitle = driver.findElements(By.xpath("//tbody//tr/td[@class='film-title']"));
                List<WebElement> filmNomination = driver
                        .findElements(By.xpath("//tbody//tr/td[@class='film-nominations']"));
                List<WebElement> filmAward = driver.findElements(By.xpath("//tbody//tr/td[@class='film-awards']"));
                List<WebElement> bestFilm = driver.findElements(By.xpath("//tbody//tr/td[@class='film-best-picture']"));

                // Iterte the For Loop to Stored only Top 5 Best Movies
                for (int i = 0; i < 5; i++) {

                    LinkedHashMap<String, String> oscar = new LinkedHashMap<>();
                    // oscar.clear();
                    oscar.put("Title", filmtitle.get(i).getText());// Add Title
                    oscar.put("Nomincations", filmNomination.get(i).getText()); // Add Nominations
                    oscar.put("Awards", filmAward.get(i).getText()); // Add Awards

                    // First Movies has Nominated as BestFilm Movie as Award Flag so the bestFilm
                    // contains True;
                    if (i == 0) {
                        oscar.put("bestFilm", "True");
                    } else if (bestFilm.get(i).getText().isEmpty()) { // cheak for the movie return has an empty value or not
                        oscar.put("bestFilm", "False");
                    }

                    oscar.put("Year", str);
                    // Epoch time
                    long epochTime = JavaUtility.epochTime();
                    oscar.put("Epoch Time", Long.toString(epochTime));

                    oscarWinner.add(oscar);
                }
            }

            // Define the desired order of keys
            String[] keyOrder = { "Title", "Nomincations", "Awards", "bestFilm", "Year", "Epoch Time" };
            // Print the keys and value to LinkeHashMap
            JavaUtility.printData(keyOrder, oscarWinner);
            // Save to Json File and Validate
            JavaUtility.saveToJSONFile(fileName, removeFilePath, oscarWinner);

            System.out.println("End TestCase 02");

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    @AfterClass(enabled = true)
    public void closeDriver() {
        System.out.println("Close Driver");
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

}
