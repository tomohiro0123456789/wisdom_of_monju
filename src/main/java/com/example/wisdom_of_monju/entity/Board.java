package com.example.wisdom_of_monju.entity;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data

public class Board {
	private int id;
	private String title;
	private String name;
	private String text;
	private MultipartFile image;
	private LocalDateTime created;

}
