package igor.petrov.jetpack_compose.entity

interface Episode {
    val id:Int
    val name:String?
    val airDate:String?
    val episode:String?
    val characters:List<String>?
    val url:String?
    val created:String?
}