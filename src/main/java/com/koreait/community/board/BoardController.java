package com.koreait.community.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.community.Const;
import com.koreait.community.SecurityUtils;
import com.koreait.community.model.BoardDTO;
import com.koreait.community.model.BoardDomain;
import com.koreait.community.model.BoardEntity;

@Controller // 목표 : jsp 파일 여는 것
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;

	@Autowired
	private SecurityUtils sUtils;

	@GetMapping("/home")
	public void home() {
	}

	@GetMapping("/list") // 화면 뿌리는 부분
	public void list() { // jsp 파일에 보내줄 게 있을 때 model 씀
		//model.addAttribute(Const.KEY_LIST, service.selBoardList(p));
	}
	
	@ResponseBody
	@GetMapping("/listData") // 데이터 주는 부분
	public List<BoardDomain> listData(BoardDTO p) {
		return service.selBoardList(p);
	}
	
	@ResponseBody
	@GetMapping("/getMaxPageNum")
	public int selMaxPageNum(BoardDTO p) {
		return service.selMaxPageNum(p);
		
	}

	@GetMapping("/write") // 화면 뿌리는 용도
	public String write(BoardEntity p) {
		return "board/writeEdit";
	}

	@PostMapping("/write")
	public String writeProc(BoardEntity p, HttpSession hs) { // 주소체계와 파일명이 불일치 할 때, redirect

		p.setUserPk(sUtils.getLoginUserPk(hs)); // p에는 로그인한 사람의 pk값 까지 담겨져 있음
		int result = service.insBoard(p);
		return "redirect:/board/detail?boardPk=" + p.getBoardPk();
	}

	@GetMapping("/detail")
	public void detail(BoardDomain p, Model model, HttpSession hs) {
		model.addAttribute(Const.KEY_DATA, service.selBoardWithHits(p, hs));
	}

	@ResponseBody
	@DeleteMapping("/del/{boardPk}")
	public Map del(BoardEntity p, HttpSession hs) {
		p.setUserPk(sUtils.getLoginUserPk(hs)); // 로그인 한 사람의 pk 값
		System.out.println("boardPk : " + p.getBoardPk());
		
		Map<String, Object> rVal = new HashMap<>();
		rVal.put(Const.KEY_DATA, service.updBoard(p));
		return rVal;
	}
	
	@GetMapping("/edit")
	public String edit(BoardEntity p, Model model) {
		BoardDomain vo2 = service.selBoard(p);
		model.addAttribute(Const.KEY_DATA, vo2);
		
		return "board/writeEdit";
	}
	
	@PostMapping("/edit")
	public String edit(BoardEntity p, HttpSession hs) {
		p.setUserPk(sUtils.getLoginUserPk(hs));
		service.updBoard(p);
		return "redirect:/board/detail?boardPk=" + p.getBoardPk();
	}

}
