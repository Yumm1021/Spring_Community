<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:importAttribute name="menuList"/>
<header>
	<c:if test="${sessionScope.loginUser != null}"> <!-- 로그인이 된 상태 -->
		${sessionScope.loginUser.nm}님 환영합니다
	</c:if>
	<nav>
		<ul class="menuItems">
			<li>
				<c:choose>
					<c:when test="${sessionScope.loginUser == null}">
						<a href="/user/login">login</a>
					</c:when>
					<c:otherwise>
						<a href="/user/logout">logout</a>
					</c:otherwise>
				</c:choose>
			</li>
			<c:forEach items="${menuList}" var="menu">
				<li>
					<a href="/board/list?category=<c:out value="${menu.category}"/>">
						<c:out value="${menu.nm}"></c:out>
					</a>
				</li>
			</c:forEach>
			<c:if test="${sessionScope.loginUser != null}">
				<li>
					<a href="/user/profile">프로필</a>
				</li>
			</c:if>
		</ul>
	</nav>
</header>