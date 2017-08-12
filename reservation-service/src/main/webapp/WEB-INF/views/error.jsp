<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>일시적 오류</title>
</head>
<body>
	오류가 발생했습니다. 
	잠시 후에 다시 시도해주세요.
	<c:if test="${ error != null}">
		<p>원인 : ${ error}</p>
	</c:if>
</body>
</html>