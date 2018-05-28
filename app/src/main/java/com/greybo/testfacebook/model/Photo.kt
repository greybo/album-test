package com.greybo.testfacebook.model

import com.google.gson.annotations.SerializedName
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "photo")
class Photo {

    @DatabaseField(generatedId = true)
    @SerializedName("idField")
    var idField: Int = 0

    @DatabaseField()
    @SerializedName("id")
    var id: String? = null

    @DatabaseField()
    @SerializedName("created_time")
    var created_time: String? = null

    @DatabaseField()
    @SerializedName("name")
    var name: String? = null

    @DatabaseField()
    @SerializedName("url")
    var url: String? = null

    @DatabaseField()
    @SerializedName("albumId")
    var albumId: String? = null

    fun convertToAlbum(response: PhotoResponse.Data): Photo {
        val data = response
        this.id = data.id
        this.created_time = data.created_time
        this.name = data.album?.name
        this.url = data.source
        this.albumId = data.album?.id

        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Photo

        if (id != other.id) return false
        if (created_time != other.created_time) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (created_time?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Photo(idField=$idField, id=$id, created_time=$created_time, name=$name, url=$url, albumId=$albumId)"
    }

}