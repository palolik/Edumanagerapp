package com.kotlin5.edumanager.data

data class CourseModel (
    val _id : String?,
    val title : String?,
val image : String?
,val price : String?
,val description : String?
,val userEmail : String?
,val userName : String?
,val totalenrolment : String?
    , val status : String?


)

data class Course(
    val title: String,
    val description: String,
    val image: String,
    val price: String,
    val userEmail: String,
    val userName: String,
    val totalenrolment: String,
    val status: String
)

data class CourseDetailsResponse(
    val title: String,
    val description: String,
    val image: String,
    val price: String,
    val userEmail: String,
    val userName: String,
    val totalenrolment: String,
    val status: String
)
data class Partner(
    val logo: String,
    val caption: String
)

data class Feedback(
    val rating: String,
    val feedback: String,
    val userEmail: String
)


