export const formatDateTime = (isoString) => {
  const date = new Date(isoString);
  return date.toLocaleString('fr-FR', {
    day: '2-digit', month: '2-digit', year: 'numeric',
    hour: '2-digit', minute: '2-digit'
  });
};

export const isActive = (start, end) => {
  const now = new Date();
  return now >= new Date(start) && now <= new Date(end);
};