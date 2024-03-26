import React from 'react';
import loginImg from '../assets/images/kakao_login.png';
import loginBg from '../assets/images/login_bg.png';

const Login: React.FC = () => {
  const KAKAO_CODE_URL = 'https://fit-me.site/api/auth/login/oauth2/authorization/kakao';
  const handleLogin = () => {
    window.location.href = KAKAO_CODE_URL;
  };

  return (
    <>
      <div
        className='bg-cover bg-center h-full flex justify-center items-center'
        style={{ backgroundImage: `url(${loginBg})` }}
      >
        <button className='w-9/12' onClick={handleLogin}>
          <img src={loginImg} alt='카카오 로그인' />
        </button>
      </div>
    </>
  );
};

export default Login;
