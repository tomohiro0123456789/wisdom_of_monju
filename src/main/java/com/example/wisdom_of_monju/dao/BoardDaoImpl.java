package com.example.wisdom_of_monju.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.wisdom_of_monju.entity.Board;

@Repository
public class BoardDaoImpl implements BoardDao {

	private final JdbcTemplate jdbcTemplate;

	public BoardDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insertBoard(Board board) {
		jdbcTemplate.update("INSERT INTO board(title,name,text,created) VALUES(?,?,?,?)", board.getTitle(),
				board.getName(), board.getText(), board.getCreated());

	}

	@Override
	public int updateBoard(Board board) {
		return jdbcTemplate.update("UPDATE board SET title = ? , name = ? , text = ? , created = ? WHERE id = ?",
				board.getTitle(), board.getName(), board.getText(), board.getCreated());

	}
//データベースからのデータ取得の場合はfindAllの方が好ましいので、そちらを利用する
	@Override
	public List<Board> getAll() {
		String sql = "SELECT id, title,name,text,created FROM board";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<Board> list = new ArrayList<Board>();
		for (Map<String, Object> result : resultList) {
			Board board = new Board();
			board.setId((int) result.get("id"));
			board.setTitle((String) result.get("title"));
			board.setName((String) result.get("name"));
			board.setText((String) result.get("text"));
			board.setCreated(((Timestamp) result.get("created")).toLocalDateTime());
			list.add(board);
		}
		return list;

	}

	@Override
	public List<Board> findAll() {
		String sql = "SELECT id, title,name,text,created FROM board";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<Board> list = new ArrayList<Board>();
		for (Map<String, Object> result : resultList) {
			Board board = new Board();
			board.setId((int) result.get("id"));
			board.setTitle((String) result.get("title"));
			board.setName((String) result.get("name"));
			board.setText((String) result.get("text"));
			board.setCreated(((Timestamp) result.get("created")).toLocalDateTime());
			list.add(board);
		}
		return list;
	}
}
