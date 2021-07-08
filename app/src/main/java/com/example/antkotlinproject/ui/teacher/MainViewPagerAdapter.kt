package com.example.antkotlinproject.ui.teacher

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val tabNames = mutableListOf<String>()
    private val fragments = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment, title: String){
        fragments.add(fragment)
        tabNames.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabNames[position]
    }
}