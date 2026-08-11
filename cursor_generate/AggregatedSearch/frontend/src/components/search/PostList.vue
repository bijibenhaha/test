<template>
  <div class="post-list">
    <p v-if="total > 0" class="result-count">共 {{ total }} 篇文章</p>
    <article v-for="item in list" :key="item.id" class="post-item">
      <img v-if="item.coverUrl" :src="item.coverUrl" :alt="item.title" class="post-cover" />
      <div class="post-body">
        <h3 class="post-title" v-html="highlight(item.title, keyword)" />
        <p class="post-summary" v-html="highlight(item.summary, keyword)" />
        <div class="post-meta">
          <span>{{ item.authorName }}</span>
          <span>{{ formatDate(item.createdAt) }}</span>
        </div>
      </div>
    </article>
    <div v-if="total === 0" class="empty">暂无文章结果</div>
    <Pagination
      v-if="total > pageSize"
      :total="total"
      :page="page"
      :page-size="pageSize"
      @change="emit('page-change', $event)"
    />
  </div>
</template>

<script setup>
import { highlight } from '../../utils/highlight'
import Pagination from './Pagination.vue'

defineProps({
  list: { type: Array, default: () => [] },
  total: { type: Number, default: 0 },
  page: { type: Number, default: 1 },
  pageSize: { type: Number, default: 10 },
  keyword: { type: String, default: '' },
})

const emit = defineEmits(['page-change'])

function formatDate(dateStr) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('zh-CN')
}
</script>

<style scoped>
.result-count {
  font-size: 13px;
  color: #6b7280;
  margin-bottom: 16px;
}

.post-item {
  display: flex;
  gap: 16px;
  padding: 16px 0;
  border-bottom: 1px solid #f0f0f5;
}

.post-cover {
  width: 120px;
  height: 72px;
  object-fit: cover;
  border-radius: 6px;
  flex-shrink: 0;
}

.post-body {
  flex: 1;
  min-width: 0;
}

.post-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 6px;
}

.post-summary {
  font-size: 14px;
  color: #6b7280;
  margin-bottom: 8px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.post-meta {
  font-size: 12px;
  color: #9ca3af;
  display: flex;
  gap: 12px;
}

.empty {
  text-align: center;
  padding: 48px 0;
  color: #9ca3af;
}
</style>
