package com.example.jetpack.core.injection.qualifier

import javax.inject.Qualifier

/**
 * Qualifier Annotations with hilt - https://medium.com/@khudoyshukur/qualifier-annotations-with-hilt-edc9d7551b0f
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher