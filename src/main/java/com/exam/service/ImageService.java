package com.exam.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
	// 저장할 디렉토리 경로 (프로젝트 루트 기준)
	private static final String IMAGE_DIR = "uploaded-images/";
	private static final String HTML_DIR = "uploaded-htmls/";
	
	public String saveBase64Image(String base64Data) throws Exception {
		
		String base64Image = base64Data.split(",")[1];
		// 이미지 파일 이름 생성 (중복 방지를 위해 UUID 사용)
		String fileName = UUID.randomUUID().toString() + ".jpg";
		String htmlName = UUID.randomUUID().toString() + ".html";
		
		// 디렉터리가 없으면 생성
		File imageDirectory = new File(IMAGE_DIR);
		if (!imageDirectory.exists()) {
			imageDirectory.mkdirs();
		}
		File htmlDirectory = new File(HTML_DIR);
		if (!htmlDirectory.exists()) {
			htmlDirectory.mkdirs();
		}
		
		// 저장할 파일의 전체 경로 생성
		File imageOutputFile = new File(IMAGE_DIR +fileName);
		
		// Base64 디코딩하여 이미지 파일로 저장
		try (OutputStream outputStream = new FileOutputStream(imageOutputFile)) {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            outputStream.write(imageBytes);
            outputStream.flush(); // 버퍼에 남은 데이터까지 모두 파일에 
        }
		
		Thread.sleep(1000);
		
		File htmlOutputFile = new File(HTML_DIR +htmlName);
		// 공유용 HTML 저장 (OG 태그 포함)
	    String htmlContent = """
	        <!DOCTYPE html>
	        <html>
	        <head>
	          <meta charset="UTF-8">
	          <meta property="og:title" content="내 밈">
	          <meta property="og:description" content="내가 만든 밈을 봐줘!">
	          <meta property="og:image" content="http://localhost:8080/images/%s">
	          <meta property="og:type" content="website">
	        </head>
	        <body>
	          <h1>내가 만든 밈</h1>
	          <img src="http://localhost:8080/images/%s" style="max-width: 100%%;">
	        </body>
	        </html>
	        """.formatted(fileName, fileName);
	    
	    try (OutputStream htmlOut = new FileOutputStream(htmlOutputFile)) {
	        htmlOut.write(htmlContent.getBytes());
	        htmlOut.flush(); // 버퍼에 남은 HTML까지 확실히 저장
	    }
		
		// 이미지 접근 URL 반환 (ngrok 사용 시 ngrok 도메인으로 변경)
//        return "http://localhost:8080/images/" + fileName;
//        return "ngrokdomain/images/" + fileName;
        
        // 공유할 HTML 파일의 접근 URL 반환
        return "http://localhost:8080/html/" + htmlName;
//        return "ngrokdomain" + htmlName;
        

	}
	
}
