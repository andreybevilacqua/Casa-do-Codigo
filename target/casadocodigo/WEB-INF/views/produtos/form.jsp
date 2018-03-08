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

	<!-- Criando o cabeçalho da tela -->
	<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${s:mvcUrl('HC#index').build() }">Casa do Código</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="${s:mvcUrl('PC#listar').build() }">Lista de Produtos</a></li>
				<li><a href="${s:mvcUrl('PC#form').build() }">Cadastro de Produtos</a></li>
			</ul>
		</div>
	</div>
	</nav>

	<div class="container">
	
		<h1>Cadastro de Produto</h1>

		<!-- mvcUrl("controller#metodo").build() -->
		<!-- Somente com as iniciais maiúsculas, pois assim -->
		<!-- o Spring encontra de qual classe tu ta falando. -->
		<!-- O erro n .build() é do Eclipse. O código funciona corretamente -->
		<form:form action="${s:mvcUrl('PC#grava').build()}" method="POST" commandName="produto" enctype="multipart/form-data">
			<!-- No commandName tu define que todos os path apontam pra "produto" -->

			<!-- Receber os valores da tela -->
			<!-- O Spring faz o bind automatico entre os campos com nome "name" e os campos do Model -->
			<!-- contando que esses campos estejam EXATAMENTE com o mesmo nome -->
			<div class="form-group">
				<!-- Ao usar o Form do Spring, não precisa mais de type nem name -->
				<!-- Somente path -->
				<label>Titulo</label>
				<form:input path="titulo" cssClass="form-control"/>
				<form:errors path="titulo" />
			</div>
			<div class="form-group">
				<label>Descricao</label>
				<form:textarea path="descricao" cssClass="form-control" />
				<form:errors path="descricao" />
			</div>
			<div class="form-group">
				<label>Paginas</label>
				<form:input path="paginas" cssClass="form-control"/>
				<form:errors path="paginas" />
			</div>
			<div class="form-group">
				<label>Data de Lançamento</label>
				<form:input path="dataLancamento" cssClass="form-control"/>
				<form:errors path="dataLancamento" />
			</div>

			<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
				<div class="form-group">
					<label>${tipoPreco}</label>
					<form:input path="precos[${status.index}].valor" cssClass="form-control"/>
					<form:hidden path="precos[${status.index}].tipo"
						value="${tipoPreco}" />
				</div>
			</c:forEach>

			<div class="form-group">
				<label>Sumário</label> <input name="sumario" type="file" class="form-control"/>
			</div>

			<button type="submit" class="btn btn-primary">Cadastrar</button>

		</form:form>
	
	</div>
	
</body>
</html>