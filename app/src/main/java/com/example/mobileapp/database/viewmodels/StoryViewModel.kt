package com.example.mobileapp.database.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.repositories.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {

    val getAllStories: Flow<PagingData<Story>> = storyRepository.getAllStories().cachedIn(viewModelScope)

    fun getStoryById(id: Int): Flow<Story?> = storyRepository.getStoryById(id)

    fun getStoriesByUserId(userId: Int): Flow<PagingData<Story>> = storyRepository.getStoriesByUserId(userId).cachedIn(viewModelScope)

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