package demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class JavaUtility {

    public static void button_clikeble(ChromeDriver driver, WebElement elmentToClick) {

        try {
            if (elmentToClick != null && elmentToClick.isDisplayed()) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.visibilityOf(elmentToClick));
                elmentToClick.click();
            }

            else
                System.out.println("Element is NOT Displayed");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception: " + e.getMessage());
        }

    }

    public static void enter_text(WebElement inputBox, String keysToSend) {
        try {
            if (inputBox != null && inputBox.isDisplayed()) {
                inputBox.sendKeys(keysToSend);
            } else
                System.out.println("Element is Not Displayed for key: " + keysToSend);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public static void navigateTo(ChromeDriver driver, String url) {
        // Navigate to Url
        try {
            if (!driver.getCurrentUrl().equals(url))
                driver.get(url);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exceptions: " + e.getMessage());
        }
    }

    public static long epochTime() {
        // Specify the maximum and Minimum Limit of Epoch Time
        long minEpochTime = Instant.parse("1970-01-01T00:00:00Z").getEpochSecond();
        long maxEpochTime = Instant.parse("2030-01-01T00:00:00Z").getEpochSecond();

        // Randomise the Epoch Time
        long randomEpochTime = ThreadLocalRandom.current().nextLong(minEpochTime, maxEpochTime);
        return randomEpochTime;
    }

    public static void printData(String[] str, List<LinkedHashMap<String, String>> data) {

        // Iterate over the ArrayList and print each HashMap in the specified order
        for (LinkedHashMap<String, String> map : data) {
            for (String key : str) {
                if (map.containsKey(key)) {
                    System.out.println("Key: " + key + ", Value: " + map.get(key));
                }
            }
            System.out.println("-----");
        }

    }

    public static void saveToJSONFile(String name, String rmPath, List<LinkedHashMap<String, String>> data) {

        // Create the Object of Mapper provide the functionality for read or write in JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        File jsonFile = new File(name);
        // Cheak the File is Exist or Not
        if (jsonFile.exists()) {
            System.out.println("File is Exist");
            File jsondel = new File(rmPath);
            jsondel.delete();
        }
        try {
            jsonFile = new File(name);

            // Write JSON to file
            mapper.writeValue(jsonFile, data);
            System.out.println("JSON file created: " + jsonFile.getAbsolutePath());

            // Specify the new directory
            Path newDirectory = Paths.get("src//test//resources");

            // Copy the JSON file to the new directory
            Path targetPath = newDirectory.resolve(jsonFile.getName());
            Files.copy(jsonFile.toPath(), targetPath);

            System.out.println("JSON file copied to: " + targetPath.toAbsolutePath());
            // Validate the JSON file has Data Present or Not
            org.testng.Assert.assertTrue(jsonFile.length() != 0, "JSON File Data is Empty");

        } catch (IOException e) {
            System.out.println("Exception : " + e.getStackTrace());
        }

    }
}
