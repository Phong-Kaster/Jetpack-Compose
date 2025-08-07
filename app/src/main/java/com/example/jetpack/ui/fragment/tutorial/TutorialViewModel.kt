package com.example.jetpack.ui.fragment.tutorial

import androidx.lifecycle.ViewModel
import com.example.jetpack.data.repository.TutorialRepository
import com.example.jetpack.ui.fragment.tutorial.component.TutorialState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel
@Inject
constructor(private val tutorialRepository: TutorialRepository) : ViewModel() {

    val tutorial = TutorialState(
        enableTutorial = tutorialRepository.enableTutorial(),
        onDone = {
//            tutorialRepository.disableTutorial()
        }
    )
}