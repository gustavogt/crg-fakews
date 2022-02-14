package br.gov.cgu.crg.fakews;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@RestController
public class FakewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FakewsApplication.class, args);
	}

	@ApiIgnore
	@GetMapping("/")
	public String home() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ' ' HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());

		return String.format("Olá CGM!!!! <br><br>" + 
		                     "Para acessar a documentação da API REST, clique <a href='/swagger-ui.html'>aqui</a>.<br><br>" + formatter.format(date));
	}

	@GetMapping(value = "/download-punidos", produces = "text/csv")
	public @ResponseBody byte[] downloadPunidos() throws IOException {
		String file = "src/punidos.csv";
		Path path = Paths.get(file);
		return Files.readAllBytes(path);
	}

	@PostMapping("/upload-punidos")
	public String uploadPunidos(@RequestParam("file") MultipartFile file) throws IOException {
		String pathStr = "src/punidos.csv";
		Path path = Paths.get(pathStr);
		Files.write(path, file.getBytes());

		return "Upload realizado com sucesso! Para baixar o arquivo, acesse: /download-punidos";
	}
}
