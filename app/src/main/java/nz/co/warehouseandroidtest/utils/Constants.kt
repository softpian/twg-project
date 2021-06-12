package nz.co.warehouseandroidtest.utils

import nz.co.warehouseandroidtest.BuildConfig

object Constants {
    const val HTTP_URL_ENDPOINT = "https://twg.azure-api.net/"
    const val PREF_USER_ID = "userId"

    /**
     * apply workaround to get product price
     *
     * - the current error:
     * -- 500 Not Found (404) is returned from a server if 208(default value) is set as a parameter for Branch while requesting GET price API to a server
     *
     * - workaround:
     * -- if Branch parameter is empty, it works fine meaning a correct response is returned from a server
     * -- so change BRANCH_ID from 208(default value) to empty
     *
     * FIXME: this is a workaround to be changed with a correct logic
     */
    const val BRANCH_ID = ""

    /**
     * Please fill in subscriptionKey, the local.properties given to you here.
     * e.g. subscriptionKey="79a8b12dca4567891a00fad111a36a44f"
     */
    const val SUBSCRIPTION_KEY = BuildConfig.SUBSCRIPTION_KEY
    const val MACHINE_ID = "1234567890"
}