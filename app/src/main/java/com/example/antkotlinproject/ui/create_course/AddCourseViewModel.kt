package com.example.antkotlinproject.ui.create_course

import com.example.antkotlinproject.base.*
import com.example.antkotlinproject.repository.CourseRepository
import com.example.antkotlinproject.ui.profile.ProfileViewModel.Companion.AVATAR
import com.example.antkotlinproject.utils.toImageRequestBody
import com.example.antkotlinproject.utils.toVideoRequestBody
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
                    { event.value = CategoryEvent.SubcategoryFetched(it) },
                    { message.value = it.message })
        )
    }

    fun createCourse(name: String, description: String, categoryId: Int,
                     lessonsCount: Int, price: Double, subcategoryId: Int, previewImage: File, video: File) {
        loading.value = true
        disposable.add(
            repository.createCourse(name = name, description = description, categoryId = categoryId,
                lessonsCount = lessonsCount, price = price, subcategoryId = subcategoryId,
                image = previewImage.toImageRequestBody(PREVIEW_IMAGE),
                video = video.toVideoRequestBody(PREVIEW_VIDEO))
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CourseEvent.CourseCreated(it) },
                    { message.value = it.message }
                )
        )
    }

    companion object {
        const val PREVIEW_IMAGE = "course_preview_image"
        const val PREVIEW_VIDEO = "course_preview_video"
    }
}