package by.kleban.dogdairy.mappers


interface Mapper<F, T> {

    fun map(from: F): T
}