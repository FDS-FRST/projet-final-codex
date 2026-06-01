import React, { createContext, useState, useEffect } from 'react';
import { login as apiLogin, register as apiRegister } from '../api/auth';

// Décode le JWT pour extraire l'ID
function decodeJWT(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)).join(''));
    return JSON.parse(jsonPayload);
  } catch (e) {
    console.error('Erreur décodage JWT:', e);
    return null;
  }
}

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');
    if (token && storedUser) {
      setUser(JSON.parse(storedUser));
    }
    setLoading(false);
  }, []);

  const login = async (credentials) => {
    const data = await apiLogin(credentials);
    const token = data.token;
    let userId = null;
    const decoded = decodeJWT(token);
    if (decoded) {
      userId = decoded.userId || decoded.id || decoded.sub;
    }
    let userObj = data.user;
    if (!userObj && token) {
      userObj = { email: credentials.email, role: 'OFFREUR' };
    }
    if (userId && userObj) {
      userObj.id = userId;
    }
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(userObj));
    setUser(userObj);
    return { ...data, user: userObj };
  };

  const register = async (userData) => {
    const data = await apiRegister(userData);
    const token = data.token;
    let userId = null;
    const decoded = decodeJWT(token);
    if (decoded) {
      userId = decoded.userId || decoded.id || decoded.sub;
    }
    let userObj = data.user;
    if (!userObj && token) {
      userObj = { ...userData, role: userData.role || 'OFFREUR' };
    }
    if (userId && userObj) {
      userObj.id = userId;
    }
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(userObj));
    setUser(userObj);
    return { ...data, user: userObj };
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, loading, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};