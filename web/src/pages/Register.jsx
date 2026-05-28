import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { Input } from '../components/common/Input';
import { Button } from '../components/common/Button';

export const Register = () => {
  const [form, setForm] = useState({ name: '', email: '', password: '' });
  const [error, setError] = useState('');
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await register({ ...form, role: 'OFFREUR' });
      navigate('/');
    } catch (err) {
      setError(err.message || 'Erreur inscription');
    }
  };

  return (
    <div className="container" style={{ maxWidth: '400px', marginTop: '2rem' }}>
      <h2>Inscription Offreur</h2>
      <form onSubmit={handleSubmit}>
        <Input label="Nom" name="name" value={form.name} onChange={handleChange} required />
        <Input label="Email" type="email" name="email" value={form.email} onChange={handleChange} required />
        <Input label="Mot de passe" type="password" name="password" value={form.password} onChange={handleChange} required />
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <Button type="submit">S'inscrire</Button>
      </form>
      <p style={{ marginTop: '1rem' }}>Déjà inscrit ? <Link to="/login">Connexion</Link></p>
    </div>
  );
};