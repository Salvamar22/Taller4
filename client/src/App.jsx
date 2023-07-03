import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from '../src/pages/home';
import LoginForm from './components/login';
import { Navigate } from 'react-router-dom';
import RegisterForm from './components/register';
import NotFound from './pages/NotFound/NotFound';
import { UserProvider } from './context/userContext';
import Redirect from './pages/Redirect/RedirectUser';
import RequireAuth from './pages/PrivateRoute'
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <BrowserRouter>
      <UserProvider>
        <Routes>
          <Route path="/" element={<Navigate replace to="/login" />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path="/register" element={<RegisterForm />} />
          <Route path="/redirect" element={<Redirect />} />

          <Route
          path= "/home"
          element= {
            <RequireAuth>
            <Home/> 
          </RequireAuth>
          }
          />
          <Route path="/*" element={<NotFound />} />
        </Routes>
      </UserProvider>
    </BrowserRouter>
  );
}

export default App;
