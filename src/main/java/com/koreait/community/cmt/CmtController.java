package com.koreait.community.cmt;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.koreait.community.SecurityUtils;
import com.koreait.community.model.CmtDomain;
import com.koreait.community.model.CmtEntity;

@RequestMapping("/cmt")
@RestController // 목표 : json형태로 데이터 주는 것 , 자동으로 Responsebody가 들어가있음
public class CmtController {
	
	@Autowired
	private CmtService service;
	
	@Autowired
	private SecurityUtils sUtils;
	
	@PostMapping // put과 post는 requestbody
	public int ins(@RequestBody CmtEntity p, HttpSession hs) {
		p.setWriterPk(sUtils.getLoginUserPk(hs));
		return service.insCmt(p);
		
	}

	@GetMapping
	public List<CmtDomain> list(CmtEntity p) {
		return service.selCmtList(p);
	}
	
	@PutMapping
	public int upd(@RequestBody CmtEntity p, HttpSession hs) {
		System.out.println("boardPk : " + p.getBoardPk());
		System.out.println("seq : " + p.getSeq());
		System.out.println("ctnt: " + p.getCtnt());
		p.setWriterPk(sUtils.getLoginUserPk(hs));
		return service.updCmt(p);
	}
	
	@DeleteMapping //delete와 get은 쿼리스트링
	public int del(CmtEntity p, HttpSession hs) {
		p.setWriterPk(sUtils.getLoginUserPk(hs));
		return service.delCmt(p);
	}
	
}
