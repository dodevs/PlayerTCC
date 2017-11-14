package lastAPI;

import java.util.HashMap;
import java.util.Map;

import lastAPI.calls.Album_Info;
import lastAPI.calls.Track_Similar;
import models.album.info.Album;
import models.album.info.AlbumGetInfo;
import models.album.info.Track;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App_AlbumInfo {
	public static void main(String[] args) {
		// Constroi o objeto
				Retrofit retrofit = new Retrofit.Builder().baseUrl(Album_Info.url_base)// Olha na interface
						.addConverterFactory(GsonConverterFactory.create())// Método de "conversão" do JSON para uma classe
						.build();
				
				Map<String, String> options = new HashMap<String, String>(); //Cria um hashmap para as query's
				options.put("method","album.getInfo");
				options.put("artist", "Avenged SevenFold");
				options.put("album", "Hail to the king");
				options.put("api_key", "68bcc9cffa6a90e346de31b396b1a163");
				options.put("format", "json");
				options.put("limit", "4");
				
				Album_Info info = retrofit.create(Album_Info.class);
				Call<AlbumGetInfo> requestSimilar = info.album_info(options);
				
				requestSimilar.enqueue(new Callback<AlbumGetInfo>() {

					public void onFailure(Call<AlbumGetInfo> call, Throwable t) {
						System.out.println("erro "+t.getMessage());	
					}

					public void onResponse(Call<AlbumGetInfo> call, Response<AlbumGetInfo> r) {
						if(!r.isSuccessful()) {
							System.out.println("Erro: "+r.code());
						}
						else {
							AlbumGetInfo info = r.body();
							System.out.println(info.album.artist);
							System.out.println(info.album.name);
							for(Track track : info.album.tracks.track) {
								System.out.println(track.name);
								System.out.println(track.artist.name);
								System.out.println("\n");
							}
							
						}
					}
					
				});
	}
}
