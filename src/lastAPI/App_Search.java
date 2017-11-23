package lastAPI;

import java.util.HashMap;
import java.util.Map;

import lastAPI.calls.Track_Search;
import models.common.Image;
import models.common.Track;
import models.track.search.TrackSearch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Hello world!
 *
 */
public class App_Search {
	public static void main(String[] args) {

		//Constroi o objeto
				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(Track_Search.url_base)//Olha na interface
						.addConverterFactory(GsonConverterFactory.create())//Método de "conversão" do JSON para uma classe
						.build();

				Map<String, String> options = new HashMap<String, String>(); //Cria um hashmap para as query's
				options.put("method", "track.search"); //Método da api, tipo, só para buscas de tracks
				options.put("track", "Planets"); //Nem precisa dizer
				options.put("api_key", "68bcc9cffa6a90e346de31b396b1a163"); //Minha key né
				options.put("limit", "5"); //Limite por pag
				options.put("format", "json"); //O formato padrao

				Track_Search search = retrofit.create(Track_Search.class); //"Instanciou uma classe do tipo LastFMSearch
				Call<TrackSearch> requestSearch = search.track_search(options); //Fez a chamada

				requestSearch.enqueue(new Callback<TrackSearch>() { //TA usando um método assincrono, já que nao usei Thread

					public void onResponse(Call<TrackSearch> call, Response<TrackSearch> r) {//Esse método trata as respostas(200,404, 500)
						if (!r.isSuccessful()) {//Se nao foi um sucesso (200)
							System.out.println("Erro: " + r.code());
						} else {
							TrackSearch search = r.body();//Aqui um objeto do tipo LastFmSearch recebe o corpo da requisição
							System.out.println(search.results.opensearc_totalResults);
							for (Track track : search.results.trackmatches.track) { //Aqui cê ja sabe, fez um "for each" bolado
								System.out.println(track.name);
								System.out.println(track.artist);
								for (Image image : track.image) { //Outro "for each" pq é uma lista
									System.out.println(image.text);
								}
							}
						}

					}

					public void onFailure(Call<TrackSearch> call, Throwable t) { //Esse método trata os erros, quando nem conecta por exemplo
						System.err.println("erro: " + t.getMessage());

					}
				});

	}
}
