package ntub.imd.afinal

data class Store(
    var id: Int = 0,
    var name: String = "",
    var phone: String = "",
    var address: String = "",
    var rating: Float = 0f,
    var imageUri: String? = null,
    var isFavorite: Boolean = false
)
