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
    val _id : String,
    val title: String,
    val description: String,
    val image: String,
    val price: String,
    val userEmail: String,
    val userName: String,
    val totalenrolment: String,
    val status: String
)


