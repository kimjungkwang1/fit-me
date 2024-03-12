import React from 'react';
import kakao from '../assets/images/kakao_login.png';
import loginBg from '../assets/images/login_bg.png';

const login: React.FC = () => {
  return (
    <>
      <div className='flex justify-center'>
        <div className='aspect-iphone h-screen'>
          <div
            className='bg-cover bg-center h-full flex justify-center items-center'
            style={{ backgroundImage: `url(${loginBg})` }}
          >
            <img src={kakao} alt='' className='w-9/12' />
          </div>
        </div>
      </div>
    </>
  );
};

export default login;
