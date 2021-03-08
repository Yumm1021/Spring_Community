package com.koreait.community.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.community.model.BoardDTO;
import com.koreait.community.model.BoardDomain;
import com.koreait.community.model.BoardEntity;

@Mapper
public interface BoardMapper {
	int insBoard(BoardEntity p);
	int selMaxPageNum(BoardDTO p);
	List<BoardDomain> selBoardList(BoardDTO p);
	BoardDomain selBoard(BoardEntity p);
	int delBoard(BoardEntity p);
	int updBoard(BoardEntity p);
}
