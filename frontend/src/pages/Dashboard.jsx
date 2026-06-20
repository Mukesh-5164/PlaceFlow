import { useEffect, useState } from 'react'
import { getDashboardStats } from '../api/dashboardApi'
import { getApplications } from '../api/applicationsApi'
import { getTasks } from '../api/tasksApi'
import { getDsaTopics } from '../api/dsaApi'
import StatCard from '../components/StatCard'
import Loader from '../components/Loader'

function Dashboard() {
  const [stats, setStats] = useState(null)
  const [deadlines, setDeadlines] = useState([])
  const [pendingTasks, setPendingTasks] = useState([])
  const [dsaTopics, setDsaTopics] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchAll = async () => {
      try {
        setLoading(true)
        const [statsData, apps, tasks, topics] = await Promise.all([
          getDashboardStats(),
          getApplications(),
          getTasks(),
          getDsaTopics(),
        ])
        setStats(statsData)

        // Upcoming deadlines: sort by deadline, show top 5
        const upcoming = apps
          .filter(a => a.deadline && new Date(a.deadline) >= new Date())
          .sort((a, b) => new Date(a.deadline) - new Date(b.deadline))
          .slice(0, 5)
        setDeadlines(upcoming)

        // Pending tasks: top 5
        const pending = tasks.filter(t => t.status === 'Pending').slice(0, 5)
        setPendingTasks(pending)

        // Top DSA topics
        setDsaTopics(topics.slice(0, 5))
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }
    fetchAll()
  }, [])

  if (loading) return <div className="page-container"><Loader message="Loading dashboard..." /></div>

  if (error) return (
    <div className="page-container">
      <div className="alert alert-error">⚠️ {error}</div>
    </div>
  )

  const formatDate = (dateStr) => {
    if (!dateStr) return '—'
    return new Date(dateStr).toLocaleDateString('en-IN', { day: '2-digit', month: 'short', year: 'numeric' })
  }

  const daysUntil = (dateStr) => {
    const diff = Math.ceil((new Date(dateStr) - new Date()) / (1000 * 60 * 60 * 24))
    if (diff === 0) return 'Today!'
    if (diff === 1) return 'Tomorrow'
    return `${diff} days`
  }

  return (
    <div className="page-container">
      <div className="page-header">
        <div>
          <h1 className="page-title">Dashboard 🏠</h1>
          <p className="page-subtitle">Your placement journey at a glance</p>
        </div>
      </div>

      {/* Stats Grid */}
      <div className="stats-grid">
        <StatCard
          icon="📋"
          value={stats?.totalApplications ?? 0}
          label="Total Applications"
          gradient="linear-gradient(90deg, #6366f1, #8b5cf6)"
        />
        <StatCard
          icon="⏰"
          value={stats?.upcomingDeadlines ?? 0}
          label="Upcoming Deadlines"
          gradient="linear-gradient(90deg, #f59e0b, #ef4444)"
          change={stats?.upcomingDeadlines > 0 ? 'In next 7 days' : 'No urgent deadlines'}
          changeType={stats?.upcomingDeadlines > 0 ? 'negative' : 'neutral'}
        />
        <StatCard
          icon="✅"
          value={stats?.pendingStudyTasks ?? 0}
          label="Pending Tasks"
          gradient="linear-gradient(90deg, #06b6d4, #6366f1)"
        />
        <StatCard
          icon="💻"
          value={`${stats?.averageDsaProgress ?? 0}%`}
          label="Avg DSA Progress"
          gradient="linear-gradient(90deg, #10b981, #06b6d4)"
        />
        <StatCard
          icon="🎉"
          value={stats?.selectedApplications ?? 0}
          label="Selected"
          gradient="linear-gradient(90deg, #10b981, #34d399)"
          change={stats?.selectedApplications > 0 ? 'Congratulations!' : 'Keep applying!'}
          changeType={stats?.selectedApplications > 0 ? 'positive' : 'neutral'}
        />
        <StatCard
          icon="🎤"
          value={stats?.interviewApplications ?? 0}
          label="In Interview Stage"
          gradient="linear-gradient(90deg, #8b5cf6, #a78bfa)"
        />
      </div>

      {/* Bottom Panels */}
      <div className="dashboard-grid">
        {/* Upcoming Deadlines */}
        <div className="card">
          <div className="card-header">
            <h2 className="card-title">⏰ Upcoming Deadlines</h2>
            <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Next 7 days</span>
          </div>
          {deadlines.length === 0 ? (
            <div className="empty-state">
              <div className="empty-state-icon">🎉</div>
              <div className="empty-state-text">No upcoming deadlines</div>
            </div>
          ) : (
            deadlines.map(app => (
              <div key={app.id} className="deadline-item">
                <div>
                  <div className="deadline-company">{app.companyName}</div>
                  <div className="deadline-role">{app.role}</div>
                </div>
                <div className="deadline-date">{daysUntil(app.deadline)}</div>
              </div>
            ))
          )}
        </div>

        {/* Pending Study Tasks */}
        <div className="card">
          <div className="card-header">
            <h2 className="card-title">📚 Pending Study Tasks</h2>
            <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Top 5</span>
          </div>
          {pendingTasks.length === 0 ? (
            <div className="empty-state">
              <div className="empty-state-icon">✨</div>
              <div className="empty-state-text">All tasks completed!</div>
            </div>
          ) : (
            <div className="task-list">
              {pendingTasks.map(task => (
                <div key={task.id} className="task-item">
                  <span style={{ fontSize: '1rem' }}>📌</span>
                  <div className="task-name">{task.taskName}</div>
                  {task.dueDate && (
                    <div className="task-due">{formatDate(task.dueDate)}</div>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>

        {/* DSA Progress Summary */}
        <div className="card" style={{ gridColumn: '1 / -1' }}>
          <div className="card-header">
            <h2 className="card-title">💻 DSA Progress Summary</h2>
            <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>
              Avg: {stats?.averageDsaProgress ?? 0}%
            </span>
          </div>
          {dsaTopics.length === 0 ? (
            <div className="empty-state">
              <div className="empty-state-icon">🧠</div>
              <div className="empty-state-text">Start tracking your DSA progress!</div>
            </div>
          ) : (
            <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
              {dsaTopics.map(topic => (
                <div key={topic.id}>
                  <div className="progress-label">
                    <span className="progress-topic">{topic.topicName}</span>
                    <span className="progress-percent">{topic.progressPercentage}%</span>
                  </div>
                  <div className="progress-bar-container">
                    <div
                      className="progress-bar-fill"
                      style={{ width: `${topic.progressPercentage}%` }}
                    />
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

export default Dashboard
