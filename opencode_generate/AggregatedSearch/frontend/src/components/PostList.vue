<template>
  <div class="post-list">
    <a-spin :spinning="loading">
      <a-empty v-if="!loading && total === 0" description="暂无文章" />
      <a-list
        v-else
        item-layout="vertical"
        :data-source="list"
        :pagination="false"
      >
        <template #renderItem="{ item }">
          <a-list-item>
            <a-list-item-meta>
              <template #title>
                <span class="post-title" v-html="highlight(item.title, keyword)" />
              </template>
              <template #description>
                <div class="post-desc" v-html="highlight(item.content, keyword)" />
              </template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>
    </a-spin>
    <a-pagination
      v-if="total > pageSize"
      class="pagination"
      :current="page"
      :total="total"
      :page-size="pageSize"
      :show-size-changer="false"
      :show-total="(t: number) => `共 ${t} 篇文章`"
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
.post-title {
  font-size: 16px;
  font-weight: 600;
}

.post-desc {
  color: #666;
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
