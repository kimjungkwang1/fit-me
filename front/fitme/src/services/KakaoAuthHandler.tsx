import React, { useEffect } from 'react';
import { useLocation } from 'react-router-dom';

const KakaoAuthHandler: React.FC = () => {
  const location = useLocation();

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const code = searchParams.get('code');

    if (code) {
      console.log('받은 코드:', code);
    }
  }, [location]);

  return <div>카카오 로그인 처리 중...</div>;
};

export default KakaoAuthHandler;
