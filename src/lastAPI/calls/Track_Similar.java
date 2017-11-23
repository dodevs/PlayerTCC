package lastAPI.calls;

import java.util.Map;

import models.track.similar.TrackGetSimilar;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface Track_Similar {
	String url_base = "http://ws.audioscrobbler.com/2.0/?"; //Url principal da api
	@GET(url_base)//Vai user o método GET nessa usrl
	Call<TrackGetSimilar> track_similar(@QueryMap Map<String, String> options);
}
