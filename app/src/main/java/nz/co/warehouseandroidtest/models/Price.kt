package nz.co.warehouseandroidtest.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Price(
    @SerializedName("Price")
    var price: String?,
    @SerializedName("Type")
    var type: String?
) : Parcelable