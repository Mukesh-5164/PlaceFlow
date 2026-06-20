import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Sidebar from './components/Sidebar'
import Dashboard from './pages/Dashboard'
import Applications from './pages/Applications'
import DsaTracker from './pages/DsaTracker'
import StudyTasks from './pages/StudyTasks'
import Notes from './pages/Notes'

function App() {
  return (
    <BrowserRouter>
      <div className="app-layout">
        <Sidebar />
        <main className="main-content">
          <Routes>
            <Route path="/" element={<Navigate to="/dashboard" replace />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/applications" element={<Applications />} />
            <Route path="/dsa" element={<DsaTracker />} />
            <Route path="/tasks" element={<StudyTasks />} />
            <Route path="/notes" element={<Notes />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  )
}

export default App
