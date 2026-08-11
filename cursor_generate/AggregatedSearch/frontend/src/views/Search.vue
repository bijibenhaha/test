<template>
  <div class="search-page">
    <SearchBar
      :model-value="keyword"
      :loading="loading"
      @update:model-value="keyword = $event"
      @search="onSearch"
    />

    <p v-if="error" class="error-msg">{{ error }}</p>

    <SearchTabs :active-tab="activeTab" @change="onTabChange" />

    <div class="search-content">
      <div v-if="!hasSearched" class="placeholder">
        输入关键词并点击 Search 开始搜索
      </div>

      <div v-else-if="loading" class="loading">加载中...</div>

      <template v-else>
        <PostList
          v-show="activeTab === 'post'"
          :list="results.post.list"
          :total="results.post.total"
          :page="results.post.page"
          :page-size="pageSize"
          :keyword="searchedKeyword"
          @page-change="onPageChange('post', $event)"
        />
        <PictureGrid
          v-show="activeTab === 'picture'"
          :list="results.picture.list"
          :total="results.picture.total"
          :page="results.picture.page"
          :page-size="pageSize"
          :keyword="searchedKeyword"
          @page-change="onPageChange('picture', $event)"
        />
        <UserList
          v-show="activeTab === 'user'"
          :list="results.user.list"
          :total="results.user.total"
          :page="results.user.page"
          :page-size="pageSize"
          :keyword="searchedKeyword"
          @page-change="onPageChange('user', $event)"
        />
      </template>
    </div>
  </div>
</template>

<script setup>
import SearchBar from '../components/search/SearchBar.vue'
import SearchTabs from '../components/search/SearchTabs.vue'
import PostList from '../components/search/PostList.vue'
import PictureGrid from '../components/search/PictureGrid.vue'
import UserList from '../components/search/UserList.vue'
import { useSearch } from '../composables/useSearch'

const pageSize = 10

const {
  keyword,
  activeTab,
  loading,
  error,
  searchedKeyword,
  hasSearched,
  results,
  onSearch,
  onTabChange,
  onPageChange,
} = useSearch(pageSize)
</script>

<style scoped>
.search-page {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.06);
}

.error-msg {
  margin-top: 12px;
  color: #dc2626;
  font-size: 14px;
}

.search-content {
  margin-top: 24px;
  min-height: 200px;
}

.placeholder,
.loading {
  text-align: center;
  padding: 48px 0;
  color: #9ca3af;
  font-size: 14px;
}

.loading {
  color: #4f46e5;
}
</style>
