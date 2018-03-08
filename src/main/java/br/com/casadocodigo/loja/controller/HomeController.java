package br.com.casadocodigo.loja.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.dao.ProdutoDao;
import br.com.casadocodigo.loja.dao.UsuarioDao;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.Role;
import br.com.casadocodigo.loja.model.Usuario;

@Controller
public class HomeController {

	@Autowired
	private ProdutoDao produtoDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@RequestMapping("/")
	@Cacheable(value="produtosHome")
	public ModelAndView index(){
		List<Produto> produtos = produtoDao.listar();
		
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
	
	@Transactional // Só para ele abrir a sessão com o banco para inserir o usuário, apenas nesse método.
	@ResponseBody
	@RequestMapping("/url-magica-maluca-isudhfalfgas572ldvg235aldsgafd634uafg7456ldjs13hvuasdg")
	public String urlMagicaMaluca() {
		Usuario u = new Usuario();
		u.setNome("admin");
		u.setEmail("admin@casadocodigo.com.br");
		u.setRoles(Arrays.asList(new Role("ADMIN")));
		u.setSenha("$2a$10$lt7pS7Kxxe5JfP.vjLNSyOXP11eHgh7RoPxo5fvvbMCZkCUss2DGu");
		usuarioDao.gravar(u);
		
		return "Url Mágica Executada";
	}

}






