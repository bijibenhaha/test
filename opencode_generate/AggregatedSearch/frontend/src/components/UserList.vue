<template>
  <div class="user-list">
    <a-spin :spinning="loading">
      <a-empty v-if="!loading && total === 0" description="暂无用户" />
      <a-list v-else item-layout="horizontal" :data-source="list" :pagination="false">
        <template #renderItem="{ item }">
          <a-list-item>
            <a-list-item-meta>
              <template #avatar>
                <a-avatar :src="item.userAvatar" :size="48" />
              </template>
              <template #title>
                <span v-html="highlight(item.userName, keyword)" />
              </template>
              <template #description>
                <div>
                  <div v-if="item.userProfile" v-html="highlight(item.userProfile, keyword)" />
                  <div class="user-meta">@{{ item.userAccount }} · {{ item.userRole }}</div>
                </div>
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
      :show-total="(t: number) => `共 ${t} 位用户`"
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
.user-meta {
  margin-top: 2px;
  font-size: 12px;
  color: #bbb;
}

.pagination {
  margin-top: 16px;
  text-align: right;
}
</style>
