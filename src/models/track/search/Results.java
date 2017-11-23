package models.track.search;

import com.google.gson.annotations.SerializedName;

public class Results {
	@SerializedName("opensearch:totalResults")
	public String opensearc_totalResults;
	public Trackmatches trackmatches;

}
