import React, { createContext, useState, useEffect } from 'react';
import { login as apiLogin, register as apiRegister } from '../api/auth';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Au lieu d'appeler /me (qui est bloqué par CORS), on restaure l'utilisateur depuis localStorage
    const token = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');
    if (token && storedUser) {
      setUser(JSON.parse(storedUser));
    }
    setLoading(false);
  }, []);

  const login = async (credentials) => {
    const data = await apiLogin(credentials);
    // Si le backend ne renvoie pas user, on le construit
    if (!data.user && data.token) {
      data.user = {
        email: credentials.email,
        role: 'OFFREUR',
        name: credentials.email.split('@')[0]
      };
    }
    // Normalisation du rôle
    if (data.user?.role) {
      data.user.role = data.user.role.toUpperCase().replace('ROLE_', '');
    }
    localStorage.setItem('token', data.token);
    localStorage.setItem('user', JSON.stringify(data.user));
    setUser(data.user);
    return data;
  };

  const register = async (userData) => {
    const data = await apiRegister(userData);
    if (!data.user && data.token) {
      data.user = { ...userData, role: userData.role || 'OFFREUR' };
    }
    if (data.user?.role) {
      data.user.role = data.user.role.toUpperCase().replace('ROLE_', '');
    }
    localStorage.setItem('token', data.token);
    localStorage.setItem('user', JSON.stringify(data.user));
    setUser(data.user);
    return data;
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