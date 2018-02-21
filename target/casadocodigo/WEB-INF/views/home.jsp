<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Livros de Java, iPhone, Ruby, PHP e muito mais - Casa do
	Código</title>
</head>
<body>
	<h1>Casa do Código</h1>
	<table>
		<tr>
			<td>Java 8 Prático</td>
			<td>Certificação OCJP</td>
		</tr>
		<tr>
			<td>TDD Na Prática - Java</td>
			<td>Google Android</td>
		</tr>
	</table>
	
	<a href="${s:mvcUrl('PC#listar').build() }" rel="nofollow"> Lista de Produtos </a>
	
</body>
</html>