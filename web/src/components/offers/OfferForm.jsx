import { useEffect, useState } from 'react';
import { Input } from '../common/Input';
import { Button } from '../common/Button';

const buildFormState = (data) => ({
  title: data?.title || '',
  description: data?.description || '',
  quantity: data?.quantity || 1,
  price: data?.price || 0,
  startRetrieval: data?.startRetrieval || '',
  endRetrieval: data?.endRetrieval || '',
  location: data?.location || '',
});

export const OfferForm = ({ onSubmit, initialData, submitLabel = "Publier l'offre" }) => {
  const [form, setForm] = useState(() => buildFormState(initialData));

  useEffect(() => {
    if (!initialData) {
      return;
    }
    setForm(buildFormState(initialData));
  }, [initialData]);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <form onSubmit={handleSubmit} className="card">
      <Input label="Titre" name="title" value={form.title} onChange={handleChange} required />
      <Input label="Description" name="description" value={form.description} onChange={handleChange} />
      <Input label="Quantité" type="number" name="quantity" value={form.quantity} onChange={handleChange} required min="1" />
      <Input label="Prix (€)" type="number" step="0.01" name="price" value={form.price} onChange={handleChange} required min="0" />
      <Input label="Début retrait" type="datetime-local" name="startRetrieval" value={form.startRetrieval} onChange={handleChange} required />
      <Input label="Fin retrait" type="datetime-local" name="endRetrieval" value={form.endRetrieval} onChange={handleChange} required />
      <Input label="Lieu" name="location" value={form.location} onChange={handleChange} required />
      <Button type="submit">{submitLabel}</Button>
    </form>
  );
};