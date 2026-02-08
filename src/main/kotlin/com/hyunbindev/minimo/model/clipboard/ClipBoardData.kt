package com.hyunbindev.minimo.model.clipboard

import java.security.MessageDigest

data class ClipBoardData(
    val type: ClipType,

    val stringContent :String,

    //val byteContent :ByteArray?,

    val contentHash: String
) {
    companion object{
        fun getDataFactory(obj:Any, type: ClipType): ClipBoardData?{
            return when(type){
                ClipType.STRING -> {
                    val c = obj as String
                    ClipBoardData(
                    type          = ClipType.STRING,
                    stringContent = c,
                    contentHash   = SHA256HASHING(c.toByteArray()),
                )}

                else -> null
            }
        }
        private fun SHA256HASHING(bytes: ByteArray):String{
            val md = MessageDigest.getInstance("SHA-256")
            md.update(bytes)
            return md.digest().joinToString("") { "%02x".format(it) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if(this === other)return true

        if(other !is ClipBoardData) return false

        return this.contentHash == other.contentHash
    }
}

enum class ClipType{
    IMAGE,FILE,STRING
}