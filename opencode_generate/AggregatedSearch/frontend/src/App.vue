<template>
  <div class="app">
    <header class="app-header">
      <span class="app-logo">聚合搜索</span>
    </header>
    <main class="app-main">
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.app-header {
  background: #fff;
  border-bottom: 1px solid #f0f0f0;
  padding: 0 24px;
  height: 56px;
  display: flex;
  align-items: center;
}

.app-logo {
  font-size: 18px;
  font-weight: 600;
  color: #1677ff;
}

.app-main {
  max-width: 900px;
  margin: 0 auto;
  padding: 24px 16px;
}
</style>
