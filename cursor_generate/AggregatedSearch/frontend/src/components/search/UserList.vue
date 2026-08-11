<template>
  <div class="user-list">
    <p v-if="total > 0" class="result-count">共 {{ total }} 位用户</p>
    <div v-for="item in list" :key="item.id" class="user-item">
      <img :src="item.avatarUrl" :alt="item.nickname" class="user-avatar" />
      <div class="user-info">
        <h4 class="user-nickname" v-html="highlight(item.nickname, keyword)" />
        <p class="user-username">@<span v-html="highlight(item.username, keyword)" /></p>
        <p v-if="item.bio" class="user-bio" v-html="highlight(item.bio, keyword)" />
      </div>
      <span class="user-followers">{{ item.followerCount }} 粉丝</span>
    </div>
    <div v-if="total === 0" class="empty">暂无用户结果</div>
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

.user-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 0;
  border-bottom: 1px solid #f0f0f5;
}

.user-avatar {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  object-fit: cover;
  flex-shrink: 0;
}

.user-info {
  flex: 1;
  min-width: 0;
}

.user-nickname {
  font-size: 15px;
  font-weight: 600;
}

.user-username {
  font-size: 13px;
  color: #6b7280;
  margin: 2px 0;
}

.user-bio {
  font-size: 13px;
  color: #9ca3af;
}

.user-followers {
  font-size: 13px;
  color: #6b7280;
  flex-shrink: 0;
}

.empty {
  text-align: center;
  padding: 48px 0;
  color: #9ca3af;
}
</style>
