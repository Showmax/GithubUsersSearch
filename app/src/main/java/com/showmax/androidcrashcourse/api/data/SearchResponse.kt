import com.google.gson.annotations.SerializedName

data class SearchResponse (

	@SerializedName("total_count") val count : Int,
	@SerializedName("items") val items : List<Item>,
	@SerializedName("incomplete_results") val incomplete : Boolean
)