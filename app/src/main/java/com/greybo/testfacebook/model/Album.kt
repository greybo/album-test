package com.greybo.testfacebook

import com.google.gson.annotations.SerializedName
import com.greybo.testfacebook.model.AlbumRasponse
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "album")
class Album {

    @DatabaseField(generatedId = true)
    @SerializedName("idField")
    var idField: Int = 0

    @DatabaseField()
    @SerializedName("id")
    var id: String? = null

    @DatabaseField()
    @SerializedName("name")
    var name: String? = null

    @DatabaseField()
    @SerializedName("created_time")
    var created_time: String? = null

    @DatabaseField()
    @SerializedName("url")
    var url: String? = null

    fun convertToAlbum(picture: AlbumRasponse.Data): Album {
        this.id = picture.id
        this.name = picture.name
        this.created_time = picture.created_time
        this.url = picture.picture?.data?.url

        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Album

        if (id != other.id) return false
        if (name != other.name) return false
        if (created_time != other.created_time) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (created_time?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        return result
    }


}