package nz.co.warehouseandroidtest.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Product(
    @SerializedName("Class0")
    var class0: String?,
    @SerializedName("Price")
    var price: @RawValue Price?,
    @SerializedName("Barcode")
    var barcode: String?,
    @SerializedName("ItemDescription")
    var itemDescription: String?,
    @SerializedName("DeptID")
    var deptID: String?,
    @SerializedName("SubClass")
    var subClass: String?,
    @SerializedName("Class0ID")
    var class0ID: String?,
    @SerializedName("SubDeptID")
    var subDeptID: String?,
    @SerializedName("Description")
    var description: String?,
    @SerializedName("BranchPrice")
    var branchPrice: String?,
    @SerializedName("ItemCode")
    var itemCode: String?,
    @SerializedName("SubDept")
    var subDept: String?,
    @SerializedName("ClassID")
    var classID: String?,
    @SerializedName("ImageURL")
    var imageURL: String?,
    @SerializedName("Dept")
    var dept: String?,
    @SerializedName("SubClassID")
    var subClassID: String?,
    @SerializedName("Class")
    var classProduct: String?,
    @SerializedName("ProductKey")
    var productKey: String?,
) : Parcelable