package com.example.antkotlinproject.ui.teacher

import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.databinding.ActivityStudyCourseBinding
import com.example.antkotlinproject.ui.auth.DefaultViewModel
import com.example.antkotlinproject.ui.teacher.homeworks.HomeworksFragment
import com.example.antkotlinproject.ui.teacher.lessons.LessonsFragment
import com.google.android.material.tabs.TabLayout

class StudyCourseActivity : BaseActivity<DefaultViewModel, ActivityStudyCourseBinding>(
    DefaultViewModel::class
) {

    lateinit var adapter: MainViewPagerAdapter

    override fun getViewBinding() = ActivityStudyCourseBinding.inflate(layoutInflater)

    override fun setupViews() {
        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        adapter = MainViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(LessonsFragment(), "Лента")
        adapter.addFragment(HomeworksFragment(), "Задания")
        binding.viewPager.adapter = adapter
    }

    private fun setupTabLayout() {
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }

        })
    }

    override fun subscribeToLiveData() {
    }
}