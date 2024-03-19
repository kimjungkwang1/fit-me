import Test from './pages/test';
import { Route, Routes } from 'react-router-dom';
import MainPage from './pages/MainPage';
import LoginPage from './pages/login';
import SignupPage from './pages/signup';
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

function App() {
  return (
    <>
      <div className='flex justify-center'>
        <div className='aspect-iphone h-screen select-none flex flex-col '>
          <HeaderBar />
          <div className='flex-grow overflow-auto hide-scrollbar'>
            <Routes>
              {/* Test 페이지 남겨뒀습니다 */}
              <Route path='/test' element={<Test />} />
              <Route path='/' element={<MainPage />} />
              <Route path='/home' element={<MainPage />} />
              <Route path='/login' element={<LoginPage />} />
              <Route path='/signup' element={<SignupPage />} />
              <Route path='/category' element={<CategoryPage />} />
              <Route path='/detail/:item_code' element={<ItemDetailPage />} />
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
    </>
  );
}

export default App;
