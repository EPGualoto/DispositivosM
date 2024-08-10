package com.grupal.appnime.ui.entities.users



data class UserLogin (val uuid:String,
                        val name:String,
                       val lastName:String){
    // Constructor sin argumentos requerido por Firestore para la deserialización
    constructor() : this("", "", "")
}