package cl.gencina.conversordivisas

data class Divisa (val nombre:String, val icono:String, val nombreCorto:String){
    override fun toString(): String {
        return nombreCorto
    }
}

