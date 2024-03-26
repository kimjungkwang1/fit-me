import axios from 'axios';
import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const KakaoAuthHandler: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  let count = 0;
  let accessToken, refreshToken;

  useEffect(() => {
    if (count === 0) {
      count++;
      console.log(count);
      const searchParams = new URLSearchParams(location.search);
      const code = searchParams.get('code');

      if (code) {
        console.log('받은 코드:', code);
        const AXIOS_URL = 'https://fit-me.site/api/auth/login/oauth2/code/kakao?code=' + code;
        console.log(AXIOS_URL);

        axios
          .get(AXIOS_URL)
          .then((res) => {
            console.log(res.status);
            console.log('여기까지 5');

            if (res.status === 200) {
              console.log('여기까지 왔다');
              accessToken = res.headers.Authorization;
              refreshToken = res.headers.AuthorizationRefresh;
              console.log('refresh 토큰 :', res.headers.AuthorizationRefresh);
              console.log('access 토큰 :', res.headers.Authorization);
              console.log('로그인 성공');
              navigate('/signup');
            } else {
              alert('로그인 오류');
              navigate('/home');
            }
          })
          .catch((error) => {
            console.log('여기까지 6');
            console.log('안돼');

            console.log(error);
          });
      }

      console.log('끝까지 왔다');
    }
  }, [location]);

  return <div>카카오 로그인 처리 중...</div>;
};

export default KakaoAuthHandler;
