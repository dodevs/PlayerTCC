package lastAPI.calls;

import java.util.Map;

import models.track.search.TrackSearch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface Track_Search {
	String url_base = "http://ws.audioscrobbler.com/2.0/?"; //Url principal da api
	@GET(url_base)//Vai user o método GET nessa usrl
	Call<TrackSearch> track_search(@QueryMap Map<String, String> options); //Passando esses parametros para a chamada

}
