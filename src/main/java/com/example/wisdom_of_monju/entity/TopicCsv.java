package com.example.wisdom_of_monju.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
//変な情報が増えてもスルーしてくれる
@JsonIgnoreProperties(ignoreUnknown = true)
//習得したデータの表示順を指定
@JsonPropertyOrder({ "ID", "title", "name", "text", "created" })

public class TopicCsv {

	@JsonProperty("ID")
	private long id;

	@JsonProperty("title")
	private String title;

	@JsonProperty("name")
	private String name;

	@JsonProperty("text")
	private String text;

	@JsonProperty("created")
	private LocalDateTime created;

}
