// LoginForm.jsx
import { useState } from 'react';
import { useUserContext } from '../context/userContext';
import { Navigate } from "react-router-dom";
import './login.css';

const LoginForm = () => {
  const { login, token } = useUserContext();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(false); 

  const onChange = (e, save) => {
    save(e.target.value);
  };

  const onSubmitHandler = async (e) => {
    e.preventDefault();

    const logged = await login(username, password);

    setError(!logged);
    setUsername("");
    setPassword("");
  };
  if (token) {
    return <Navigate replace to="/redirect" />
  }
  return (
    <div className="login-form">
      <h2>Login</h2>
      <form
        onSubmit={onSubmitHandler}
      >
        {error && (
          <p className="w-full rounded p-3 text-center text-white font-roboto bg-red-700 select-none">
            Un error ha ocurrido en el inicio de sesión
          </p>
        )}
        <input type="text" placeholder="Username"
          value={username}
          onChange={(e) => onChange(e, setUsername)}
        />
        <input
          type="password"
          placeholder="Password"
          onChange={(e) => onChange(e, setPassword)}
          value={password}
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default LoginForm;
