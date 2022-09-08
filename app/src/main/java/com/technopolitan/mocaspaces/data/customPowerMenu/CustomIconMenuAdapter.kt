package com.technopolitan.mocaspaces.data.customPowerMenu


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.skydoves.powermenu.MenuBaseAdapter
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import javax.inject.Inject


class CustomIconMenuAdapter @Inject constructor(private var glideModule: GlideModule) : MenuBaseAdapter<DropDownMapper>() {

    override fun getView(index: Int, view: View?, viewGroup: ViewGroup): View? {
        var view = view
        val context = viewGroup.context
        if (view == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.custom_power_menu_item, viewGroup, false)
        }
        val item: DropDownMapper = getItem(index) as DropDownMapper
        val imageView = view!!.findViewById<ImageView>(R.id.power_menu_image_view)
        val title = view.findViewById<TextView>(R.id.power_menu_text)
        val layout = view.findViewById<LinearLayout>(R.id.item_layout)
        glideModule.loadImage(item.image, imageView)
        title.text = item.name
        if(item.selected){
            layout.setBackgroundColor(context.getColor(R.color.accent_color_58))
            title.setTextColor(context.getColor(R.color.white))
        }else{
            layout.setBackgroundColor(context.getColor(R.color.white))
            title.setTextColor(context.getColor(R.color.black))
        }
        return super.getView(index, view, viewGroup)
    }


}