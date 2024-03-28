import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Tooltip } from 'flowbite-react';
import { isAuthenticated } from '../services/auth';

const HeaderBar: React.FC = () => {
  const [currentTab, setCurrentTab] = useState<string>('mypage');
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    if (location.pathname === '/' || location.pathname === '/home') {
      setCurrentTab('home');
    } else if (location.pathname === '/mypage') {
      setCurrentTab('mypage');
    } else if (location.pathname === '/login') {
      setCurrentTab('login');
    } else if (location.pathname === '/signup') {
      setCurrentTab('signup');
    } else if (location.pathname === '/category') {
      setCurrentTab('category');
    } else if (location.pathname === '/dressroom') {
      setCurrentTab('dressroom');
    } else if (location.pathname === '/search') {
      setCurrentTab('search');
    } else if (location.pathname === '/feed') {
      setCurrentTab('feed');
    } else if (location.pathname.startsWith('/feed/')) {
      setCurrentTab('feedDetail');
    } else if (location.pathname === '/cart') {
      setCurrentTab('cart');
    } else {
      setCurrentTab('');
    }
  }, [location.pathname]);

  const handleBack = () => {
    navigate(-1);
  };

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    navigate('/');
  };

  let left, right, content;
  if (currentTab === 'mypage' || currentTab === 'home') {
    let additionalElement;
    const basicElement = (
      <button key='alertElement'>
        <Tooltip content='알림'>
          <svg
            xmlns='http://www.w3.org/2000/svg'
            fill='none'
            viewBox='0 0 24 24'
            strokeWidth={1.5}
            stroke='currentColor'
            className='w-8 h-8'
          >
            <path
              strokeLinecap='round'
              strokeLinejoin='round'
              d='M14.857 17.082a23.848 23.848 0 0 0 5.454-1.31A8.967 8.967 0 0 1 18 9.75V9A6 6 0 0 0 6 9v.75a8.967 8.967 0 0 1-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 0 1-5.714 0m5.714 0a3 3 0 1 1-5.714 0'
            />
          </svg>
        </Tooltip>
      </button>
    );
    if (currentTab === 'mypage' && isAuthenticated()) {
      additionalElement = (
        <button key='logoutElement' onClick={handleLogout}>
          <Tooltip content='로그아웃'>
            <svg
              xmlns='http://www.w3.org/2000/svg'
              viewBox='0 0 1024 1024'
              strokeWidth={1.5}
              stroke='currentColor'
              className='w-8 h-8'
            >
              <path
                d='M768 106V184c97.2 76 160 194.8 160 328 0 229.6-186.4 416-416 416S96 741.6 96 512c0-133.2 62.8-251.6 160-328V106C121.6 190.8 32 341.2 32 512c0 265.2 214.8 480 480 480s480-214.8 480-480c0-170.8-89.6-321.2-224-406z'
                strokeLinecap='round'
                strokeLinejoin='round'
              />
              <path
                d='M512 32c-17.6 0-32 14.4-32 32v448c0 17.6 14.4 32 32 32s32-14.4 32-32V64c0-17.6-14.4-32-32-32z'
                strokeLinecap='round'
                strokeLinejoin='round'
              />
            </svg>
          </Tooltip>
        </button>
      );
    } else {
    }
    left = [
      <React.Fragment key='basicElement'>{basicElement}</React.Fragment>,
      <React.Fragment key='additionalElement'>{additionalElement}</React.Fragment>,
    ];
  } else if (currentTab === 'dressroom' || currentTab === 'feed' || currentTab === 'category') {
  } else {
    left = (
      <button onClick={handleBack}>
        <Tooltip content='이전으로'>
          <svg
            xmlns='http://www.w3.org/2000/svg'
            fill='none'
            viewBox='0 0 24 24'
            strokeWidth={1.5}
            stroke='currentColor'
            className='w-8 h-8'
          >
            <path strokeLinecap='round' strokeLinejoin='round' d='M15.75 19.5 8.25 12l7.5-7.5' />
          </svg>
        </Tooltip>
      </button>
    );
  }

  if (currentTab === 'signup') {
    right = <div></div>;
  } else {
    let additionalElement;
    const basicElement = (
      <button onClick={() => navigate('/search')} key='basicElement'>
        <Tooltip content='검색'>
          <svg
            xmlns='http://www.w3.org/2000/svg'
            fill='none'
            viewBox='0 0 24 24'
            strokeWidth={1.5}
            stroke='currentColor'
            className='w-8 h-8'
          >
            <path
              strokeLinecap='round'
              strokeLinejoin='round'
              d='m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z'
            />
          </svg>
        </Tooltip>
      </button>
    );
    if (currentTab === 'cart') {
    } else if (currentTab === 'feed' || currentTab === 'feedDetail') {
      additionalElement = (
        <button onClick={() => navigate('/feed/myfeed')} key='additionalElement'>
          <Tooltip content='나의 피드'>
            <svg
              xmlns='http://www.w3.org/2000/svg'
              fill='none'
              viewBox='0 0 24 24'
              strokeWidth={1.5}
              stroke='currentColor'
              className='w-8 h-8'
            >
              <path
                strokeLinecap='round'
                strokeLinejoin='round'
                d='M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a9 9 0 1 0-11.963 0m11.963 0A8.966 8.966 0 0 1 12 21a8.966 8.966 0 0 1-5.982-2.275M15 9.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z'
              />
            </svg>
          </Tooltip>
        </button>
      );
    } else {
      additionalElement = (
        <button onClick={() => navigate('/cart')} key='additionalElement'>
          <Tooltip content='장바구니'>
            <svg
              xmlns='http://www.w3.org/2000/svg'
              fill='none'
              viewBox='0 0 24 24'
              strokeWidth={1.5}
              stroke='currentColor'
              className='w-8 h-8'
            >
              <path
                strokeLinecap='round'
                strokeLinejoin='round'
                d='M15.75 10.5V6a3.75 3.75 0 1 0-7.5 0v4.5m11.356-1.993 1.263 12c.07.665-.45 1.243-1.119 1.243H4.25a1.125 1.125 0 0 1-1.12-1.243l1.264-12A1.125 1.125 0 0 1 5.513 7.5h12.974c.576 0 1.059.435 1.119 1.007ZM8.625 10.5a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm7.5 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z'
              />
            </svg>
          </Tooltip>
        </button>
      );
    }
    right = [
      <React.Fragment key='basicElement'>{basicElement}</React.Fragment>,
      <React.Fragment key='additionalElement'>{additionalElement}</React.Fragment>,
    ];
  }

  if (currentTab === 'signup') {
    content = '회원 정보 입력';
  } else if (currentTab === 'mypage') {
    content = '마이페이지';
  } else if (currentTab === 'feed' || currentTab === 'feedDetail') {
    content = '피드';
  } else {
    content = 'Fit Me';
  }

  return (
    <header className='h-[55px]'>
      {currentTab !== 'login' && (
        <div className='h-[55px] bg-white flex border-b-2 border-gray-300'>
          <div className='w-28 pl-4 mt-1 flex items-center'>{left}</div>
          <div className='mt-1 grow flex items-center justify-center text-xl font-bold'>
            {content}
          </div>
          <div className='w-28 pr-4 mt-1 flex items-center justify-end'>{right}</div>
        </div>
      )}
    </header>
  );
};

export default HeaderBar;
