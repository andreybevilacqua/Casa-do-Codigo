<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
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
	<!-- Criando o cabeçalho/menu de cima da tela -->
	<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${s:mvcUrl('HC#index').build() }">Casa do Código</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="${s:mvcUrl('PC#listar').build() }">Lista de Produtos</a></li>
				<li><a href="${s:mvcUrl('PC#form').build() }">Cadastro de Produtos</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#"> <!-- principal = nome padrão que todos os frameworks de seguranca usam para dizer -->
								<!-- esse é o usuário logado atualmente -->
					<security:authentication property="principal" var="usuario"/>
					Usuário: ${usuario.username }
				</a></li>
			</ul>
		</div>
	</div>
	</nav>

	<div class="container">

		<h1>Lista de Produtos</h1>

		<table class="table table-bordered table-striped table-condensed">
			<tr>
				<!-- Os titulos da tabela -->
				<th>Título</th>
				<th>Descrição</th>
				<th>Preços</th>
				<th>Páginas</th>
			</tr>
			<!-- As infos de cada campo da tabela: busca no banco e faz um loop pra imprimir eles-->
			<c:forEach items="${produtos}" var="produto">
				<tr>
					<td><a
						href="${s:mvcUrl('PC#detalhe').arg(0,produto.id).build() }">
							${produto.titulo} </a></td>
					<td>${produto.descricao}</td>
					<td>${produto.precos }</td>
					<td>${produto.paginas}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>