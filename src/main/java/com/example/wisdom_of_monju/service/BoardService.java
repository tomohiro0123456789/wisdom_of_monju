package com.example.wisdom_of_monju.service;

import java.util.List;

import com.example.wisdom_of_monju.entity.Board;

public interface BoardService {

	void save(Board board);

//	void update(Board board);

	List<Board> getAll();

	List<Board> findAll();
}
