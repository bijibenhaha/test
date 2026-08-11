<template>
  <div class="index-page">
    <a-card :bordered="false">
      <a-input-search
        v-model:value="keyword"
        placeholder="搜索文章、图片、用户..."
        enter-button="Search"
        size="large"
        allow-clear
        @search="onSearch"
      />

      <p v-if="error" class="error-msg">{{ error }}</p>

      <!-- Tab 顺序：Post → Picture → User -->
      <a-tabs v-model:activeKey="activeKey" class="search-tabs" @change="onTabChange">
        <a-tab-pane key="Post" tab="文章">
          <PostList
            :list="results.Post.list"
            :total="results.Post.total"
            :page="results.Post.page"
            :page-size="pageSize"
            :keyword="searchedKeyword"
            :loading="loading"
            @page-change="onPageChange('Post', $event)"
          />
        </a-tab-pane>

        <a-tab-pane key="Picture" tab="图片">
          <PictureList
            :list="results.Picture.list"
            :total="results.Picture.total"
            :page="results.Picture.page"
            :page-size="pageSize"
            :keyword="searchedKeyword"
            :loading="loading"
            @page-change="onPageChange('Picture', $event)"
          />
        </a-tab-pane>

        <a-tab-pane key="User" tab="用户">
          <UserList
            :list="results.User.list"
            :total="results.User.total"
            :page="results.User.page"
            :page-size="pageSize"
            :keyword="searchedKeyword"
            :loading="loading"
            @page-change="onPageChange('User', $event)"
          />
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import PostList from '../components/PostList.vue'
import PictureList from '../components/PictureList.vue'
import UserList from '../components/UserList.vue'
import { useSearch } from '../composables/useSearch'

const pageSize = 10

const {
  keyword,
  activeKey,
  loading,
  error,
  searchedKeyword,
  results,
  onSearch,
  onTabChange,
  onPageChange,
} = useSearch(pageSize)
</script>

<style scoped>
.index-page {
  max-width: 900px;
  margin: 0 auto;
}

.search-tabs {
  margin-top: 8px;
}
</style>
