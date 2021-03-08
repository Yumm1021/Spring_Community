
function goToDetail(boardPk) {
	location.href= `/board/detail?boardPk=${boardPk}`
}


var listContentElem = document.querySelector('#listContent')
var category = listContentElem.dataset.category

function getBoardList() {
	
	if(!page) {
		page = 1
	}
	
	fetch(`/board/listData?category=${category}&page=${page}&rowCnt=2`)
	.then(res => res.json())
	.then(myJson => {
		console.log(myJson)
		boardproc(myJson)
	})
}

	function boardproc(myJson) {
		if(myJson.length === 0) {
			listContentElem.innerHTML = '<div>글 없음</div>'
			return
		}
		
		var table = document.createElement('table')
		table.classList.add('basic-table')
		
		var htr = document.createElement('tr')
		htr.innerHTML = `
			<td>번호</td>
			<td>제목</td>
			<td>조회수</td>
			<td>작성일</td>
			<td>작성자</td>
		`
		table.append(htr)
		
		myJson.forEach(function(item) {
			var tr = document.createElement('tr')
			tr.classList.add('record')
			tr.addEventListener('click', function () {
				goToDetail(item.boardPk)
			})
			var td_1 = document.createElement('td')
			var td_2 = document.createElement('td')
			var td_3 = document.createElement('td')
			var td_4 = document.createElement('td')
			var td_5 = document.createElement('td')
			
			td_1.innerText = item.seq
			td_2.innerText = item.title
			td_3.innerText = item.hits
			td_4.innerText = item.regDt
			td_5.innerText = item.writerNm
			
			tr.append(td_1)
			tr.append(td_2)
			tr.append(td_3)
			tr.append(td_4)
			tr.append(td_5)
			
			table.append(tr)
		})
		
		listContentElem.innerHTML =''
		listContentElem.append(table)
	}

	getBoardList()

	function getMaxPageNum() {
		fetch(`/board/getMaxPageNum?category=${category}&rowCnt=2`)
		.then(res => res.json())
		.then(myJson => {
			pageProc(myJson)
		})
	}
	
	var pagingContentElem = document.querySelector('#pagingContent')
	function pageProc(myJson) {
		for(let i=1; i<=myJson; i++) {
			var span = document.querySelector('span')
			span.innerText = i
			span.classList.add('pointer') //span마다 pointer 클래스 주기
			//span에 click 이벤트를 건다. 클릭하면 getBoardList 함수 호출
			span.addEventListener('click', function() {
				getBoardList(i)
			})
			pagingContentElem.append(span)
		}
	}
	
	getMaxPageNum()
	