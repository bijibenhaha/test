export function escapeRegExp(str) {
  return str.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')
}

export function highlight(text, keyword) {
  if (!text || !keyword) return text || ''
  const reg = new RegExp(`(${escapeRegExp(keyword)})`, 'gi')
  return text.replace(reg, '<mark>$1</mark>')
}
