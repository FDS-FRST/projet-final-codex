import { fetchPost, fetchGet } from './client';

export async function register(userData) {
  return fetchPost('/auth/register', userData);
}

export async function login(credentials) {
  return fetchPost('/auth/login', credentials);
}

export async function getCurrentUser() {
  return fetchGet('/auth/me');
}