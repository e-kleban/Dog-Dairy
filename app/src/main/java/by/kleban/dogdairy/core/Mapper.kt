package by.kleban.dogdairy.core


interface Mapper<F, T> {

    fun map(from: F): T
}