package RestAPI.retrofitGson;


import java.util.HashMap;
import java.util.Map;

import main2.ControllerAudio;
import models.LastFmSearch;
import models.Track;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Hello world!
 *
 */
public class App implements Runnable {
	String name;
	public App(String i){
		name = i;
	}
	@Override
	public void run() {
		 synchronized(this){
		//Constroi o objeto
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(LastFMSearch.url_base)//Olha na interface
				.addConverterFactory(GsonConverterFactory.create())//Método de "conversão" do JSON para uam classe
				.build();

		Map<String, String> options = new HashMap<String, String>(); //Cria um hashmap para as query's
		options.put("method", new String("track.search")); //Método da api, tipo, só para buscas de tracks
		options.put("track", name); //Nem precisa dizer
		options.put("api_key", new String("68bcc9cffa6a90e346de31b396b1a163")); //Minha key né
		options.put("limit", new String("5")); //Limite por pag
		options.put("format", new String("json")); //O formato padrao

		LastFMSearch search = retrofit.create(LastFMSearch.class); //"Instanciou uma classe do tipo LastFMSearch
		Call<LastFmSearch> requestSearch = search.lastfmsearch(options); //Fez a chamada

		requestSearch.enqueue(new Callback<LastFmSearch>() { //TA usando um método assincrono, já que nao usei Thread

			public void onResponse(Call<LastFmSearch> call, Response<LastFmSearch> r) {//Esse método trata as respostas(200,404, 500)
				if (!r.isSuccessful()) {//Se nao foi um sucesso (200)
					System.out.println("Erro: " + r.code());
				} else {
					LastFmSearch search = r.body();//Aqui um objeto do tipo LastFmSearch recebe o corpo da requisição

					for (Track track : search.results.trackmatches.track) { //Aqui cê ja sabe, fez um "for each" bolado
						System.out.println(track.name);
						System.out.println(track.artist);
						System.out.println(track.image.get(3).text);
						ControllerAudio.album = (track.image.get(3).text);
						System.out.println(ControllerAudio.album);
						
					}
					
				}

			}
			
			public void onFailure(Call<LastFmSearch> call, Throwable t) { //Esse método trata os erros, quando nem conecta por exemplo
				System.err.println("erro: " + t.getMessage());

			}
		});
		
	}
		 
	}
}
