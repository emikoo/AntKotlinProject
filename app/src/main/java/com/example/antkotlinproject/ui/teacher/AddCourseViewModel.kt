package com.example.antkotlinproject.ui.teacher

import com.example.antkotlinproject.base.*
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.repository.CourseRepository
import com.example.antkotlinproject.ui.profile.ProfileViewModel.Companion.AVATAR
import com.example.antkotlinproject.ui.user.search.main.SearchViewModel
import com.example.antkotlinproject.utils.toImageRequestBody
import com.example.antkotlinproject.utils.toVideoRequestBody
import okhttp3.MultipartBody
import java.io.File

class AddCourseViewModel(private val repository: CourseRepository): BaseViewModel<BaseEvent>() {

    init {
        fetchCategory()
    }

    private fun fetchCategory() {
        loading.value = true
        disposable.add(
            repository.fetchCategory()
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CategoryEvent.CategoriesFetched(it) },
                    { message.value = it.message })
        )
    }

    fun fetchSubcategory(categoryId: Int) {
        loading.value = true
        disposable.add(
            repository.fetchSubcategory(categoryId)
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CategoryEvent.SubCategoryFetched(it) },
                    { message.value = it.message })
        )
    }

    fun createCourse(name: String, description: String, categoryId: Int,
                     lessonsCount: Int, price: Double, previewImage: File) {
        loading.value = true
        disposable.add(
            repository.createCourse(name, description, categoryId, lessonsCount, price,
                previewImage.toImageRequestBody(
                PREVIEW_IMAGE))
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CourseEvent.CourseCreated(it) },
                    { message.value = it.message }
                )
        )
    }

    companion object {
        const val PREVIEW_IMAGE = "preview_image"
        const val VIDEO = "video"

    }
}