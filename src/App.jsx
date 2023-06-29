import './App.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Home from '../src/pages/home'
import LoginForm from './pages/components/login'
import RegisterForm from './pages/components/register'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginForm />} />
        <Route path="/register" element={<RegisterForm />} />
        <Route path="/home" element={<Home />} />
      </Routes>
    </Router>
  )
}

export default App
