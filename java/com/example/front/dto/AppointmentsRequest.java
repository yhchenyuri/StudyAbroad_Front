package com.example.front.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class AppointmentsRequest {
	 @NotBlank(message = "姓名不能為空")
	 private String name;

	 @NotBlank(message = "電話不能為空")
	 @Pattern(
		        regexp = "^(09\\d{8}|0\\d{1,2}-\\d{8})$",
		        message = "電話格式錯誤（例：0912345678 或 02-12345678）"
		    )
	 private String phone;

	 @Email(message = "Email格式錯誤")
	 @NotBlank(message = "Email不能為空 請輸入正確Email格式 例如: user@example.com")
	 private String email;

	 private Integer countryId;
	 private String requirement;
}	    
	


