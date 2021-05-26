package com.bignerdranch.android.a1305network.mappers


interface Mapper<F, T> {

    fun map(from: F): T
}