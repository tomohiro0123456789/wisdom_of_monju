package com.example.wisdom_of_monju.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wisdom_of_monju.dao.BoardDao;
import com.example.wisdom_of_monju.entity.Board;

@Service
public class BoardServiceImp implements BoardService {

	private final BoardDao dao;

	@Autowired
	BoardServiceImp(BoardDao dao) {
		this.dao = dao;
	}

	@Override
	public void save(Board board) {
		dao.insertBoard(board);

	}

//	@Override
//	public void update(Board board) {
//		if (dao.updateBoard(board) == 0) {
//			throw new BoardNotFoundException("can't find the same ID");
//		}
//
//	}
	// 例外クラスを制作後に使用予定

	@Override
	public List<Board> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Board> findAll() {
		return dao.findAll();
	}

}
