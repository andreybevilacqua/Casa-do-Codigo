package br.com.casadocodigo.loja.controller;

import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class BrincandoComControllers {

	@AfterTransaction
	public void imprimeUmaMensagem() {
		System.out.println("Se tu pode usar o @ControllerAdvice para monitorar erros em controllers");
		System.out.println("Tu pode usar ele para monitorar/escutar qualquer coisa em controllers!");
	}
}
