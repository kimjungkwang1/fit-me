import React from 'react';
// TestPage에 import하고 싶은 페이지 경로를 적어주세요.
import ItemDetailPage from './ItemDetailPage';

const test: React.FC = () => {
  return (
    <>
      <div className='flex flex-col h-screen'>
        {/* 상단 div, 고정된 높이 설정 */}
        <div className='h-16 bg-red-400'>상단 고정 높이</div>

        {/* 중간 div, flex-grow를 사용해 남은 공간을 모두 차지하게 설정 */}
        <div className='flex-grow overflow-auto bg-green-400'>
          중간 내용 (스크롤 가능)
          {/* 내용이 많을 경우를 대비해 반복적인 내용 추가 */}
          {Array.from({ length: 100 }, (_, i) => (
            <p key={i}>내용 {i + 1}</p>
          ))}
        </div>

        {/* 하단 div, 고정된 높이 설정 */}
        <div className='h-16 bg-blue-400'>하단 고정 높이</div>
      </div>
    </>
  );
};

export default test;
