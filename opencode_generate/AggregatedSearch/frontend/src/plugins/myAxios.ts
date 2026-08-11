import axios from 'axios'

const instance = axios.create({
  baseURL: '/api',
  timeout: 10000,
})

// 响应拦截器：code === 0 时直接返回 data.data
instance.interceptors.response.use(
  (response) => {
    const data = response.data
    if (data.code === 0) {
      return data.data
    }
    console.error('request error', data)
    return data
  },
  (error) => Promise.reject(error)
)

export default instance
