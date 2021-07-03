package by.kleban.dogdiary.core


interface Mapper<F, T> {

    fun map(from: F): T
}