import axios from 'axios';
import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const KakaoAuthHandler: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const code = searchParams.get('code');

    if (code) {
      console.log('받은 코드:', code);
      axios
        .get('https://fit-me.site/api/auth/login/oauth2/code/kakao' + code)
        .then((res) => {
          if (res.status === 200) {
            let accessToken = res.headers.Authorization;
            let refreshToken = res.headers.AuthorizationRefresh;
            console.log('refresh 토큰 :', refreshToken);
            console.log('access 토큰 :', accessToken);
            console.log('로그인 성공');
            navigate('/signup');
          } else {
            alert('로그인 오류');
            navigate('/home');
          }
        })
        .catch((error) => console.log(error));
    }
  }, [location, navigate]);

  return <div>카카오 로그인 처리 중...</div>;
};

export default KakaoAuthHandler;
