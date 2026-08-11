<template>
  <div class="picture-list">
    <a-spin :spinning="loading">
      <a-empty v-if="!loading && total === 0" description="暂无图片" />
      <div v-else class="grid">
        <div v-for="(item, index) in list" :key="index" class="picture-card">
          <a-image :src="item.url" :alt="item.name" class="picture-img" />
          <div class="picture-info">
            <div class="picture-name" v-html="highlight(item.name, keyword)" />
            <div
              v-if="item.description"
              class="picture-desc"
              v-html="highlight(item.description, keyword)"
            />
          </div>
        </div>
      </div>
    </a-spin>
    <a-pagination
      v-if="total > pageSize"
      class="pagination"
      :current="page"
      :total="total"
      :page-size="pageSize"
      :show-size-changer="false"
      :show-total="(t: number) => `共 ${t} 张图片`"
      @change="emit('page-change', $event)"
    />
  </div>
</template>

<script setup lang="ts">
import { highlight } from '../utils/highlight'

interface Props {
  list: any[]
  total: number
  page: number
  pageSize: number
  keyword: string
  loading: boolean
}

defineProps<Props>()

const emit = defineEmits<{
  (e: 'page-change', page: number): void
}>()
</script>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 16px;
}

.picture-card {
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  overflow: hidden;
  background: #fff;
}

.picture-img {
  width: 100%;
  height: 140px;
  object-fit: cover;
}

.picture-info {
  padding: 8px 12px;
}

.picture-name {
  font-size: 14px;
  font-weight: 600;
}

.picture-desc {
  margin-top: 4px;
  font-size: 12px;
  color: #999;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.pagination {
  margin-top: 16px;
  text-align: right;
}
</style>
