<template>
  <div class="search-bar">
    <input
      :value="modelValue"
      type="text"
      class="search-input"
      placeholder="搜索文章、图片、用户..."
      @input="emit('update:modelValue', $event.target.value)"
      @keyup.enter="emit('search')"
    />
    <button class="search-btn" :disabled="loading" @click="emit('search')">
      {{ loading ? '搜索中...' : 'Search' }}
    </button>
  </div>
</template>

<script setup>
defineProps({
  modelValue: { type: String, required: true },
  loading: { type: Boolean, default: false },
})

const emit = defineEmits(['update:modelValue', 'search'])
</script>

<style scoped>
.search-bar {
  display: flex;
  gap: 12px;
}

.search-input {
  flex: 1;
  height: 44px;
  padding: 0 16px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 15px;
  outline: none;
  transition: border-color 0.2s;
}

.search-input:focus {
  border-color: #4f46e5;
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.15);
}

.search-btn {
  height: 44px;
  padding: 0 24px;
  background: #4f46e5;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.2s;
}

.search-btn:hover:not(:disabled) {
  background: #4338ca;
}

.search-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>
