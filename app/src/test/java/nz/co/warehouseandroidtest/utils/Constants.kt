package nz.co.warehouseandroidtest.utils

import nz.co.warehouseandroidtest.models.*

object Constants {

    private const val USER_ID = "3F06B8D9-1922-4578-BD29-A9B3B9C6F581"

    fun getUser(): User {
        return User("Prod", USER_ID)
    }

    fun getProductDetail(): ProductDetail {
        val price: Price = Price("99", "NAT")
        val product: Product = Product(
            "Sporting",
            price,
            "9401063534012",
            "",
            "06072",
            "Kids Bikes",
            "09937",
            "6245",
            "Milazo Moto Balance Bike",
            "99",
            "",
            "Bikes",
            "6827",
            "https://twg.azure-api.net/twlProductImage/productImage/9401063534012/format/png/size/small",
            "Wheels",
            "2821",
            "Kids Bikes",
            "2682834"
        )
        return ProductDetail(
            "1234567890",
            "S",
            "9401063534012",
            "1867868",
            "",
            product,
            "Prod",
            "2021-06-14T00:03:55",
            "Y",
            "248E2B91-8133-4CDC-A6DC-6924685E1156",
            ""
        )
    }

    fun getSearchResult(): SearchResult {
        return SearchResult("307", null, "39690", "Prod", "Y")
    }




}