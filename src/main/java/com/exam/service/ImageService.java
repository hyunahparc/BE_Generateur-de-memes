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
	
	public String saveBase64Image(String base64Data) throws Exception {
		
		String base64Image = base64Data.split(",")[1];
		// 이미지 파일 이름 생성 (중복 방지를 위해 UUID 사용)
		String fileName = UUID.randomUUID().toString() + ".jpg";
		
		// 디렉터리가 없으면 생성
		File directory = new File(IMAGE_DIR);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		
		// 저장할 파일의 전체 경로 생성
		File outputFile = new File(IMAGE_DIR +fileName);
		
		// Base64 디코딩하여 이미지 파일로 저장
		try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            outputStream.write(imageBytes);
        }
		
		// 이미지 접근 URL 반환 (ngrok 사용 시 ngrok 도메인으로 변경)
        return "http://localhost:8080/images/" + fileName;
//        return "https://special-hugely-labrador.ngrok-free.app/images/" + fileName;

	}
	
}
