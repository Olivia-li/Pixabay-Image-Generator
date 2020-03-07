package com.example.docusign.model

import org.json.JSONException
import org.json.JSONObject

class PhotoGenerator {
    companion object {
        var photosList = ArrayList<Photo>()
        var hitNumber = 0

        fun parseJSON(photoData: String) {
            try {
                val obj = JSONObject(photoData)
                hitNumber = obj.getString("totalHits").toInt()
                val photoJSON = obj.getJSONArray("hits")

                for (i in 0 until photoJSON.length()) {
                    var photo = photoJSON.getJSONObject(i)
                    val id = photo.getString("id")
                    val largeImageURL = photo.getString("largeImageURL")
                    val likes = photo.getString("likes")
                    val user = photo.getString("user")

                    var currPhoto = Photo(
                        id,
                        largeImageURL,
                        likes,
                        user
                    )
                    photosList.add(currPhoto)
                }
            } catch (e: JSONException) {
                e.printStackTrace();
            }
        }

        fun clearPhotos() {
            photosList.clear()
        }
    }

}