package com.example.backendldh.ControllerTest;

import com.example.backendldh.Controller.GetImageController;
import com.example.backendldh.Domain.Media;
import com.example.backendldh.Exception.UrlNotAllowedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class GetImageControllerTest {

    @Autowired
    private GetImageController getImageController;
    @Test
    void goBackSuccess(){
        Media media = new Media();
        media.setFilePath("/Users/ldh/nic/media/82300297/20230621213849");
        getImageController.goBack(media);
    }
    @Test
    void getURLSuccess() throws InterruptedException{
        String url = "https://photogray-download.aprd.io/?id=c2Vzc2lvbklkPUFGN0I1NDY3MTQ5QkM1OUUwRkIyNzVDREUzRkM5RENGQTc0OUU5NjQ4RkI3RDU0QzE2RjVDNTI4RjQ2M0ZFNTkmbW9kZT1CQVNFJmNvbGxhYm9OYW1lPU5PTkUmZXhwaXJlZERhdGU9MTY4NzQyOTA0MQ==";
        ResponseEntity<Void> status=getImageController.getURL(url);
        int code= status.getStatusCode().value();
        Assertions.assertThat(code).isEqualTo(200);
    }
    @Test
    void getURLFailure() throws InterruptedException{
        String url="http://l4c01.lifejuin.biz/QRImage/20230606/SEL.MPO.YEONNAM02/161723678/index.html";
        try{
            ResponseEntity<Void> status=getImageController.getURL(url);
            int code= status.getStatusCode().value();
        }catch(UrlNotAllowedException ex)
        {
            ex.printStackTrace();
            System.out.println("ex = " + ex);
        }
    }
}