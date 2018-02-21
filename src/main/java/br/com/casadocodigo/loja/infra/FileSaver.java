package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // Ele é um componente genérico, por isso @Component.
public class FileSaver {

	@Autowired // Spring, injeta o request para mim, por favor.
	private HttpServletRequest request;
	
	private String realPath;
	private String path;
	
	public String write(String baseFolder, MultipartFile file){
		try {
			realPath = request.getServletContext().getRealPath("/" + baseFolder);
			path = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(path));
			
			return baseFolder + "/" + file.getOriginalFilename();
			
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		} catch (FileAlreadyExistsException e){
			try {
				file.transferTo(new File(path));
			} catch (IllegalStateException | IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e){
			throw new RuntimeException(e);
		}
		
		return baseFolder + "/" + file.getOriginalFilename();
	}
}
