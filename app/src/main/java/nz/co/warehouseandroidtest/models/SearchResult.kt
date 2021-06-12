package nz.co.warehouseandroidtest.models

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

data class SearchResult(
    @SerializedName("HitCount")
    var hitCount: String?,
    @SerializedName("Results")
    var results: @RawValue List<SearchResultItem>?,
    @SerializedName("SearchID")
    var searchID: String?,
    @SerializedName("ProdQAT")
    var prodQAT: String?,
    @SerializedName("Found")
    var found: String?
)