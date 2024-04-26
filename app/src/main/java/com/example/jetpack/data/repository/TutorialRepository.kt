package com.example.jetpack.data.repository

import com.example.jetpack.datastore.TutorialDatastore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TutorialRepository
@Inject
constructor(
    private val tutorialDatastore: TutorialDatastore
) {
    fun enableTutorial(): Boolean {
        return tutorialDatastore.enableTutorial
    }

    fun disableTutorial(){
        tutorialDatastore.enableTutorial = false
    }
}