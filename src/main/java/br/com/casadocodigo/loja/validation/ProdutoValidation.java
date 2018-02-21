package br.com.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.model.Produto;

public class ProdutoValidation implements Validator { // É o Validator do Spring!!

	// Pessoal coloca com z pq class eh palavra reservada do java.
	@Override
	public boolean supports(Class<?> clazz) {
		
		// O que faz esse método:
		// Se o Produto é validado e pode ser aceito através dessa classe que
		// ele recebe por parâmetro. Só assim ele vai saber se ele pode 
		// chamar o validator que definimos para o nosso objeto Produto.
		// Em resumo: essa classe recebida por parâmetro é um Produto?
		// Se sim, ele pode usar o validator definido no @Valid e no initBinder.
		return Produto.class.isAssignableFrom(clazz);
	}

	// Dentro do método validate da interface Validator tu coloca
	// os testes que tu quer realizar, utilizando o objeto genérico target
	// que tu quer validar, e colocando as infos no objeto Errors
	@Override
	public void validate(Object target, Errors errors) {
		// O Spring que vai dizer se deu erro ou não. Não é necessário retornar
		// nada.
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");

		// Como o target é um objeto Genérico, tem que criar um produto com as infos dele.
		Produto produto = (Produto) target;
		
		// Adiciona todos os erros dentro do objeto Errors que o Spring está
		// passando.
		if (produto.getPaginas() <= 0) {
			errors.rejectValue("paginas", "field.required");
		}

		// O Spring usa os objetos BeanValidation do java, por isso tem que
		// importar ele no pom e utilizar na classe controller.

	}
}
