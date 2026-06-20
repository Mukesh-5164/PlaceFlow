import { useEffect, useState } from 'react'
import {
  getDsaTopics,
  createDsaTopic,
  updateDsaTopic,
  deleteDsaTopic,
} from '../api/dsaApi'
import Modal from '../components/Modal'
import Loader from '../components/Loader'

const EMPTY_FORM = {
  topicName: '',
  progressPercentage: 0,
  lastPracticedDate: '',
}

function DsaTracker() {
  const [topics, setTopics] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [modalOpen, setModalOpen] = useState(false)
  const [editingTopic, setEditingTopic] = useState(null)
  const [form, setForm] = useState(EMPTY_FORM)
  const [saving, setSaving] = useState(false)
  const [formError, setFormError] = useState(null)

  const fetchTopics = async () => {
    try {
      setLoading(true)
      const data = await getDsaTopics()
      setTopics(data)
      setError(null)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { fetchTopics() }, [])

  const openCreate = () => {
    setEditingTopic(null)
    setForm(EMPTY_FORM)
    setFormError(null)
    setModalOpen(true)
  }

  const openEdit = (topic) => {
    setEditingTopic(topic)
    setForm({
      topicName: topic.topicName,
      progressPercentage: topic.progressPercentage,
      lastPracticedDate: topic.lastPracticedDate || '',
    })
    setFormError(null)
    setModalOpen(true)
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this DSA topic?')) return
    try {
      await deleteDsaTopic(id)
      fetchTopics()
    } catch (err) {
      alert(err.message)
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setFormError(null)
    setSaving(true)
    try {
      const payload = {
        ...form,
        progressPercentage: Number(form.progressPercentage),
        lastPracticedDate: form.lastPracticedDate || null,
      }
      if (editingTopic) {
        await updateDsaTopic(editingTopic.id, payload)
      } else {
        await createDsaTopic(payload)
      }
      setModalOpen(false)
      fetchTopics()
    } catch (err) {
      setFormError(err.message)
    } finally {
      setSaving(false)
    }
  }

  const getProgressColor = (pct) => {
    if (pct >= 80) return 'linear-gradient(90deg, #10b981, #34d399)'
    if (pct >= 50) return 'linear-gradient(90deg, #6366f1, #8b5cf6)'
    if (pct >= 25) return 'linear-gradient(90deg, #f59e0b, #fbbf24)'
    return 'linear-gradient(90deg, #f43f5e, #fb7185)'
  }

  const avgProgress = topics.length > 0
    ? Math.round(topics.reduce((acc, t) => acc + t.progressPercentage, 0) / topics.length)
    : 0

  return (
    <div className="page-container">
      <div className="page-header">
        <div>
          <h1 className="page-title">DSA Tracker 💻</h1>
          <p className="page-subtitle">
            Track your Data Structures &amp; Algorithms progress
            {topics.length > 0 && (
              <span style={{ marginLeft: '12px', color: 'var(--primary-light)', fontWeight: 600 }}>
                · Overall: {avgProgress}%
              </span>
            )}
          </p>
        </div>
        <button id="add-dsa-btn" className="btn btn-primary" onClick={openCreate}>
          + Add Topic
        </button>
      </div>

      {error && <div className="alert alert-error">⚠️ {error}</div>}

      {loading ? (
        <Loader message="Loading topics..." />
      ) : topics.length === 0 ? (
        <div className="empty-state" style={{ marginTop: '48px' }}>
          <div className="empty-state-icon">🧠</div>
          <div className="empty-state-text">No topics yet.<br />Start adding DSA topics to track your progress!</div>
        </div>
      ) : (
        <div className="dsa-grid">
          {topics.map(topic => (
            <div key={topic.id} className="dsa-card">
              <div className="dsa-card-header">
                <div>
                  <div className="dsa-card-name">{topic.topicName}</div>
                  <div className="dsa-card-date">
                    {topic.lastPracticedDate
                      ? `Last practiced: ${new Date(topic.lastPracticedDate).toLocaleDateString('en-IN', { day: '2-digit', month: 'short', year: 'numeric' })}`
                      : 'Not practiced yet'}
                  </div>
                </div>
                <div className="dsa-card-actions">
                  <button
                    className="btn-icon edit"
                    onClick={() => openEdit(topic)}
                    title="Edit"
                    aria-label={`Edit ${topic.topicName}`}
                  >✏️</button>
                  <button
                    className="btn-icon danger"
                    onClick={() => handleDelete(topic.id)}
                    title="Delete"
                    aria-label={`Delete ${topic.topicName}`}
                  >🗑️</button>
                </div>
              </div>

              <div className="progress-label">
                <span style={{ fontSize: '0.75rem', color: 'var(--text-secondary)' }}>Progress</span>
                <span className="progress-percent">{topic.progressPercentage}%</span>
              </div>
              <div className="progress-bar-container">
                <div
                  className="progress-bar-fill"
                  style={{
                    width: `${topic.progressPercentage}%`,
                    background: getProgressColor(topic.progressPercentage)
                  }}
                />
              </div>

              <div style={{ marginTop: '12px', display: 'flex', gap: '8px' }}>
                <span
                  style={{
                    fontSize: '0.7rem',
                    padding: '3px 10px',
                    borderRadius: '999px',
                    background: topic.progressPercentage === 100 ? 'rgba(16,185,129,0.15)' : 'rgba(99,102,241,0.1)',
                    color: topic.progressPercentage === 100 ? 'var(--accent-emerald)' : 'var(--primary-light)',
                    border: `1px solid ${topic.progressPercentage === 100 ? 'rgba(16,185,129,0.25)' : 'rgba(99,102,241,0.2)'}`,
                    fontWeight: 600,
                  }}
                >
                  {topic.progressPercentage === 100 ? '✅ Completed' : topic.progressPercentage > 0 ? '🔄 In Progress' : '⏳ Not Started'}
                </span>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Create/Edit Modal */}
      <Modal
        isOpen={modalOpen}
        onClose={() => setModalOpen(false)}
        title={editingTopic ? 'Edit DSA Topic' : 'Add DSA Topic'}
      >
        <form onSubmit={handleSubmit} id="dsa-form">
          {formError && <div className="alert alert-error">⚠️ {formError}</div>}

          <div className="form-group">
            <label className="form-label" htmlFor="topicName">Topic Name *</label>
            <input
              id="topicName"
              className="form-input"
              type="text"
              placeholder="e.g. Arrays, Dynamic Programming"
              value={form.topicName}
              onChange={e => setForm(f => ({ ...f, topicName: e.target.value }))}
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label" htmlFor="progressPercentage">
              Progress: {form.progressPercentage}%
            </label>
            <input
              id="progressPercentage"
              type="range"
              min="0"
              max="100"
              step="5"
              value={form.progressPercentage}
              onChange={e => setForm(f => ({ ...f, progressPercentage: Number(e.target.value) }))}
              style={{ width: '100%', accentColor: 'var(--primary)', cursor: 'pointer' }}
            />
            <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.7rem', color: 'var(--text-muted)', marginTop: '4px' }}>
              <span>0%</span>
              <span>50%</span>
              <span>100%</span>
            </div>
          </div>

          <div className="form-group">
            <label className="form-label" htmlFor="lastPracticedDate">Last Practiced Date</label>
            <input
              id="lastPracticedDate"
              className="form-input"
              type="date"
              value={form.lastPracticedDate}
              onChange={e => setForm(f => ({ ...f, lastPracticedDate: e.target.value }))}
            />
          </div>

          <div className="form-actions">
            <button type="button" className="btn btn-secondary" onClick={() => setModalOpen(false)}>
              Cancel
            </button>
            <button type="submit" className="btn btn-primary" disabled={saving}>
              {saving ? 'Saving...' : editingTopic ? 'Update' : 'Add Topic'}
            </button>
          </div>
        </form>
      </Modal>
    </div>
  )
}

export default DsaTracker
