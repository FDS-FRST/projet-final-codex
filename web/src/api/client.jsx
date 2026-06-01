// src/api/client.js
const API_BASE = import.meta.env.VITE_API_URL || '/api';

function getHeaders() {
  const token = localStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    ...(token && { 'Authorization': `Bearer ${token}` })
  };
}

async function handleResponse(response) {
  if (!response.ok) {
    let errorMessage = `Erreur HTTP ${response.status}`;
    try {
      const errorData = await response.json();
      errorMessage = errorData.error || errorData.message || JSON.stringify(errorData);
    } catch (e) {
      const text = await response.text();
      if (text) errorMessage = text;
    }
    console.error('Erreur backend:', errorMessage);
    throw new Error(errorMessage);
  }
  return response.json();
}

export async function fetchGet(endpoint) {
  const res = await fetch(`${API_BASE}${endpoint}`, { headers: getHeaders() });
  return handleResponse(res);
}

export async function fetchPost(endpoint, data) {
  const res = await fetch(`${API_BASE}${endpoint}`, {
    method: 'POST',
    headers: getHeaders(),
    body: JSON.stringify(data)
  });
  return handleResponse(res);
}

export async function fetchPatch(endpoint, data) {
  const res = await fetch(`${API_BASE}${endpoint}`, {
    method: 'PATCH',
    headers: getHeaders(),
    body: JSON.stringify(data)
  });
  return handleResponse(res);
}

export async function fetchDelete(endpoint) {
  const res = await fetch(`${API_BASE}${endpoint}`, {
    method: 'DELETE',
    headers: getHeaders()
  });
  return handleResponse(res);
}