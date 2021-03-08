<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div>
		<a href="/board/list?category=${requestScope.data.category}">리스트로 돌아가기</a>
	</div>
		<c:if test="${loginUser.userPk == data.userPk}">
			<div>
			<button id="btnDel">삭제</button>
			<a href="/board/edit?category=${requestScope.data.category}&boardPk=${requestScope.data.boardPk}">
				<button>수정</button>
			</a>
			</div>
		</c:if>
	
	<div id="data" data-loginuserpk="${sessionScope.loginUser.userPk}" data-pk="${requestScope.data.boardPk}" data-category="${requestScope.data.category}">
		<div style="font-size: 18px; color: #228B22; font-weight: 600; line-height:150%">번호 : ${data.seq}</div>
		<div style="font-size: 18px; color: #228B22; font-weight: 600; line-height:150%">제목 : ${data.title}</div>
		<div style="font-size: 18px; color: #228B22; font-weight: 600; line-height:150%">내용 : ${data.ctnt}</div>
		<div style="font-size: 18px; color: #228B22; font-weight: 600; line-height:150%">조회수 : ${data.hits}</div>
		<div style="font-size: 18px; color: #228B22; font-weight: 600; line-height:150%">작성일 : ${data.regDt}</div>
		<div style="font-size: 18px; color: #228B22; font-weight: 600; line-height:150%">작성자 : ${data.writerNm}</div>
	</div>
	
	<c:if test="${sessionScope.loginUser != null}">
		<div id="cmt">
			<h4>댓글 쓰기</h4>
			<form id="cmtFrm" onsubmit="return false;">
					<input type="text" name="ctnt">
					<input type="button" value="댓글등록" name="insBtn"> <!-- form 줬기 때문에 type은 button으로 -->
			</form>
		</div>
		
		<div id="modal" class="hide">  <!-- 첫 화면에서는 안 보임 -->
		
			<div class="modal-content">
				<span id="modClose">창닫기</span>
				<input type="text" id="modCtnt">
				<input type="button" id="modBtn" value="수정">
			</div>
			
		</div>
	</c:if>
		
		<div id="cmtList"></div>
<script src="/res/js/board/detail.js"></script>