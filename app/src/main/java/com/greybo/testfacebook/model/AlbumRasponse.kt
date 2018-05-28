package com.greybo.testfacebook.model

import com.google.gson.annotations.SerializedName

class AlbumRasponse {

    @SerializedName("data")
    var data: ArrayList<Data>? = null

    class Data {
        @SerializedName("picture")
        var picture: Picture? = null

        @SerializedName("id")
        var id: String? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("created_time")
        var created_time: String? = null

        override fun toString(): String {
            return "Data(picture=$picture, id=$id, name=$name, created_time=$created_time)"
        }
    }

    class Picture {
        @SerializedName("data")
        var data: Datum? = null

        override fun toString(): String {
            return "Picture(data=$data)"
        }


    }

    class Datum {

        @SerializedName("url")
        var url: String? = null

        @SerializedName("is_silhouette")
        var is_silhouette: String? = null

        override fun toString(): String {
            return "Datum(url=$url, is_silhouette=$is_silhouette)"
        }
    }

    override fun toString(): String {
        return "AlbumRasponse(data=$data)"
    }
}
