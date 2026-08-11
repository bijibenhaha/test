<template>
  <div class="picture-grid">
    <p v-if="total > 0" class="result-count">共 {{ total }} 张图片</p>
    <div class="grid">
      <div v-for="item in list" :key="item.id" class="picture-item">
        <img :src="item.thumbnailUrl" :alt="item.title" class="picture-img" />
        <div class="picture-info">
          <h4 class="picture-title" v-html="highlight(item.title, keyword)" />
          <p v-if="item.description" class="picture-desc" v-html="highlight(item.description, keyword)" />
        </div>
      </div>
    </div>
    <div v-if="total === 0" class="empty">暂无图片结果</div>
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
</script>

<style scoped>
.result-count {
  font-size: 13px;
  color: #6b7280;
  margin-bottom: 16px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 16px;
}

.picture-item {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid #e8e8ef;
}

.picture-img {
  width: 100%;
  height: 160px;
  object-fit: cover;
}

.picture-info {
  padding: 12px;
}

.picture-title {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 4px;
}

.picture-desc {
  font-size: 12px;
  color: #6b7280;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.empty {
  text-align: center;
  padding: 48px 0;
  color: #9ca3af;
}
</style>
