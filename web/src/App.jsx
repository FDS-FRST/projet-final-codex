import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { useAuth } from './hooks/useAuth';
import { Navbar } from './components/common/Navbar';
import { Login } from './pages/Login';
import { Register } from './pages/Register';
import { Dashboard } from './pages/Dashboard';
import { CreateOffer } from './pages/CreateOffer';
import { OfferDetail } from './pages/OfferDetail';

const ProtectedRoute = ({ children }) => {
  const { user, loading } = useAuth();
  if (loading) return <p>Chargement...</p>;
  if (!user) return <Navigate to="/login" />;
  // Vérification insensible à la casse et au préfixe ROLE_
  const userRole = (user.role || '').toUpperCase().replace('ROLE_', '');
  if (userRole !== 'OFFREUR') return <Navigate to="/login" />;
  return children;
};

function AppContent() {
  const { user } = useAuth();
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/login" element={!user ? <Login /> : <Navigate to="/" />} />
        <Route path="/register" element={!user ? <Register /> : <Navigate to="/" />} />
        <Route path="/" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
        <Route path="/create-offer" element={<ProtectedRoute><CreateOffer /></ProtectedRoute>} />
        <Route path="/offers/:id" element={<ProtectedRoute><OfferDetail /></ProtectedRoute>} />
      </Routes>
    </>
  );
}

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <AppContent />
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;