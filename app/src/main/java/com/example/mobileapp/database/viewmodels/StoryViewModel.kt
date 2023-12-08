package com.example.mobileapp.database.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.repositories.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {

    val getAllStories = storyRepository.getAllStories()

    fun getStoryById(id: Int): Flow<Story?> = storyRepository.getStoryById(id)

    fun getStoriesByUserId(userId: Int): Flow<List<Story>> = storyRepository.getStoriesByUserId(userId)

    fun insertStory(story: Story) = viewModelScope.launch {
        storyRepository.insertStory(story)
    }

    fun updateStory(story: Story) = viewModelScope.launch {
        storyRepository.updateStory(story)
    }

    fun deleteStory(story: Story) = viewModelScope.launch {
        storyRepository.deleteStory(story)
    }
}