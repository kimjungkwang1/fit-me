import React, { useEffect, useState } from 'react';
import { Route, Routes } from 'react-router-dom';
import { PrivateRoute } from './services/PrivateRoute';
import MainPage from './pages/MainPage';
import LoginPage from './pages/login';
import SignupPage from './pages/signup';
import KakaoAuthHandler from './services/KakaoAuthHandler';
import CategoryListPage from './pages/CategoryListPage';
import CategoryDetailPage from './pages/CategoryDetailPage';
import ItemDetailPage from './pages/ItemDetailPage';
import SearchPage from './pages/SearchPage';
import CartPage from './pages/cart';
import DressroomPage from './pages/dressroom';
import MyPage from './pages/MyPage/MyPageMain';
import ReviewPage from './pages/MyPage/ReviewPage';
import FeedPage from './pages/FeedPage';
import FeedDetailPage from './pages/FeedDetailPage';
import MyFeedPage from './pages/MyFeedPage';
import FeedWritePage from './pages/FeedWritePage';
import HeaderBar from './components/headerBar';
import FooterBar from './components/footerBar';
import { Provider } from 'react-redux';
import { store } from './store/store';
import NotFoundPage from './pages/NotFoundPage';
import CompletePage from './pages/CompletePage';
import ScrollToTop from './services/ScrollTop';
// import TestPage from './pages/test';

const App: React.FC = () => {
  useEffect(() => {
    document.title = 'Fit-Me';
  }, []);

  return (
    <>
      <Provider store={store}>
        <div id='root' className='flex justify-center'>
          <div className='w-full max-w-[600px] h-dvh select-none flex flex-col '>
            <HeaderBar />
            <div className='w- full flex-grow hide-scrollbar py-[55px]'>
              <Routes>
                <Route path='/' element={<MainPage />} />
                <Route path='/home' element={<MainPage />} />
                <Route path='/login' element={<LoginPage />} />
                <Route path='/signup' element={<SignupPage />} />
                <Route path='/auth/login/oauth2/code/kakao' element={<KakaoAuthHandler />} />
                <Route path='/category' element={<CategoryListPage />} />
                <Route path='/category/:id' element={<CategoryDetailPage />} />
                <Route path='/detail/:item_id' element={<ItemDetailPage />} />
                <Route path='/search' element={<SearchPage />} />
                <Route path='/cart' element={<PrivateRoute element={<CartPage />} />} />
                {/* /payment/complete 페이지 아직 없음 */}
                <Route path='/payment/complete' element={<CompletePage />} />
                <Route path='/dressroom' element={<PrivateRoute element={<DressroomPage />} />} />
                <Route path='/mypage' element={<PrivateRoute element={<MyPage />} />} />
                <Route path='/review' element={<ReviewPage />} />
                <Route path='/feed' element={<FeedPage />} />
                <Route path='/feed/:feed_no' element={<FeedDetailPage />} />
                <Route path='/feed/myfeed' element={<FeedPage />} />
                <Route path='/feed/write' element={<FeedWritePage />} />
                <Route path='*' element={<NotFoundPage />} />
                {/* <Route path='/test' element={<TestPage />} /> */}
              </Routes>
            </div>
            <FooterBar />
          </div>
        </div>
      </Provider>
    </>
  );
};

export default App;
