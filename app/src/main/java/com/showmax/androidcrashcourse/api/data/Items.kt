import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Items (

	@SerializedName("id") val id : String,
	@SerializedName("year") val year : Int,
	@SerializedName("provider") val provider : String,
	@SerializedName("videos") val videos : List<Videos>,
	@SerializedName("type") val type : String,
	@SerializedName("categories") val categories : List<Categories>,
	@SerializedName("description") val description : String,
	@SerializedName("audio_languages") val audio_languages : List<Audio_languages>,
	@SerializedName("showmax_rating") val showmax_rating : String,
	@SerializedName("cast") val cast : List<Cast>,
	@SerializedName("slug") val slug : String,
	@SerializedName("metadata_direction") val metadata_direction : String,
	@SerializedName("metadata_language") val metadata_language : Metadata_language,
	@SerializedName("title") val title : String,
	@SerializedName("age_range_min") val age_range_min : Int,
	@SerializedName("coming_soon") val coming_soon : String,
	@SerializedName("crew") val crew : List<Crew>,
	@SerializedName("website_url") val website_url : String,
	@SerializedName("link") val link : String,
	@SerializedName("coming_soon_when") val coming_soon_when : String,
	@SerializedName("vod_model") val vod_model : String,
	@SerializedName("images") val images : List<Image>,
	@SerializedName("has_download_policy") val has_download_policy : Boolean,
	@SerializedName("valid_subscription_statuses") val valid_subscription_statuses : List<String>,
	@SerializedName("rating") val rating : Rating,
	@SerializedName("section") val section : String,
	@SerializedName("hero_position") val hero_position : Hero_position,
	@SerializedName("age_range_max") val age_range_max : Int
)