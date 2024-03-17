package com.ssafy.kdkd.controller;

import com.ssafy.kdkd.domain.dto.account.ProfileDeleteDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
public class FileController {
//	@PostMapping("/fileUpload")
//	@Operation(summary = "파일업로드")
//	public ResponseEntity<?> fileUpload(@RequestParam("file")MultipartFile file){
//		//시간과 originFilename 으로 매핑시켜서 sro주소를 만들어낸다.
//		Date date = new Date();
//		StringBuilder sb = new StringBuilder();
//
//		//file image가 없을 경우
//		if(file.isEmpty()){
//			sb.append(("none"));
//		}else{
//			sb.append(date.getTime());
//			sb.append(file.getOriginalFilename());
//		}
//
//		if(!file.isEmpty()){
//			File dest = new File("C://image/feed/" + sb.toString());
//			try{
//				file.transferTo(dest);
//			} catch (IOException e) {
//
//			}
//		}
//
//	}

}
