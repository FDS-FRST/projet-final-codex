const API_BASE = import.meta.env.VITE_API_URL;

function getHeaders() {
  const token = localStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    ...(token && { 'Authorization': `Bearer ${token}` })
  };
}

export async function fetchGet(endpoint) {
  const res = await fetch(`${API_BASE}${endpoint}`, {
    headers: getHeaders()
  });
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

export async function fetchPost(endpoint, data) {
  const res = await fetch(`${API_BASE}${endpoint}`, {
    method: 'POST',
    headers: getHeaders(),
    body: JSON.stringify(data)
  });
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

export async function fetchPatch(endpoint, data) {
  const res = await fetch(`${API_BASE}${endpoint}`, {
    method: 'PATCH',
    headers: getHeaders(),
    body: JSON.stringify(data)
  });
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}