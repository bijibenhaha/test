<template>
  <div class="search-tabs">
    <button
      v-for="tab in tabs"
      :key="tab.key"
      class="tab-item"
      :class="{ active: activeTab === tab.key }"
      @click="emit('change', tab.key)"
    >
      {{ tab.label }}
    </button>
  </div>
</template>

<script setup>
defineProps({
  activeTab: { type: String, required: true },
})

const emit = defineEmits(['change'])

const tabs = [
  { key: 'post', label: 'Post' },
  { key: 'picture', label: 'Picture' },
  { key: 'user', label: 'User' },
]
</script>

<style scoped>
.search-tabs {
  display: flex;
  gap: 4px;
  border-bottom: 1px solid #e8e8ef;
  margin-top: 24px;
}

.tab-item {
  padding: 12px 20px;
  background: none;
  border: none;
  border-bottom: 2px solid transparent;
  font-size: 14px;
  font-weight: 500;
  color: #6b7280;
  cursor: pointer;
  transition: color 0.2s, border-color 0.2s;
  margin-bottom: -1px;
}

.tab-item:hover {
  color: #4f46e5;
}

.tab-item.active {
  color: #4f46e5;
  border-bottom-color: #4f46e5;
}
</style>
