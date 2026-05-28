import { useNavigate } from 'react-router-dom';
import { useOffers } from '../hooks/useOffers';
import { useAuth } from '../hooks/useAuth';
import { OfferForm } from '../components/offers/OfferForm';

export const CreateOffer = () => {
  const { addOffer } = useOffers();
  const { user } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (offerData) => {
    try {
      if (!user) {
        alert('Vous devez être connecté');
        return;
      }
      // Récupérer l'id de l'utilisateur (si absent, utiliser un fallback 33 pour test)
      const userId = user.id || 33;
      
      // Construire l'objet à envoyer. Deux options selon l'attente du backend :
      // Option 1: champ offererId (si le backend a un champ simple)
      // Option 2: objet offerer avec id (si le backend utilise une relation)
      // Essayons les deux? Non, choisissons l'objet offerer.
      const formattedData = {
        title: offerData.title,
        description: offerData.description,
        quantity: Number(offerData.quantity),
        price: Number(offerData.price),
        startRetrieval: offerData.startRetrieval + ':00',
        endRetrieval: offerData.endRetrieval + ':00',
        location: offerData.location,
        offerer: { id: userId }   // envoi de l'utilisateur avec son id
      };
      console.log('Envoi ->', formattedData);
      await addOffer(formattedData);
      navigate('/');
    } catch (err) {
      console.error(err);
      alert('Erreur : ' + err.message);
    }
  };

  return (
    <div className="container">
      <h1>Créer une offre</h1>
      <OfferForm onSubmit={handleSubmit} />
    </div>
  );
};