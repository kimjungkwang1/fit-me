import React, { useState } from 'react';
import Order from './order';

const Mypage: React.FC = () => {
  // 현재 선택된 탭을 관리하기 위한 상태
  const [selectedTab, setSelectedTab] = useState<string>('bought');

  // 버튼의 스타일을 결정하는 함수
  const getButtonStyle = (tabName: string) => {
    return `text-gray-600 w-1/4 py-4 px-6 block ${
      selectedTab === tabName ? 'bg-black text-white' : 'bg-white hover:underline'
    } focus:outline-none`;
  };

  const getContent = (tabName: string) => {
    switch (tabName) {
      case 'order':
        return <Order />;
    }
  };

  return (
    <>
      <div className='bg-white'>
        <nav className='flex'>
          <button className={getButtonStyle('bought')} onClick={() => setSelectedTab('bought')}>
            구매 목록
          </button>
          <button className={getButtonStyle('fav')} onClick={() => setSelectedTab('fav')}>
            좋아요 한 상품
          </button>
          <button className={getButtonStyle('order')} onClick={() => setSelectedTab('order')}>
            주문내역
          </button>
          <button className={getButtonStyle('modify')} onClick={() => setSelectedTab('modify')}>
            정보 수정
          </button>
        </nav>
      </div>
      {getContent(selectedTab)}
    </>
  );
};

export default Mypage;
