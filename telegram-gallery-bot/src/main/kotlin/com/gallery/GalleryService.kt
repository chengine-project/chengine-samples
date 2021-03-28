package com.gallery

import org.springframework.stereotype.Service

data class GalleryPhoto(
    val id: Int,
    val image: String?,
    val caption: String?,
    val nextId: Int?,
    val previousId: Int?
)

@Service
class GalleryService {

    fun getPhoto(id: Int? = null): GalleryPhoto? {
        TODO()
    }

}