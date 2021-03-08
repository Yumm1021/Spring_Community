package com.koreait.community.cmt;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.koreait.community.model.CmtDomain;
import com.koreait.community.model.CmtEntity;

@Mapper
public interface CmtMapper {

	int insCmt(CmtEntity p);
	List<CmtDomain> selCmtList(CmtEntity p); // Json으로 변하는 건 객체기준이라 <CmtDomain> 굳이 안 바꿔도 됨
	int updCmt(CmtEntity p);
	int delCmt(CmtEntity p);
}
