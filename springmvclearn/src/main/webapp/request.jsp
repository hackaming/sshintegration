<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action = "dealrequest.jsp" method="get">
<input type="text" name="name" />
<input type="submit" value="��������">
<% session.setAttribute("name", 11); %>
</form>
</body>
</html>