import { useState } from 'react'
import { BrowserRouter, Routes, Route, Link, useLocation } from 'react-router-dom'
import DashboardPage from './pages/dashboard/DashboardPage'
import CustomerPage from './pages/customer/CustomerPage'
import RentalsPage from './pages/rentals/RentalsPage'
import ReportsPage from './pages/reports/ReportsPage'

function usePageTitle(pathname: string): string {
  switch(pathname) {
    case '/':
      return 'Dashboard'
    case '/customers':
      return 'Customers'
    case '/rentals':
      return 'Rentals'
    case '/reports':
      return 'Reports'
    default:
      return 'Admin Panel'
  }
}

function Header() {
  const location = useLocation()
  
  return (
    <header className="bg-white shadow p-4">
      <div className="flex justify-between items-center">
        <h2 className="text-xl font-semibold text-gray-800">
          {usePageTitle(location.pathname)}
        </h2>
        <div className="flex items-center space-x-4">
          <button className="p-2 text-gray-500 hover:text-gray-700">
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
            </svg>
          </button>
          <div className="relative">
            <button className="flex items-center space-x-2">
              <img src="https://placehold.co/40" alt="User" className="rounded-full" />
              <span className="text-gray-700">Admin User</span>
            </button>
          </div>
        </div>
      </div>
    </header>
  )
}

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(true)

  return (
    <BrowserRouter>
      <div className="min-h-screen bg-gray-100">
        {/* Sidebar */}
        <div className={`fixed inset-y-0 left-0 transform ${sidebarOpen ? 'translate-x-0' : '-translate-x-full'} transition-transform duration-200 ease-in-out bg-primary w-64`}>
          <div className="flex items-center justify-between p-4 border-b border-secondary">
            <h1 className="text-xl font-bold text-white">Car Rental System</h1>
            <button 
              onClick={() => setSidebarOpen(!sidebarOpen)}
              className="p-2 text-white hover:bg-secondary rounded-lg"
            >
              {sidebarOpen ? '«' : '»'}
            </button>
          </div>
          <nav className="p-4">
            <ul className="space-y-2">
              <li>
                <Link to="/" className="block p-2 text-white hover:bg-secondary rounded-lg">Dashboard</Link>
              </li>
              <li>
                <Link to="/customers" className="block p-2 text-white hover:bg-secondary rounded-lg">Customers</Link>
              </li>
              <li>
                <Link to="/rentals" className="block p-2 text-white hover:bg-secondary rounded-lg">Rentals</Link>
              </li>
              <li>
                <Link to="/reports" className="block p-2 text-white hover:bg-secondary rounded-lg">Reports</Link>
              </li>
            </ul>
          </nav>
        </div>

        {/* Main Content */}
        <div className={`transition-all duration-200 ease-in-out ${sidebarOpen ? 'ml-64' : 'ml-0'}`}>
          <Header />
          <main className="p-6">
            <Routes>
              <Route path="/customers" element={<CustomerPage />} />
              <Route path="/" element={<DashboardPage />} />
              <Route path="/rentals" element={<RentalsPage />} />
              <Route path="/reports" element={<ReportsPage />} />
            </Routes>
          </main>
        </div>
      </div>
    </BrowserRouter>
  )
}

export default App
