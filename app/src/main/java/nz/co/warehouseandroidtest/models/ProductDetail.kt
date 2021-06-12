package nz.co.warehouseandroidtest.models

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

data class ProductDetail(
    @SerializedName("MachineID")
    var machineID: String?,
    @SerializedName("Action")
    var action: String?,
    @SerializedName("ScanBarcode")
    var scanBarcode: String?,
    @SerializedName("ScanID")
    var scanID: String?,
    @SerializedName("UserDescription")
    var userDescription: String?,
    @SerializedName("Product")
    var product: @RawValue Product?,
    @SerializedName("ProdQAT")
    var prodQAT: String?,
    @SerializedName("ScanDateTime")
    var scanDateTime: String?,
    @SerializedName("Found")
    var found: String?,
    @SerializedName("UserID")
    var userID: String?,
    @SerializedName("Branch")
    var branch: String?
)