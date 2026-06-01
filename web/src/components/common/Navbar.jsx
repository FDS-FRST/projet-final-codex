import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';

export const Navbar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => { logout(); navigate('/login'); };

  if (!user) return null;

  return (
    <nav style={{ background: '#1f2937', color: 'white', padding: '1rem', display: 'flex', justifyContent: 'space-between' }}>
      <Link to="/" style={{ color: 'white', textDecoration: 'none', fontWeight: 'bold' }}>FoodShare Offreur</Link>
      <div>
        <span style={{ marginRight: '1rem' }}>{user.name}</span>
        <button onClick={handleLogout} className="btn btn-secondary">Déconnexion</button>
      </div>
    </nav>
  );
};