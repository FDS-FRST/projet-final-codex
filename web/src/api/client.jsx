// src/api/client.js

// Utilise l'URL définie dans .env ou par défaut '/api' (pour le proxy Vite)
const API_BASE = import.meta.env.VITE_API_URL || '/api';

// Construit les en-têtes avec le token JWT s'il existe
function getHeaders() {
  const token = localStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    ...(token && { 'Authorization': `Bearer ${token}` })
  };
}

// Gère la réponse HTTP : si erreur, lance une exception avec le message
async function handleResponse(response) {
  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || `Erreur HTTP ${response.status}`);
  }
  return response.json();
}

// Requête GET
export async function fetchGet(endpoint) {
  const res = await fetch(`${API_BASE}${endpoint}`, {
    headers: getHeaders()
  });
  return handleResponse(res);
}

// Requête POST
export async function fetchPost(endpoint, data) {
  const res = await fetch(`${API_BASE}${endpoint}`, {
    method: 'POST',
    headers: getHeaders(),
    body: JSON.stringify(data)
  });
  return handleResponse(res);
}

// Requête PATCH
export async function fetchPatch(endpoint, data) {
  const res = await fetch(`${API_BASE}${endpoint}`, {
    method: 'PATCH',
    headers: getHeaders(),
    body: JSON.stringify(data)
  });
  return handleResponse(res);
}