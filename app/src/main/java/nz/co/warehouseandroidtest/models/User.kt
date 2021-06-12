package nz.co.warehouseandroidtest.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("ProdQAT")
    var prodQAT: String?,
    @SerializedName("UserID")
    var userID: String?
)