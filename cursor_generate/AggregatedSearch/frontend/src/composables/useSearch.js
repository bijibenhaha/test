import { ref, reactive } from 'vue'
import { search as searchApi } from '../api/search'

const TAB_TYPES = ['post', 'picture', 'user']

function createEmptyTabState() {
  return {
    total: 0,
    list: [],
    page: 1,
    loaded: false,
  }
}

export function useSearch(pageSize = 10) {
  const keyword = ref('')
  const activeTab = ref('post')
  const loading = ref(false)
  const error = ref('')
  const searchedKeyword = ref('')
  const hasSearched = ref(false)

  const results = reactive({
    post: createEmptyTabState(),
    picture: createEmptyTabState(),
    user: createEmptyTabState(),
  })

  function resetResults() {
    TAB_TYPES.forEach((type) => {
      Object.assign(results[type], createEmptyTabState())
    })
  }

  async function fetchTab(type, page = results[type].page) {
    if (!searchedKeyword.value) return

    loading.value = true
    error.value = ''
    try {
      const res = await searchApi({
        keyword: searchedKeyword.value,
        type,
        page,
        size: pageSize,
      })
      const data = res.data
      results[type].total = data.total
      results[type].list = data.list
      results[type].page = page
      results[type].loaded = true
    } catch (e) {
      error.value = e.message || '搜索失败，请重试'
      results[type].loaded = false
    } finally {
      loading.value = false
    }
  }

  async function onSearch() {
    const trimmed = keyword.value.trim()
    if (!trimmed) {
      error.value = '请输入搜索关键词'
      return
    }

    error.value = ''
    activeTab.value = 'post'
    searchedKeyword.value = trimmed
    hasSearched.value = true
    resetResults()
    await fetchTab('post', 1)
  }

  async function onTabChange(tab) {
    activeTab.value = tab

    if (!hasSearched.value) return

    if (results[tab].loaded) return

    await fetchTab(tab, 1)
  }

  async function onPageChange(type, page) {
    await fetchTab(type, page)
  }

  return {
    keyword,
    activeTab,
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
