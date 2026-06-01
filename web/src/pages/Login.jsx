import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { Input } from '../components/common/Input';
import { Button } from '../components/common/Button';

export const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await login({ email, password });
      console.log('Login réussi, utilisateur:', response.user);
      navigate('/');
    } catch (err) {
      console.error('Erreur login:', err);
      setError(err.message || 'Échec de connexion');
    }
  };

  return (
    <div className="container" style={{ maxWidth: '400px', marginTop: '2rem' }}>
      <h2>Connexion Offreur</h2>
      <form onSubmit={handleSubmit}>
        <Input label="Email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        <Input label="Mot de passe" type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <Button type="submit">Se connecter</Button>
      </form>
      <p style={{ marginTop: '1rem' }}>Pas encore de compte ? <Link to="/register">Inscription</Link></p>
    </div>
  );
};