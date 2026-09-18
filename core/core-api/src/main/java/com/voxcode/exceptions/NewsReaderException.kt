package com.voxcode.exceptions

open class NewsReaderException(message: String?) : Throwable(message) {
    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }
}