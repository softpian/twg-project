package nz.co.warehouseandroidtest.data

class SearchResult {
    @JvmField
    var HitCount: String? = null
    @JvmField
    var Results: List<SearchResultItem>? = null
    var SearchID: String? = null
    var ProdQAT: String? = null
    @JvmField
    var Found: String? = null
}