package com.example.antkotlinproject.ui.teacher

import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.databinding.ActivityMainTeacherBinding
import com.example.antkotlinproject.ui.auth.DefaultViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MainTeacherActivity : BaseActivity<DefaultViewModel, ActivityMainTeacherBinding>(
    DefaultViewModel::class
) {
    override fun getViewBinding() = ActivityMainTeacherBinding.inflate(layoutInflater)

    override fun setupViews() {
        setupBottomView()
        setupFab()
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            val bottomSheetDialogFragment: BottomSheetDialogFragment =
                AddCourseBottomSheetFragment()
            bottomSheetDialogFragment.isCancelable = true
            bottomSheetDialogFragment.show(
                supportFragmentManager,
                bottomSheetDialogFragment.tag
            )
        }
    }

    private fun setupBottomView() {
        binding.bottomNavigationTeacher.background = null
        val navController =
            Navigation.findNavController(this, R.id.main_teacher_nav_view)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation_teacher)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun subscribeToLiveData() {}

}