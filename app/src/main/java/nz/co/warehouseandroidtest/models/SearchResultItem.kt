package nz.co.warehouseandroidtest.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class SearchResultItem(
    @SerializedName("Description")
    var description: String?,
    @SerializedName("Products")
    var products: @RawValue List<ProductWithoutPrice>?
) : Parcelable