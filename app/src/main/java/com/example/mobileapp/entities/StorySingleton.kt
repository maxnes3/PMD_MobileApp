package com.example.mobileapp.entities

class StorySingleton {
    companion object {
        val storyList: MutableList<Story> = mutableListOf()
    }

    fun addStory(story: Story) {
        storyList.add(story)
    }

    fun getStoryList(): List<Story> {
        return storyList.toList()
    }
}