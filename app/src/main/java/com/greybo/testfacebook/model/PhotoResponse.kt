package com.greybo.testfacebook.model

import com.google.gson.annotations.SerializedName

class PhotoResponse {

    @SerializedName("data")
    var data: ArrayList<Data>? = null

    class Data {

        @SerializedName("id")
        var id: String? = null

        @SerializedName("source")
        var source: String? = null

        @SerializedName("created_time")
        var created_time: String? = null

        @SerializedName("album")
        var album: Album? = null

        override fun toString(): String {
            return "Data(id=$id, source=$source, created_time=$created_time, album=$album)"
        }


    }

    class Album {
        @SerializedName("id")
        var id: String? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("created_time")
        var created_time: String? = null

        override fun toString(): String {
            return "Album(id=$id, name=$name, created_time=$created_time)"
        }

    }

    override fun toString(): String {
        return "PhotoResponse(data=$data)"
    }
}