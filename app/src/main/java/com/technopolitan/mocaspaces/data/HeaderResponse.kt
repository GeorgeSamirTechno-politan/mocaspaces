package com.technopolitan.mocaspaces.data

import com.google.gson.annotations.SerializedName


data class HeaderResponse<T>(
    @SerializedName("Succeeded")
    val succeeded: Boolean = false,

    @SerializedName("Message")
    val message: String = "",

    @SerializedName("Errors")
    val errors: String = "",

    @SerializedName("Data")
    val data: T? = null,

    @SerializedName("PageNumber")
    val pageNumber: Int? = null,

    @SerializedName("PageSize")
    val pageSize: Int? = null,

    @SerializedName("pg_total")
    val pageTotal: Int? = null,

)