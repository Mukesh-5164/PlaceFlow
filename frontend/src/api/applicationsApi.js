import api from './axios'

export const getApplications = (status = '') =>
  api.get('/applications', { params: status ? { status } : {} }).then(r => r.data)

export const getApplicationById = (id) =>
  api.get(`/applications/${id}`).then(r => r.data)

export const createApplication = (data) =>
  api.post('/applications', data).then(r => r.data)

export const updateApplication = (id, data) =>
  api.put(`/applications/${id}`, data).then(r => r.data)

export const deleteApplication = (id) =>
  api.delete(`/applications/${id}`)
