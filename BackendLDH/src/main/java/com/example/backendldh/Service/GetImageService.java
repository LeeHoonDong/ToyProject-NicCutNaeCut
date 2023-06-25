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
        System.out.println("folder = " + folder);
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
        //진짜 겁나게 편협한 생각 일반화의 오류일수도 있지만
        //body부분에 button있다면, 그거 클릭
        List<WebElement> list=driver.findElements(By.tagName("button"));
        sleep(1000);
        //없다면, footer부분의 a태그들 싹 다 클릭
        for(WebElement webElement : list){
            webElement.click();
            sleep(500);
            media.setImage(savePath+"/image.jpg");
        }
        sleep(500);
        //***************수정이 필요하다고 생각함. 아직 완성되지 않은 코드***************
        //********************************************************************

        driver.quit();
        return media;
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
//TODO
//1. 다운로드 경로 바꾸기
//  -> ASIS: 다운로드 폴더, TOBE: 로컬 nic/media : 완료
//2. Amazon S3에 저장하고 해당 이미지 url 받아오기
//3. 해당 이미지 url DB에 저장하기
//4. 해당 이미지 url