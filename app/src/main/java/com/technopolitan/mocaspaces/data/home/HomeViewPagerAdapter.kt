package com.technopolitan.mocaspaces.data.home


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.technopolitan.mocaspaces.ui.home.bizLounge.BizLoungeFragment
import com.technopolitan.mocaspaces.ui.home.eventSpace.EventSpaceFragment
import com.technopolitan.mocaspaces.ui.home.meetingSpace.MeetingRoomFragment
import com.technopolitan.mocaspaces.ui.home.workSpace.WorkSpaceFragment
import javax.inject.Inject

class HomeViewPagerAdapter @Inject constructor(
    private var context: Context,
    private var fragment: Fragment?
) : FragmentStateAdapter(fragment!!) {
    private val fragmentList: MutableList<Fragment> = mutableListOf()

    init {
        fragmentList.add(WorkSpaceFragment())
        fragmentList.add(MeetingRoomFragment())
        fragmentList.add(EventSpaceFragment())
        fragmentList.add(BizLoungeFragment())
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }


}