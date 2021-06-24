package com.example.antkotlinproject.base

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

abstract class BaseFragment<ViewModel : BaseViewModel<BaseEvent>, VB_CHILD : ViewBinding>(
    private val clazz: KClass<ViewModel>
) : Fragment() {

    protected lateinit var viewModel: ViewModel
    private var _binding: VB_CHILD? = null
    lateinit var binding: VB_CHILD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = getInflatedView(inflater, container, false)

    private fun getInflatedView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        val tempList = mutableListOf<VB_CHILD>()
        attachBinding(tempList, inflater, container, attachToRoot)
        this._binding = tempList[0]
        binding = _binding as VB_CHILD
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupViews()
        subscribeToLiveData()
    }

    abstract fun setupViews()
    abstract fun subscribeToLiveData()

    private fun init() {
        getViewModel(clazz = clazz)
    }

    fun selectPicture() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(i, "Select Picture"),
            SELECT_PICTURE
        )
    }

    fun getSelectedPicture(resultCode: Int, requestCode: Int, data: Intent?, imageView: ImageView) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri: Uri? = data?.data
                if (null != selectedImageUri) {
                    Glide.with(imageView)
                        .load(selectedImageUri)
                        .into(imageView)
                }
            }
        }
    }

    companion object {
        const val SELECT_PICTURE = 200
    }

    override fun onDestroy() {
        super.onDestroy()
        this._binding = null
    }

    abstract fun attachBinding(
        list: MutableList<VB_CHILD>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    )
}