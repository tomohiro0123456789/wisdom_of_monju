package com.example.wisdom_of_monju.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BoardForm {

	@Size(min = 1, max = 30, message = "Please input 30characters or less")
	private String title;

	@NotNull
	private String name;

	@NotNull
	@Size(min = 1, max = 1000, message = "Please input 1000characters or less")
	private String text;

//	private String image;
	// バリデーション方法を考えるので一回放置
	// テキストに乗ってた方法を流用予定

}
