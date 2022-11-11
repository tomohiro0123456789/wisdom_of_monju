package com.example.wisdom_of_monju.dao;

import java.util.List;

import com.example.wisdom_of_monju.entity.Board;

public interface BoardDao {

	void insertBoard(Board board);

//	int updateIBoard(Board board);

	List<Board> getAll();

	int updateBoard(Board board);

	List<Board> findAll();

}
