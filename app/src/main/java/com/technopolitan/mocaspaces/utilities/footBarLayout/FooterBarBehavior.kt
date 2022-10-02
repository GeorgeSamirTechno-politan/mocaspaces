package com.technopolitan.mocaspaces.utilities.footBarLayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

import com.google.android.material.appbar.AppBarLayout


class FooterBarBehavior : CoordinatorLayout.Behavior<FooterBarLayout?> {
    //Required to instantiate as a default behavior
    constructor()

    //Required to attach behavior via XML
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    //This is called to determine which views this behavior depends on
    fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: FooterBarLayout,
        dependency: View?
    ): Boolean {
        //We are watching changes in the AppBarLayout
        return dependency is AppBarLayout
    }

    //This is called for each change to a dependent view
    fun onDependentViewChanged(
        parent: CoordinatorLayout?,
        child: FooterBarLayout,
        dependency: View
    ): Boolean {
        val offset: Int = -dependency.top
        child.translationY = offset.toFloat()
        return true
    }
}