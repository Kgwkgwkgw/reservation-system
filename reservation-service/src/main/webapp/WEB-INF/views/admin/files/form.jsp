<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<jsp:include page="../inc/head.jsp">
	<jsp:param value="파일 업로드" name="title"/>
</jsp:include>
<body>
	<form method="post" action="${url }" enctype="multipart/form-data">
	    title : <input type="text" name="title"><br>
	    <input type="file" name="file"><br>
	    <input type="file" name="file"><br>
	    <input type="submit" value="등록">
	</form>
</body>
</html>