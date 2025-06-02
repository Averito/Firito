package com.averito.firito.data.extensions

inline fun <T> Iterable<T>.sumOf(selector: (T) -> Float): Float {
    var result = 0f

    for (element in this) {
        result += selector(element)
    }

    return result
}