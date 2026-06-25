import axios from 'axios'

const BASE_URL = import.meta.env.VITE_API_URL || '/api'

const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

// ── Request interceptor: attach JWT Bearer token ──────────────────────────
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('placeflow_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

// ── Response interceptor: handle errors ──────────────────────────────────
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Auto-logout on 401 Unauthorized or 403 Forbidden (token expired or invalid)
    if ((error.response?.status === 401 || error.response?.status === 403) && window.location.pathname !== '/login') {
      localStorage.removeItem('placeflow_token')
      localStorage.removeItem('placeflow_user')
      window.location.href = '/login'
    }

    const message =
      error.response?.data?.message ||
      error.response?.data?.error ||
      error.message ||
      'An unexpected error occurred'
    return Promise.reject(new Error(message))
  }
)

export default api
