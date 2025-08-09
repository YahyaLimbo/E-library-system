import apiClient from './apiConfig';

export const authService = {
  login: async (credentials) => {
    try {
      const response = await apiClient.post('/auth/login', credentials);
      const { token } = response.data;
      
      if (token) {
        localStorage.setItem('token', token);
        console.log('🚀 Token saved, dispatching authStateChanged event');
        // Dispatch custom event to notify components of auth state change
        window.dispatchEvent(new Event('authStateChanged'));
      }
      
      return response.data;
    } catch (error) {
      throw error.response?.data || { message: 'Login failed' };
    }
  },

  logout: () => {
    localStorage.removeItem('token');
    // Dispatch custom event to notify components of auth state change
    window.dispatchEvent(new Event('authStateChanged'));
  },

  getCurrentUser: () => {
    const token = localStorage.getItem('token');
    if (!token) return null;
    
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload;
    } catch (error) {
      localStorage.removeItem('token');
      return null;
    }
  },

  register: async (userData) => {
    try {
      const response = await apiClient.post('/auth/register', userData);
      return response.data;
    } catch (error) {
      throw error.response?.data || { message: 'Registration failed' };
    }
  },

  isAuthenticated: () => {
    const token = localStorage.getItem('token');
    if (!token) return false;
    
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp > Date.now() / 1000;
    } catch (error) {
      localStorage.removeItem('token');
      return false;
    }
  }
};