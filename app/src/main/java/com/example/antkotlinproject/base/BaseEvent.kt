package com.example.antkotlinproject.base

import com.example.antkotlinproject.data.model.*

sealed class BaseEvent

sealed class CategoryEvent : BaseEvent() {
    class CategoriesFetched(val array: MutableList<CategoryModel>?) : CategoryEvent()
    class SubcategoriesFetched(val array: MutableList<CategoryModel>?) : CategoryEvent()
    class SubcategoryFetched(val item: CategoryModel?) : CategoryEvent()
}

sealed class CourseEvent : BaseEvent() {
    class CoursesFetched(val array: MutableList<CourseModel>?) : CourseEvent()
    class UserCoursesFetched(val array: MutableList<CourseModel>?) : CourseEvent()
    class CourseFetched(val item: CourseModel?) : CourseEvent()
    class SubcategoryCoursesFetched(val item: CategoryModel?) : CourseEvent()
    class CourseCreated(val item: CourseModel) : CourseEvent()
    class CourseAccessCreated(val item: CourseAccessModel) : CourseEvent()
}

sealed class ProfileEvent : BaseEvent() {
    class UserProfileFetched(val item: User?) : ProfileEvent()
    class TeacherProfileFetched(val item: User) : ProfileEvent()
    class UserIsStuffFetched(val item: User) : ProfileEvent()
    class UserProfileEdited(val item: User?) : ProfileEvent()
    class UserAvatarChanged(val item: User) : ProfileEvent()
}

