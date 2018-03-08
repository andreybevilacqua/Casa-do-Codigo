<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Livros de Java, iPhone, Ruby, PHP e muito mais - Casa do Código</title>

<c:url value="/" var="contextPath" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

<link rel="stylesheet" href="${contextPath}resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${contextPath}resources/css/bootstrap-theme.min.css">

</head>
<body>

	<div class="container">
	
		<h1>Login da Casa do Código</h1>

		<!-- "/login" é o default do Spring -->
		<form:form servletRelativeAction="/login" method="POST">
			<!-- No commandName tu define que todos os path apontam pra "produto" -->

			<!-- Receber os valores da tela -->
			<!-- O Spring faz o bind automatico entre os campos com nome "name" e os campos do Model -->
			<!-- contando que esses campos estejam EXATAMENTE com o mesmo nome -->
			<div class="form-group">
				<!-- Ao usar o Form do Spring, não precisa mais de type nem name -->
				<!-- Somente path -->
				<label>E-mail</label>
				<input name="username" type="text" class="form-control"/>
			</div>
			<div class="form-group">
				<label>Senha</label>
				<input type="password" name="password" class="form-control" />
			</div>
			<button type="submit" class="btn btn-primary">Logar</button>

		</form:form>
	
	</div>
	
</body>
</html>