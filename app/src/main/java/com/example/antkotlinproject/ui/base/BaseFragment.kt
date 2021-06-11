package com.example.antkotlinproject.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.antkotlinproject.databinding.FragmentAuthorizationBinding
import com.example.antkotlinproject.utils.viewBinding

abstract class BaseFragment(private val resID: Int) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(resID, container, false)
    }
}