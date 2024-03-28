import { useEffect } from 'react';
import { Route, Routes } from 'react-router-dom';
import { PrivateRoute } from './services/PrivateRoute';
import MainPage from './pages/MainPage';
import LoginPage from './pages/login';
import SignupPage from './pages/signup';
import KakaoAuthHandler from './services/KakaoAuthHandler';
import CategoryPage from './pages/CategoryPage';
import ItemDetailPage from './pages/ItemDetailPage';
import SearchPage from './pages/SearchPage';
import CartPage from './pages/cart';
import DressroomPage from './pages/dressroom';
import MyPage from './pages/MyPage/mypage';
import FeedPage from './pages/FeedPage';
import FeedDetailPage from './pages/FeedDetailPage';
import MyFeedPage from './pages/MyFeedPage';
import FeedWritePage from './pages/FeedWritePage';
import HeaderBar from './components/headerBar';
import FooterBar from './components/footerBar';
import { Provider } from 'react-redux';
import { store } from './store/store';
import NotFoundPage from './pages/NotFoundPage';

const App: React.FC = () => {
  useEffect(() => {
    document.title = 'Fit-Me';
  }, []);

  return (
    <>
      <Provider store={store}>
        <div className='flex justify-center'>
          <div className='max-w-[600px] h-screen select-none flex flex-col '>
            <HeaderBar />
            <div className='flex-grow overflow-auto hide-scrollbar'>
              <Routes>
                <Route path='/' element={<MainPage />} />
                <Route path='/home' element={<MainPage />} />
                <Route path='/login' element={<LoginPage />} />
                <Route path='/signup' element={<SignupPage />} />
                <Route path='/auth/login/oauth2/code/kakao' element={<KakaoAuthHandler />} />
                <Route path='/category' element={<CategoryPage />} />
                <Route path='/detail/:item_id' element={<ItemDetailPage />} />
                <Route path='/search' element={<SearchPage />} />
                <Route path='/cart' element={<CartPage />} />
                {/* /payment/complete 페이지 아직 없음 */}
                <Route path='/payment/complete' element={<LoginPage />} />
                <Route path='/dressroom' element={<DressroomPage />} />
                <Route path='/mypage' element={<MyPage />} />
                <Route path='/feed' element={<FeedPage />} />
                <Route path='/feed/:feed_no' element={<FeedDetailPage />} />
                <Route path='/feed/myfeed' element={<MyFeedPage />} />
                <Route path='/feed/write' element={<FeedWritePage />} />
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
