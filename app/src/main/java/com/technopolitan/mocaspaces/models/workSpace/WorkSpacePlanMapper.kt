package com.technopolitan.mocaspaces.models.workSpace


data class WorkSpacePlanMapper(
    val planId: Int,
    val mocaMColorResId: Int,
    val backGroundColorResId: Int,
    val planTypeText: String,
    val planTypeTextColorResId: Int,
    val animatedRawResId: Int,
    val useDownAnimated: Boolean = false,
    val planDescText: String,
    val planDescColorResId: Int,
    val priceText: String,
    val priceTextColorResId: Int,
    val planBtnColorResId: Int,
    val planBtnTextColorResId: Int,
    val planTermsOfUseHtml: String,
    val currency: String,
    val pricePerText: String,
)