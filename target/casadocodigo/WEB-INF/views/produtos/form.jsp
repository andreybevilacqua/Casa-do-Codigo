<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Livros de Java, iPhone, Ruby, PHP e muito mais - Casa do
	Código</title>
</head>
<body>
	<!-- mvcUrl("controller#metodo").build() -->
	<!-- Somente com as iniciais maiúsculas, pois assim -->
	<!-- o Spring encontra de qual classe tu ta falando. -->
	<!-- O erro n .build() é do Eclipse. O código funciona corretamente -->
	<form:form action="${s:mvcUrl('PC#grava').build()}" method="POST"
		commandName="produto" enctype="multipart/form-data">
		<!-- No commandName tu define que todos os path apontam pra "produto" -->
		
		<!-- Receber os valores da tela -->
		<!-- O Spring faz o bind automatico entre os campos com nome "name" e os campos do Model -->
		<!-- contando que esses campos estejam EXATAMENTE com o mesmo nome -->
		<div>
			<!-- Ao usar o Form do Spring, não precisa mais de type nem name -->
			<!-- Somente path -->
			<label>Titulo</label>
			<form:input path="titulo" />
			<form:errors path="titulo" />
		</div>
		<div>
			<label>Descricao</label>
			<form:textarea path="descricao" rows="10" cols="20" />
			<form:errors path="descricao" />
		</div>
		<div>
			<label>Paginas</label>
			<form:input path="paginas" />
			<form:errors path="paginas" />
		</div>
		<div>
			<label>Data de Lançamento</label>
			<form:input path="dataLancamento" />
			<form:errors path="dataLancamento" />
		</div>

		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
			<div>
				<label>${tipoPreco}</label>
				<form:input path="precos[${status.index}].valor" />
				<form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}" />
			</div>
		</c:forEach>

		<div>
			<label>Sumário</label>
			<input name="sumario" type="file"/>
		</div>

		<button type="submit">Cadastrar</button>
		
	</form:form>
	
</body>
</html>