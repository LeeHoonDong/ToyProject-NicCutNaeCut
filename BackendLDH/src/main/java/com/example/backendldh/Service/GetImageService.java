package com.example.backendldh.Service;

import com.example.backendldh.Domain.Media;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.Thread.sleep;

@Service
public class GetImageService {
    public void deleteFolder(File folder) {
        if(folder.isDirectory()){
            File [] files = folder.listFiles();
            if(files!=null){
                for(File file : files){
                    deleteFolder(file);
                }
            }
        }
        folder.delete();
    }
    public Media crawler(String url) throws InterruptedException {
        Media media = new Media();
        String savePath = "/Users/ldh/nic/media";
        System.setProperty("webdriver.chrome.driver","/Users/ldh/nic/NiCutNaeCut/chromedriver");//크롬 드라이버 셋팅
        //Chrome에서 다운로드 위치 설정 바꿔줘야함.
        ChromeOptions options = new ChromeOptions();
        String user_id="82300297";
        LocalDateTime currentDatetime=LocalDateTime.now();
        savePath+="/"+user_id
                +"/"+currentDatetime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        //  /Users/ldh/nic/media/82300297/202030621200912
        options.setExperimentalOption("prefs", createChromePreferences(savePath));

        //바꾼 옵션으로 driver열어주고
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);

        //*******************사실상 여기가 크롤링하는 부분인데************************
        //***************수정이 필요하다고 생각함. 아직 완성되지 않은 코드***************
        List<WebElement> button_list = click_button_tag(driver,savePath,media); //button tag 클릭
        if(button_list.size()!=0){
            driver.quit();
            return media;
        }
        //button tag 클릭해서 이미지와 동영상 다운로드 받기
        //button tag 가 없다면, footer 의 a tag 클릭하기
        List<WebElement> a_list = click_a_tag(driver,savePath,media);
        if(a_list.size()!=0){
            driver.quit();
            return media;
        }
        //***************수정이 필요하다고 생각함. 아직 완성되지 않은 코드***************
        //********************************************************************

        driver.quit();
        return media;
    }

    private List<WebElement> click_a_tag(WebDriver driver, String savePath, Media media) throws InterruptedException {
        WebElement footer_element= driver.findElement(By.tagName("footer"));
        sleep(1000);
        List<WebElement> a_list = footer_element.findElements(By.tagName("a"));
        for(WebElement webElement : a_list){
            webElement.click();
            sleep(500);
            media.setImage(savePath+"/image.jpg");
        }
        sleep((500));
        return a_list;
    }

    private List<WebElement> click_button_tag(WebDriver driver,String savePath,Media media) throws InterruptedException {
        List<WebElement> list=driver.findElements(By.tagName("button"));
        sleep(1000);
        //없다면, footer부분의 a태그들 싹 다 클릭
        for(WebElement webElement : list){
            webElement.click();
            sleep(500);
            media.setImage(savePath+"/image.jpg");
        }
        sleep(500);
        return list;
    }

    //크롬 다운로드 경로 바꾸기
    private static ImmutableMap<String, Object> createChromePreferences(String downloadPath) {
        return ImmutableMap.of(
                "download.default_directory", downloadPath,
                "download.prompt_for_download", false,
                "download.directory_upgrade", true,
                "safebrowsing.enabled", true);
    }
}