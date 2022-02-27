package com.example.apartments

class ApartmentDTO {
    //must have
    var CNT_NAME = ""
    //must have
    var CITY = ""
    var DESCRIPTION = ""
    var LANDLORD_EMAIL = ""
    var LANDLORD_NAME = ""
    var LANDLORD_PHONE = ""
    var PLACES_NUM = 0
    var ST_NAME = ""
    var ST_NUM = ""
    var APT_NUM = ""
    var ZIP = ""

    //don't have to be filled
    var ApartmentId = ""
    var StartTime =  ""
    var VOLUNTEER_NAME = ""
    var IS_BUSY = false
    var IS_VERIFIED = false

    fun userFriendlyString(): String {
        val sb = StringBuilder()
        sb.append("działa").append(ZIP).append(" ").append(CITY).append(", ").append(ST_NAME).append(" ").append(ST_NUM)
        return sb.toString()
    }//TODO display

}