import { useEffect, useState } from 'react';
import { GoMoveToTop, GoMoveToBottom } from 'react-icons/go';
import { useLocation } from 'react-router-dom';

function TopButton() {
  const location = useLocation();
  const [currentTab, setCurrentTab] = useState('/');
  const [isVisible, setVisible] = useState(true);
  const [toTop, setToTop] = useState(false);

  // 현재 위치 설정
  useEffect(() => {
    if (location.pathname === '/' || location.pathname === '/home') {
      setCurrentTab('home');
    } else if (location.pathname.startsWith('/detail/')) {
      setCurrentTab('detail');
    } else if (location.pathname === '/mypage') {
      setCurrentTab('mypage');
    } else if (location.pathname === '/login') {
      setCurrentTab('login');
    } else if (location.pathname === '/signup') {
      setCurrentTab('signup');
    } else if (location.pathname === '/category') {
      setCurrentTab('category');
    } else if (location.pathname.startsWith('/category/')) {
      setCurrentTab('categoryDetail');
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

  // 현재 위치에 따라 버튼을 보여줄지 말지 결정
  useEffect(() => {
    if (
      currentTab === 'home' ||
      currentTab === 'detail' ||
      currentTab === 'categoryDetail' ||
      currentTab === 'search' ||
      currentTab === 'feed'
    ) {
      setVisible(true);
    } else {
      setVisible(false);
    }
  }, [currentTab]);

  // 스크롤 감지
  useEffect(() => {
    const handleScroll = () => {
      // 1000 이상 아래로 스크롤 된 경우에는 위로 올라가는 버튼
      if (window.scrollY > 1000) {
        setToTop(true);
      } else {
        setToTop(false);
      }
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  // 위로 스크롤
  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth', // 부드러운 스크롤 효과
    });
  };

  // 아래로 스크롤
  const scrollToBottom = () => {
    window.scrollTo({
      top: document.body.scrollHeight,
      behavior: 'smooth',
    });
  };

  return (
    <>
      {isVisible &&
        (toTop ? (
          <div
            onClick={scrollToTop}
            className='w-10 h-10 fixed bottom-[70px] right-[15px] rounded-full bg-white flex flex-col justify-center items-center border border-solid border-darkgray px-1'
          >
            <GoMoveToTop className='text-2xl text-darkgray' />
          </div>
        ) : (
          <div
            onClick={scrollToBottom}
            className='w-10 h-10 fixed bottom-[70px] right-[15px] rounded-full bg-white flex flex-col justify-center items-center border border-solid border-darkgray px-1'
          >
            <GoMoveToBottom className='text-2xl text-darkgray' />
          </div>
        ))}
    </>
  );
}

export default TopButton;
