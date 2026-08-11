import request from './request'

export function search(params) {
  return request.get('/search', { params })
}
