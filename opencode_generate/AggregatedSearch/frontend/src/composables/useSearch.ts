import { reactive, ref } from 'vue'
import myAxios from '../plugins/myAxios'

// Tab 顺序：Post → Picture → User
export const TAB_TYPES = ['Post', 'Picture', 'User'] as const
export type TabType = (typeof TAB_TYPES)[number]

export interface TabState {
  total: number
  list: any[]
  page: number
  loaded: boolean
}

export type SearchResults = Record<TabType, TabState>

function emptyState(): TabState {
  return { total: 0, list: [], page: 1, loaded: false }
}

export function useSearch(pageSize = 10) {
  const keyword = ref('')
  const activeKey = ref<TabType>('Post')
  const loading = ref(false)
  const error = ref('')
  const searchedKeyword = ref('')
  const hasSearched = ref(false)

  const results = reactive<SearchResults>({
    Post: emptyState(),
    Picture: emptyState(),
    User: emptyState(),
  })

  function resetResults() {
    TAB_TYPES.forEach((t) => {
      results[t] = emptyState()
    })
  }

  async function fetchTab(type: TabType, page: number) {
    if (!searchedKeyword.value) return
    loading.value = true
    error.value = ''
    try {
      const res: any = await myAxios.post('/search/all', {
        searchText: searchedKeyword.value,
        type,
        current: page,
        pageSize,
      })
      results[type].total = res.total || 0
      results[type].list = res.dataList || []
      results[type].page = page
      results[type].loaded = true
    } catch (e: any) {
      error.value = e?.message || '搜索失败，请重试'
      results[type].loaded = false
    } finally {
      loading.value = false
    }
  }

  // 点击 Search：重置为 Post Tab，只请求 Post
  async function onSearch() {
    const trimmed = keyword.value.trim()
    if (!trimmed) {
      error.value = '请输入搜索关键词'
      return
    }
    error.value = ''
    activeKey.value = 'Post'
    searchedKeyword.value = trimmed
    hasSearched.value = true
    resetResults()
    await fetchTab('Post', 1)
  }

  // 切换 Tab：首次切换才请求对应类型，已加载则直接用缓存
  async function onTabChange(key: TabType) {
    activeKey.value = key
    if (!hasSearched.value) return
    if (results[key].loaded) return
    await fetchTab(key, 1)
  }

  async function onPageChange(type: TabType, page: number) {
    await fetchTab(type, page)
  }

  return {
    keyword,
    activeKey,
    loading,
    error,
    searchedKeyword,
    hasSearched,
    results,
    onSearch,
    onTabChange,
    onPageChange,
  }
}
