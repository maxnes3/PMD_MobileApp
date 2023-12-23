package com.example.mobileapp.database.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mobileapp.GlobalUser
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.repositories.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {

    val getAllStories: Flow<PagingData<Story>> = storyRepository.getAllStories().cachedIn(viewModelScope)

    suspend fun getStoryById(id: Int): Story? = storyRepository.getStoryById(id)

    val getStoriesByUserId: Flow<PagingData<Story>> = GlobalUser.getInstance().getUser()?.id?.let {
        storyRepository.getStoriesByUserId(it).cachedIn(viewModelScope)
    } ?: flowOf(PagingData.empty())

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