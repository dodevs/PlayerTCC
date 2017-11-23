package RestAPI.retrofitGson;


import java.util.Map;

import models.LastFmSearch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface LastFMSearch {
	
	String url_base = "http://ws.audioscrobbler.com/2.0/?"; //Url principal da api
	
	@GET(url_base)//Vai user o método GET nessa usrl
	Call<LastFmSearch> lastfmsearch(@QueryMap Map<String, String> options); //Passando esses parametros para a chamada

}
