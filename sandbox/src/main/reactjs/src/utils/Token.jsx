import { jwtDecode } from "jwt-decode";

export const getTokenData = () => {
  const token = sessionStorage.getItem('token');
  if (token) {
    try {
      const decodedToken = jwtDecode(token);
      return decodedToken;
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  } else {
    console.error('Token not found in sessionStorage');
    return null;
  }
};

