package com.example.antkotlinproject.base

import com.example.antkotlinproject.data.model.CategoryModel
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.data.model.SubcategoryModel
import com.example.antkotlinproject.data.model.User
import java.io.File

sealed class BaseEvent

sealed class CategoryEvent : BaseEvent() {
    class CategoryFetched(val array: MutableList<CategoryModel>?) : CategoryEvent()
    class SubCategoryFetched(val item: CategoryModel?) : CategoryEvent()
}

sealed class CourseEvent : BaseEvent() {
    class CoursesFetched(val array: MutableList<CourseModel>?) : CourseEvent()
    class CourseFetched(val item: CourseModel?) : CourseEvent()
    class SubcategoryCoursesFetched(val item: SubcategoryModel?) : CourseEvent()
}

sealed class ProfileEvent : BaseEvent() {
    class UserProfileFetched(val item: User?) : ProfileEvent()
    class TeacherProfileFetched(val item: User?) : ProfileEvent()
    class UserIsStuffFetched(val item: User) : ProfileEvent()

    class UserProfileEdited(val item: User?) : ProfileEvent()
    class UserAvatarChanged(val item: User) : ProfileEvent()
}

