import React, { useEffect, useState } from 'react';
import Order from './order';
import Modify from './modify';
import ItemList from '../../components/MyPage/ItemList';
import { useLocation, useNavigate } from 'react-router-dom';
import { api } from '../../services/api';

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

type ApiDataType = {
  id: number;
  nickname: string;
  gender: boolean;
  profileUrl: string;
  phoneNumber: string;
  birthYear: number;
  address: string;
};

const Mypage: React.FC = () => {
  const [apiData, setApiData] = useState<ApiDataType>();
  // 현재 선택된 탭을 관리하기 위한 상태
  const [selectedTab, setSelectedTab] = useState<string>('bought');
  let query = useQuery();
  let navigate = useNavigate();
  let location = useLocation();

  useEffect(() => {
    api
      .get('/api/members')
      .then((res) => {
        setApiData(res.data);
      })
      .catch((error) => {
        console.error('서버로부터 에러 응답:', error);
      });
  }, []);

  useEffect(() => {
    let tab = query.get('tab');
    const validTabs = ['bought', 'fav', 'order', 'modify'];

    if (tab && validTabs.includes(tab)) {
      // 'tab' 파라미터가 유효한 경우 해당 탭으로 설정합니다.
      setSelectedTab(tab);
    } else {
      // 'tab' 파라미터가 없거나 유효하지 않은 경우 기본값으로 설정하고 리다이렉트합니다.
      navigate('/mypage?tab=bought', { replace: true });
    }
  }, [query, navigate, location.pathname, location.search]);

  // 버튼의 스타일을 결정하는 함수
  const getButtonStyle = (tabName: string) => {
    return `text-gray-600 w-1/4 py-4 px-6 block ${
      selectedTab === tabName ? 'bg-darkgray text-white' : 'bg-white hover:underline'
    } focus:outline-none`;
  };

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    navigate('/');
  };

  const changeTab = (tabName: string) => {
    setSelectedTab(tabName);
    navigate(`/mypage?tab=${tabName}`);
  };

  const getContent = (tabName: string) => {
    switch (tabName) {
      case 'bought':
        return <ItemList tabName={tabName} />;
      case 'fav':
        return <ItemList tabName={tabName} />;
      case 'order':
        return <Order />;
      case 'modify':
        return <>{apiData && <Modify userInfo={apiData} />}</>;
    }
  };

  return (
    <>
      <div className='bg-white'>
        <div className='h-14 bg-beige flex justify-between py-2 px-5'>
          <div className='flex items-center'>
            <div className='font-bold text-lg'>{apiData?.nickname}</div>
            <div>님 안녕하세요!</div>
          </div>
          <button onClick={handleLogout} className='bg-darkgray text-white px-3 rounded-md'>
            로그아웃
          </button>
        </div>
        <nav className='flex'>
          <button className={getButtonStyle('bought')} onClick={() => changeTab('bought')}>
            구매 목록
          </button>
          <button className={getButtonStyle('fav')} onClick={() => changeTab('fav')}>
            좋아요
          </button>
          <button className={getButtonStyle('order')} onClick={() => changeTab('order')}>
            주문내역
          </button>
          <button className={getButtonStyle('modify')} onClick={() => changeTab('modify')}>
            정보 수정
          </button>
        </nav>
      </div>
      {getContent(selectedTab)}
    </>
  );
};

export default Mypage;
