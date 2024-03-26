import axios from 'axios';
import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const KakaoAuthHandler: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  let count = 0;

  useEffect(() => {
    if (count === 0) {
      count++;
      console.log(count);
      const searchParams = new URLSearchParams(location.search);
      console.log('여기까지 1');
      const code = searchParams.get('code');
      console.log('여기까지 2');

      if (code) {
        console.log('여기까지 3');

        console.log('받은 코드:', code);
        const AXIOS_URL = 'https://fit-me.site/api/auth/login/oauth2/code/kakao?code=' + code;
        console.log(AXIOS_URL);

        console.log('여기까지 4');

        axios
          .get(AXIOS_URL)
          .then((res) => {
            console.log(res.status);
            console.log('여기까지 5');

            if (res.status === 200) {
              console.log('여기까지 왔다');
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
