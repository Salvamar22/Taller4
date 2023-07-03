// RegisterForm.jsx
import React from 'react';
import './register.css';

const RegisterForm = () => {
  return (
    <div className="register-form">
      <h2>Register</h2>
      <form>
        <input type="text" placeholder="Username" />
        <input type="password" placeholder="Password" />
        <input type="password" placeholder="Confirm Password" />
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default RegisterForm;
