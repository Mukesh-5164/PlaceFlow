import api from './axios'

export const getDsaTopics = () =>
  api.get('/dsa').then(r => r.data)

export const getDsaTopicById = (id) =>
  api.get(`/dsa/${id}`).then(r => r.data)

export const createDsaTopic = (data) =>
  api.post('/dsa', data).then(r => r.data)

export const updateDsaTopic = (id, data) =>
  api.put(`/dsa/${id}`, data).then(r => r.data)

export const deleteDsaTopic = (id) =>
  api.delete(`/dsa/${id}`)
