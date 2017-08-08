<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${postUrl }" method="post">
		<label for="title">블로그 제목</label>
		<input type="text" name="title" id="title">
		<label for="contents">내용</label>
		<input type="text" name="contents" id="contents">
		<input type="submit" value="블로그 게시">
	</form>
</body>
</html>