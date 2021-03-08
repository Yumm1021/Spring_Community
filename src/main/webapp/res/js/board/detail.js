
var data = document.querySelector('#data')
var btnDelElem = document.querySelector('#btnDel')

	if(btnDelElem) {
		btnDelElem.addEventListener('click', function() {
			if(confirm('삭제할거야 ?')) {
				ajax()
			}
	})
	
	function ajax() {
		var {pk, category} = data.dataset
		/*
		var pk = data.dataset.pk
		var category = data.dataset.category
		*/
		fetch(`/board/del/${pk}`, {
			method: 'delete'
		})
	}
}

//------------------------- 댓글 부분 ----------------------------//

// 댓글 리스트
var cmtListElem = document.querySelector('#cmtList') // 댓글 리스트 나타나는 위치
var modalElem = document.querySelector('#modal')
var modCtntElem = document.querySelector('#modCtnt') // 수정 내용
var modBtnElem = document.querySelector('#modBtn') // 수정 버튼

if(modalElem) {
	// 모달 달기 버튼
	var modalCloseElem = document.querySelector('#modClose')
	modalCloseElem.addEventListener('click', function() {
		modalElem.classList.add('hide')
	})
}

function selCmtList() {
	
	fetch(`/cmt?boardPk=${data.dataset.pk}`)
	.then(res => res.json())
	.then(myJson => { // myJson이라는 객체로 배열이 넘어옴
		clearView()
		createView(myJson)
	})
	
	function clearView() {
		cmtListElem.innerHTML =''
	}
	
	function createView(myJson) {
		if(myJson.length === 0) {
			return 
		}
		
		var tableElem = createTable() //tableElem에 계속 append 해줄 거임
		myJson.forEach(function(item) { // forEach문에 콜백함수 보내는 이유 ? 줄게 없을 때까지 값을 item에 넣어라
			tableElem.append(createRecord(item))
		})
		
		cmtListElem.append(tableElem) //append() > 선택한 요소의 내용 맨 뒤에 위치
	}
	
	function createRecord(item) { // 한줄 한줄 레코드를 만드는 함수
		var tr = document.createElement('tr')
		var td_1 = document.createElement('td')
		var td_2 = document.createElement('td')
		var td_3 = document.createElement('td')
		
		td_1.innerText = item.ctnt //내용 적어줌
		td_2.innerText = item.writerNm //이름 가져옴
		
		// 내가 쓴 댓글이라면 삭제, 수정버튼 추가
		var loginUserPk = parseInt(data.dataset.loginuserpk)
		if(loginUserPk === item.writerPk) {
			
				// 삭제 처리
				function delAjax() {
					fetch(`/cmt?boardPk=${item.boardPk}&seq=${item.seq}`, {
						method: 'delete'
					}).then(res => res.json())
					.then(myJson => {
						if(myJson === 1) {
							selCmtList() // 1이라면 selCmtList 호출
						} else {
							alert('삭제 실패')
						}
					})
				}
				
				// 수정 처리
				function modAjax (param) {
					fetch('/cmt', {
						method: 'put',
						headers: {
							'Content-Type': 'application/json'
						},
						body: JSON.stringify(param)
					}).then(res => res.json())
					.then(myJson => {
						if(myJson === 1) {
							modalElem.classList.add('hide') // 수정 실패 했을 땐 닫음
							selCmtList() // 1이라면 selCmtList 호출
						} else {
							alert('수정 실패')
						}
					})
				}
				
			var delBtn = document.createElement('input')
			delBtn.type = 'button'
			delBtn.value = '삭제'
			delBtn.addEventListener('click', function() {
				if(confirm('삭제하시겠습니까?')) {
					delAjax()
				}
			})
			
			var editBtn = document.createElement('input')
			editBtn.type = 'button'
			editBtn.value = '수정'
			editBtn.addEventListener('click', function() {
				// 수정할 수 있는 창이 열림
				modCtntElem.value = item.ctnt
				modalElem.classList.remove('hide') 
				
				modBtnElem.onclick = function() {
					
					var param = {
						boardPk: item.boardPk,
						seq: item.seq,
						ctnt: modCtntElem.value // 수정이 끝난 값의 내용을 가져옴
					}
					modAjax(param)
				}
			})
			
			td_3.append(delBtn) // 삭제
			td_3.append(editBtn) // 수정
		}
		
		tr.append(td_1)
		tr.append(td_2)
		tr.append(td_3)
		
		return tr
		
	}
	
	function createTable() {
		var tableElem = document.createElement('table')
		tableElem.innerHTML = `
		<tr>
			<th>내용</th>
			<th>작성자</th>
			<th>비고</th>
		</tr>`
		return tableElem
	}
}
selCmtList() //최초 시작점임 > 호출

// 댓글 작성
var cmtFrmElem = document.querySelector('#cmtFrm')

if(cmtFrmElem) {
		
		var insBtnElem = cmtFrmElem.insBtn
		var ctntElem = cmtFrmElem.ctnt
		
		function ajax() {
			var ctntVal = ctntElem.value
			if(ctntVal === '') {
				alert('댓글을 작성안하셨으니까 작성부탁드려요!~~~')
				return
			}
			
			var param =  {
				boardPk: data.dataset.pk,
				ctnt: ctntVal,
			}
			fetch('/cmt', {
				method: 'post',
				headers: {
						'Content-Type': 'application/json'
					},
				body: JSON.stringify(param)
			}).then(function(res) {
				return res.json()
			}).then(function(myJson) {
				
				if(myJson===1){
					alert('댓글 성공적으로 작성됨..')
				}else{
					alert('안됨... 생각좀하고 작성 부탁드려여')
				}
			})
		}
	
		insBtnElem.addEventListener('click', ajax)
	}
	