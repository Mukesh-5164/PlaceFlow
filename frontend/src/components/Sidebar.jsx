import { NavLink } from 'react-router-dom'

import { LayoutDashboard, Briefcase, Code2, CheckSquare, FileText, Rocket } from 'lucide-react'

const navItems = [
  { to: '/dashboard', icon: <LayoutDashboard size={20} />, label: 'Dashboard' },
  { to: '/applications', icon: <Briefcase size={20} />, label: 'Applications' },
  { to: '/dsa', icon: <Code2 size={20} />, label: 'DSA Tracker' },
  { to: '/tasks', icon: <CheckSquare size={20} />, label: 'Study Tasks' },
  { to: '/notes', icon: <FileText size={20} />, label: 'Notes' },
]

function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar-logo">
        <div className="sidebar-logo-icon">
          <Rocket size={20} color="white" />
        </div>
        <div>
          <div className="sidebar-logo-text">PlaceFlow</div>
          <div className="sidebar-logo-sub">Placement Tracker</div>
        </div>
      </div>

      <nav className="sidebar-nav">
        <div className="sidebar-section-label">Navigation</div>
        {navItems.map((item) => (
          <NavLink
            key={item.to}
            to={item.to}
            className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}
          >
            <span className="nav-icon">{item.icon}</span>
            <span className="nav-label">{item.label}</span>
          </NavLink>
        ))}
      </nav>

      <div className="sidebar-footer">
        <div className="sidebar-footer-text">
          PlaceFlow v1.0.0 &nbsp;•&nbsp; Built for Students
        </div>
      </div>
    </aside>
  )
}

export default Sidebar
