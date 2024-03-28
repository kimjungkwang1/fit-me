import React, { useEffect, useState } from 'react';
import Order from './order';
import Modify from './modify';
import ItemList from '../../components/MyPage/ItemList';
import { useLocation, useNavigate } from 'react-router-dom';

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

const Mypage: React.FC = () => {
  // 현재 선택된 탭을 관리하기 위한 상태
  const [selectedTab, setSelectedTab] = useState<string>('bought');
  let query = useQuery();
  let navigate = useNavigate();
  let location = useLocation();

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
      selectedTab === tabName ? 'bg-black text-white' : 'bg-white hover:underline'
    } focus:outline-none`;
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
        return <Modify />;
    }
  };

  return (
    <>
      <div className='bg-white'>
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
