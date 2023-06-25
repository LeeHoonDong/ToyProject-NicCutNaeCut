package com.example.backendldh.ServiceTest;

import com.example.backendldh.Domain.Media;
import com.example.backendldh.Service.GetImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SpringBootTest
class GetImageServiceTest {

    @Autowired
    private GetImageService getImageService;
    @Test
    public void crawlerSuccess() throws InterruptedException {
        Media mediaTest = new Media();
        //String url = "https://photogray-download.aprd.io/?id=c2Vzc2lvbklkPUFFMDFEN0Q0QzRDNjM5QUM4Q0Y5MzIwOUM5OTY5QkEzMzdEREE3QTREQzhGMEVENDM1QzU0NzA0RDRFQzM1QkQmbW9kZT1TUEVDSUFMJmNvbGxhYm9OYW1lPU5PTkUmZXhwaXJlZERhdGU9MTY4NzI1NjkxMQ==";
        //String url="http://l4c01.lifejuin.biz/QRImage/20230606/SEL.MPO.YEONNAM02/161723678/index.html";
        //String url= "https://photogray-download.aprd.io/?id=c2Vzc2lvbklkPTI4ODIyMTQ5QTYwMTAzMDZEMTA3MjI3ODk1NjQ0MTA0QTMyNzg3NUYyNkJGRTE3NUE0OEMxQTRDRTZDM0YxMDkmbW9kZT1CQVNFJmNvbGxhYm9OYW1lPU5PTkUmZXhwaXJlZERhdGU9MTY4NjY1MTQ0Nw==";
        String url = "https://photogray-download.aprd.io/?id=c2Vzc2lvbklkPUFGN0I1NDY3MTQ5QkM1OUUwRkIyNzVDREUzRkM5RENGQTc0OUU5NjQ4RkI3RDU0QzE2RjVDNTI4RjQ2M0ZFNTkmbW9kZT1CQVNFJmNvbGxhYm9OYW1lPU5PTkUmZXhwaXJlZERhdGU9MTY4NzQyOTA0MQ==";
        mediaTest=getImageService.crawler(url);
        System.out.println("mediaTestImage = " + mediaTest.getImage());
        System.out.println("mediaTestVideo = " + mediaTest.getVideo());
    }
}