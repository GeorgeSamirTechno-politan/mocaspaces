package com.technopolitan.mocaspaces.modules

import android.content.Context
import android.view.View
import com.skydoves.powermenu.CircularEffect
import com.skydoves.powermenu.CustomPowerMenu
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.data.customPowerMenu.CustomIconMenuAdapter
import dagger.Module
import javax.inject.Inject

@Module
class PowerMenuModule @Inject constructor(
    private var context: Context,
    private var customIconMenuAdapter: CustomIconMenuAdapter
) {
    private lateinit var callBack: (entity: DropDownMapper) -> Unit
    private lateinit var powerMenu: CustomPowerMenu<DropDownMapper, CustomIconMenuAdapter>
    private lateinit var dropDownList: List<DropDownMapper>


    fun init(callBack: (entity: DropDownMapper) -> Unit, dropDownList: List<DropDownMapper>) {
        this.dropDownList = dropDownList
        this.callBack = callBack
        powerMenu = CustomPowerMenu.Builder<DropDownMapper, CustomIconMenuAdapter>(
            context,
            customIconMenuAdapter
        )
            .addItemList(dropDownList)
            .setAnimation(MenuAnimation.FADE)
            .setMenuRadius(context.resources.getDimension(com.intuit.sdp.R.dimen._5sdp))
//            .setMenuShadow(context.resources.getDimension(com.intuit.sdp.R.dimen._3sdp))
            .setOnMenuItemClickListener(onItemClickListen)
            .setMenuShadow(10f)
            .setAutoDismiss(false)
            .setIsClipping(true)
            .setDismissIfShowAgain(true)
            .setIsMaterial(true)
            .setFocusable(true)
            .setAutoDismiss(false)
            .setPadding(0)
            .setShowBackground(false)
            .setWidth(250)
            .build()
    }

    fun show(showedUnderView: View){
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            powerMenu.menuListView.setEdgeEffectColor(context.getColor(R.color.accent_color))
            powerMenu.menuListView.outlineAmbientShadowColor = context.getColor(R.color.accent_color)
            powerMenu.menuListView.outlineSpotShadowColor = context.getColor(R.color.accent_color)
        }*/
        powerMenu.circularEffect = CircularEffect.BODY
        powerMenu.showAsDropDown(showedUnderView)
    }

    fun isShown(): Boolean = powerMenu.isShowing

    fun dismiss() = powerMenu.dismiss()


    private val onItemClickListen: OnMenuItemClickListener<DropDownMapper> =
        OnMenuItemClickListener<DropDownMapper> { position, item ->
            run {
                dropDownList.forEach { item ->
                    item.selected = false
                }
                dropDownList[position].selected = true
                callBack(item)
                powerMenu.dismiss()
            }
        }

}


