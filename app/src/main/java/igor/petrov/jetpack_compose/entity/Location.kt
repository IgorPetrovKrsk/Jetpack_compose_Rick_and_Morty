package igor.petrov.jetpack_compose.entity

interface Location {
    val id:Int
    val name:String?
    val type:String?
    val dimension:String?
    val residents:List<String>?
    val url:String?
    val created:String?
}