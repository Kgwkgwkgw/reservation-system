<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="inc/common/head.jsp">
			<jsp:param value="네이버 예약" name="title"/>
</jsp:include>
<title>로그인</title>
</head>
<body>
	<a href="/login/google">
		<img src	="/resources/img/googleLogin.png"/ width="100%">
	</a>
	<a href="/login/naver">
		<img src	=/resources/img/naverLogin.png/ width="100%">
	</a>
	<a href="/login/facebook">
		<img src="/resources/img/facebookLogin.png"/ width="100%">
	</a>
</body>
</html>