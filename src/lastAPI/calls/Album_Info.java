package lastAPI.calls;

import java.util.Map;

import models.album.info.AlbumGetInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface Album_Info {
	String url_base = "http://ws.audioscrobbler.com/2.0/?"; //Url principal da api
	@GET(url_base)
	Call<AlbumGetInfo> album_info(@QueryMap Map<String, String> options);
}
