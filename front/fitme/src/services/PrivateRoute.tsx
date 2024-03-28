// PrivateRoute.tsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { isAuthenticated } from './auth';
import LoginConfirm from '../components/user/LoginConfirm';

interface PrivateRouteProps {
  element: React.ReactElement;
}

export const PrivateRoute: React.FC<PrivateRouteProps> = ({ element }) => {
  const navigate = useNavigate();
  const [showPage, setPage] = useState(false);

  const handleConfirm = () => {
    navigate('/login');
  };

  const handleClose = () => {
    navigate(-1);
  };

  if (isAuthenticated()) {
    return element;
  } else {
    if (!showPage) {
      setPage(true);
      return <div style={{ visibility: 'hidden' }}>Loading...</div>;
    } else {
      return <LoginConfirm onConfirm={handleConfirm} onClose={handleClose} />;
    }
  }
};
