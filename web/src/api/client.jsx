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
  const rawBody = await response.text();

  const parseBody = () => {
    if (!rawBody) {
      return null;
    }

    try {
      return JSON.parse(rawBody);
    } catch {
      return rawBody;
    }
  };

  const parsedBody = parseBody();

  if (!response.ok) {
    let errorMessage = `Erreur HTTP ${response.status}`;

    if (parsedBody && typeof parsedBody === 'object') {
      errorMessage = parsedBody.error || parsedBody.message || errorMessage;
    } else if (typeof parsedBody === 'string' && parsedBody.trim()) {
      errorMessage = parsedBody;
    }

    console.error('Erreur backend:', errorMessage);
    throw new Error(errorMessage);
  }

  return parsedBody;
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