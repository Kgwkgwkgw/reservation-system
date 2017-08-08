<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<jsp:include page="../inc/head.jsp">
	<jsp:param value="카테고리 목록보기/등록/수정/삭제" name="title"/>
</jsp:include>
<body>
	<div class="wrap">

      <h1>카테고리 목록보기/수정/삭제</h1>

      <h2>목록보기/수정/삭제</h2>
      <c:if test="${ !empty list }">
        <ul class="_categoryList">
        			<c:forEach var="item" items= "${list}" varStatus="status">
        				<li class="_item" data-status="" data-id="${item.id }">
        				    번호 : ${status.count}
        				   	카테고리_id : ${item.id}
        				    카테고리 : <em class="_name">${item.name}</em>
                    <input type="text" value="" placeholder="수정할 이름 입력해주세요."
                      class="hide _editInput">
        				    <button type="button" class="_modify">수정</button>
        				    <button type="button" class="_destroy">삭제</button>
        				 </li>
        			</c:forEach>
        </ul>
      </c:if>
	</div>

  <script>
  require(['category'], function(category) {
      // Configuration loaded now, safe to do other require calls
      // that depend on that config.
      "use strict";
      category.init();

    });
  </script>
</body>
</html>
