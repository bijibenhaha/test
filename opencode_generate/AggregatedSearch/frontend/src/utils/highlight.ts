export function escapeRegExp(str: string): string {
  return str.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')
}

export function escapeHtml(str: string): string {
  return str
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#39;')
}

/**
 * 对文本中的关键词进行高亮（返回带 <mark> 的 html，需配合 v-html 使用）
 * 先转义原文再匹配，避免 XSS
 */
export function highlight(text: string, keyword: string): string {
  if (!text) return ''
  const escaped = escapeHtml(text)
  if (!keyword) return escaped
  const escapedKeyword = escapeHtml(keyword)
  const reg = new RegExp(`(${escapeRegExp(escapedKeyword)})`, 'gi')
  return escaped.replace(reg, '<mark>$1</mark>')
}
