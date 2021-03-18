package com.example.apptestesnode

import com.google.gson.annotations.SerializedName

class Cliente (

    var id: Int = 0,

    @SerializedName ("name")
    var nome: String
)