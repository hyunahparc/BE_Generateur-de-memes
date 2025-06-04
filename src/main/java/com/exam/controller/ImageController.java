package com.exam.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.service.ImageService;

@RestController
@RequestMapping("/api/images")
public class ImageController {
	
	private final ImageService imageService;

	@Autowired
	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}
	
	// POST /api/images 요청 처리
	@PostMapping("/upload")
	public ResponseEntity<String> uploadImage(@RequestBody Map<String, String> request) {
		// 프론트에서 보내준 JSON에서 "image" 키를 추출 (Base64 문자열)
		String base64Image = request.get("image");
		
		// 값이 없거나 비었으면 400 bad request 응답
		if(base64Image == null || base64Image.isEmpty()) {
			System.out.println("이미지가 없습니다.");
			return ResponseEntity.badRequest().body("이미지가 없습니다.");
		}
		try {
			// 서비스 로직을 통해 이미지를 저장하고 접근 가능한 URL을 반환하기
			String url = imageService.saveBase64Image(base64Image);
			
			// 성공적으로 처리되었을 때 200 ok + 이미지 URL 반환
			return ResponseEntity.ok(url);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("이미지 저장 실패 : " + e.getMessage());
		}
	}
	

}
