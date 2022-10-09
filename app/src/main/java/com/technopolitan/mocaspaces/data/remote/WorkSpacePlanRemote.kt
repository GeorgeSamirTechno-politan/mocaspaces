package com.technopolitan.mocaspaces.data.remote

import android.content.Context
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.shared.PriceResponse
import com.technopolitan.mocaspaces.models.workSpace.PlanResponse
import com.technopolitan.mocaspaces.models.workSpace.PlanTypeResponse
import com.technopolitan.mocaspaces.models.workSpace.WorkSpacePlanMapper
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.BookingType
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import com.technopolitan.mocaspaces.utilities.formatPrice
import io.reactivex.Flowable
import javax.inject.Inject

class WorkSpacePlanRemote @Inject constructor(
    private var networkModule: NetworkModule,
    private var context: Context
) :
    BaseRemote<List<WorkSpacePlanMapper>, List<PlanTypeResponse>>() {
    private lateinit var priceResponse: PriceResponse
    private lateinit var currency: String

    fun getWorkSpacePlans(
        priceResponse: PriceResponse,
        currency: String
    ): SingleLiveEvent<ApiStatus<List<WorkSpacePlanMapper>>> {
        this.priceResponse = priceResponse
        this.currency = currency
        return handleApi()
    }

    override fun flowable(): Flowable<HeaderResponse<List<PlanTypeResponse>>> =
        networkModule.provideService(BaseUrl.setting).getPlanTypesWithRelatedData()

    override fun handleResponse(it: HeaderResponse<List<PlanTypeResponse>>): ApiStatus<List<WorkSpacePlanMapper>> {
        if (it.succeeded) {
            val list = mutableListOf<WorkSpacePlanMapper>()
            it.data?.let {
                it.forEach { item ->
                    when (item.name.lowercase()) {
                        "Hourly" -> {
                            priceResponse.hourly.let { price ->
                                list.add(
                                    handleWorkSpaceTypeMapper(
                                        price,
                                        item.planResponse,
                                        BookingType.hourlyTypeId
                                    )
                                )
                            }

                        }
                        "Day" -> {
                            priceResponse.day.let { price ->
                                list.add(
                                    handleWorkSpaceTypeMapper(
                                        price,
                                        item.planResponse,
                                        BookingType.dayPassTypeId
                                    )
                                )
                            }

                        }
                        "Tailored" -> {
                            priceResponse.tailored.let { price ->
                                list.add(
                                    handleWorkSpaceTypeMapper(
                                        price,
                                        item.planResponse,
                                        BookingType.tailoredTypeId
                                    )
                                )
                            }

                        }
                        "Bundle" -> {
                            priceResponse.bundle.let { price ->
                                list.add(
                                    handleWorkSpaceTypeMapper(
                                        price,
                                        item.planResponse,
                                        BookingType.bundleTypeId
                                    )
                                )
                            }

                        }
                        "PrivateOffice" -> {
                            priceResponse.privateOffice.let { price ->
                                list.add(
                                    handleWorkSpaceTypeMapper(
                                        price,
                                        item.planResponse,
                                        BookingType.privateOfficeTypeId
                                    )
                                )
                            }

                        }
                    }
                }
            }
            return SuccessStatus(it.message, list)
        } else return FailedStatus(it.message)
    }

    /**
     *
     * @param planTypeId
     * 1 hourly
     * 2 day pass
     * 3 tailored
     * 4 bundle
     * 5 private office
     * @sample WorkSpacePlanMapper
     */
    private fun handleWorkSpaceTypeMapper(
        price: Double,
        planResponse: PlanResponse,
        planTypeId: Int
    ): WorkSpacePlanMapper {
        return when (planTypeId) {
            BookingType.hourlyTypeId -> {
                WorkSpacePlanMapper(
                    planId = BookingType.hourlyTypeId,
                    mocaMColorResId = R.color.accent_color,
                    backGroundColorResId = R.color.workspace_color,
                    planTypeText = context.getString(R.string.hourly),
                    planTypeTextColorResId = R.color.white,
                    animatedRawResId = R.raw.hourly_glass,
                    useDownAnimated = false,
                    planDescText = planResponse.description,
                    planDescColorResId = R.color.light_black_color,
                    priceText = formatPrice(price),
                    priceTextColorResId = R.color.light_black_color,
                    planBtnColorResId = R.color.accent_color,
                    planBtnTextColorResId = R.color.white,
                    planTermsOfUseHtml = planResponse.termsOfUse,
                    currency = currency,
                    pricePerText = "/${context.getString(R.string.hour)}"
                )
            }
            BookingType.dayPassTypeId -> {
                WorkSpacePlanMapper(
                    planId = BookingType.dayPassTypeId,
                    mocaMColorResId = R.color.workspace_color,
                    backGroundColorResId = R.color.dark_green_color,
                    planTypeText = context.getString(R.string.day_pass),
                    planTypeTextColorResId = R.color.white,
                    animatedRawResId = R.raw.day_pass,
                    useDownAnimated = true,
                    planDescText = planResponse.description,
                    planDescColorResId = R.color.white,
                    priceText = formatPrice(price),
                    priceTextColorResId = R.color.white,
                    planBtnColorResId = R.color.white,
                    planBtnTextColorResId = R.color.accent_color,
                    planTermsOfUseHtml = planResponse.termsOfUse,
                    currency = currency,
                    pricePerText = "/${context.getString(R.string.day)}"
                )
            }
            BookingType.tailoredTypeId -> {
                WorkSpacePlanMapper(
                    planId = BookingType.tailoredTypeId,
                    mocaMColorResId = R.color.accent_color,
                    backGroundColorResId = R.color.tailored_plan_color,
                    planTypeText = context.getString(R.string.tailored),
                    planTypeTextColorResId = R.color.white,
                    animatedRawResId = R.raw.tailored,
                    useDownAnimated = false,
                    planDescText = planResponse.description,
                    planDescColorResId = R.color.light_black_color,
                    priceText = formatPrice(price),
                    priceTextColorResId = R.color.light_black_color,
                    planBtnColorResId = R.color.accent_color,
                    planBtnTextColorResId = R.color.white,
                    planTermsOfUseHtml = planResponse.termsOfUse,
                    currency = currency,
                    pricePerText = "/${context.getString(R.string.hour)}"
                )
            }
            BookingType.bundleTypeId -> {
                WorkSpacePlanMapper(
                    planId = BookingType.bundleTypeId,
                    mocaMColorResId = R.color.accent_color,
                    backGroundColorResId = R.color.bundle_plan_color,
                    planTypeText = context.getString(R.string.bundle),
                    planTypeTextColorResId = R.color.white,
                    animatedRawResId = R.raw.tailored,
                    useDownAnimated = false,
                    planDescText = planResponse.description,
                    planDescColorResId = R.color.light_black_color,
                    priceText = formatPrice(price),
                    priceTextColorResId = R.color.light_black_color,
                    planBtnColorResId = R.color.accent_color,
                    planBtnTextColorResId = R.color.white,
                    planTermsOfUseHtml = planResponse.termsOfUse,
                    currency = currency,
                    pricePerText = "/${context.getString(R.string.hour)}"
                )
            }
            else -> {
                WorkSpacePlanMapper(
                    planId = BookingType.privateOfficeTypeId,
                    mocaMColorResId = R.color.workspace_color,
                    backGroundColorResId = R.color.accent_color,
                    planTypeText = context.getString(R.string.private_office_with_new_line),
                    planTypeTextColorResId = R.color.white,
                    animatedRawResId = R.raw.hourly_glass,
                    useDownAnimated = false,
                    planDescText = planResponse.description,
                    planDescColorResId = R.color.white,
                    priceText = formatPrice(price),
                    priceTextColorResId = R.color.white,
                    planBtnColorResId = R.color.white,
                    planBtnTextColorResId = R.color.accent_color,
                    planTermsOfUseHtml = planResponse.termsOfUse,
                    currency = currency,
                    pricePerText = "/${context.getString(R.string.hour)}"
                )
            }
        }
    }
}