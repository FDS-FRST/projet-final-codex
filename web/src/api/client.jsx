const API_BASE = import.meta.env.VITE_API_URL;

function getHeaders() {
  const token = localStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    ...(token && { 'Authorization': `Bearer ${token}` })
  };
}

async function handleResponse(response) {
  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || `Erreur HTTP ${response.status}`);
  }
  return response.json();
}

export async function fetchGet(endpoint) {
  const res = await fetch(`${API_BASE}${endpoint}`, {
    headers: getHeaders()
  });
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