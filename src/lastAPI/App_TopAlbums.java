package lastAPI;

import java.util.HashMap;
import java.util.Map;

import lastAPI.calls.Artist_Albums;
import models.artist.albums.Album;
import models.artist.albums.ArtistGetTopAlbums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App_TopAlbums {
	public static void main(String[] args) {
		// Constroi o objeto
				Retrofit retrofit = new Retrofit.Builder().baseUrl(Artist_Albums.url_base)// Olha na interface
						.addConverterFactory(GsonConverterFactory.create())// Método de "conversão" do JSON para uma classe
						.build();
				
				Map<String, String> options = new HashMap<String, String>(); //Cria um hashmap para as query's
				options.put("method","artist.gettopalbums");
				options.put("artist", "Sam Smith");
				options.put("api_key", "68bcc9cffa6a90e346de31b396b1a163");
				options.put("format", "json");
				options.put("limit", "4");
				
				Artist_Albums albums = retrofit.create(Artist_Albums.class);
				Call<ArtistGetTopAlbums> requestSimilar = albums.artist_albums(options);
				
				requestSimilar.enqueue(new Callback<ArtistGetTopAlbums>() {

					public void onFailure(Call<ArtistGetTopAlbums> call, Throwable t) {
						System.out.println("erro "+t.getMessage());	
					}

					public void onResponse(Call<ArtistGetTopAlbums> call, Response<ArtistGetTopAlbums> r) {
						if(!r.isSuccessful()) {
							System.out.println("Erro: "+r.code());
						}
						else {
							ArtistGetTopAlbums albums = r.body();
							
							for(Album album : albums.topalbums.album) {
								System.out.println(album.name);
								System.out.println(album.artist.name);
								System.out.println(album.artist.mbid);
								System.out.println("\n");
							}
							
						}
					}
					
				});
	}
}
