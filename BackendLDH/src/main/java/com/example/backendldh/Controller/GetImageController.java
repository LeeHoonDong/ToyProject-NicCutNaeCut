package com.example.backendldh.Controller;

import com.example.backendldh.Domain.Media;
import com.example.backendldh.Exception.UrlNotAllowedException;
import com.example.backendldh.Service.GetImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.net.URI;

@RestController
public class GetImageController {
    @Autowired
    private GetImageService getImageService;
    @DeleteMapping("/goBack")//사진등록화면에서 뒤로가기 버튼 클릭 시 이미지 삭제 후 뒤로 돌아가기!
    public void deleteImageForS3(@RequestBody Media media){
        //일단 이미지 삭제 먼저!
        //해당 메소드가 실행되는 경우는 S3에서 이미지 삭제가 불가능한 경우를 제외한다.
        //왜냐? 해당 메소드는 무조건 getURL이 실행되고 난 후 유저의 행동에 따라 실행이 결정됨.
        String filePath=media.getFilePath();
        File file = new File(filePath);
        getImageService.deleteFolder(file);
        //return ResponseEntity.status(200).build();
    }
    @GetMapping("/image")
    public ResponseEntity<Void> getURL(@RequestParam("url") String url) throws InterruptedException {

       String getImageUrl=null;
       HttpStatus statusCode=getStatusFromUrl(url);
       int statuscode=statusCode.value();
       //유효기간이 지났는대도 불구하고 200 OK를 보내는 사이트가 있고
        // 200 OK를 보내지 않는 사이트가 있음
        //일단 200 OK가 아니면 에러 발생시키고 404
       if(statuscode!=200){
           throw new UrlNotAllowedException("유효시간이 지나 사진을 다운받을 수 없어요...");
       }
       //200 OK인데도 가져온 이미지 url이 없으면 위와 UrlNotAllowedException발생

        getImageUrl=getImageService.crawler(url).getImage();;

       if(getImageUrl==null){
           throw new UrlNotAllowedException("유효시간이 지나 사진을 다운받을 수 없어요...");
       }

       URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{imageurl}").buildAndExpand(getImageUrl).toUri();
       return ResponseEntity.created(location).build();
    }
    private HttpStatus getStatusFromUrl(String url){
        RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.headForHeaders(url);
            return HttpStatus.OK;
        }catch(HttpClientErrorException e) {
            return HttpStatus.NOT_FOUND;
        }
    }
}
