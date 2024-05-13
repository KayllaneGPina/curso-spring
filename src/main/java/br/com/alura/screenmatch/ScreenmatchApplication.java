package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverterDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("http://www.omdbapi.com/?t=narcos&apikey=9231eb0f");
//		System.out.println(json);

		ConverterDados conversor = new ConverterDados();

		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
//		System.out.println(dados);

		DadosEpisodios dadosEpisodios = conversor.obterDados(json, DadosEpisodios.class);
//		System.out.println(dadosEpisodios);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTemporadas(); i++) {
			json = consumoAPI.obterDados("http://www.omdbapi.com/?t=narcos&season=" + i + "&apikey=9231eb0f");

			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);
	}
}
