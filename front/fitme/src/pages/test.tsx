import React from 'react';
// TestPage에 import하고 싶은 페이지 경로를 적어주세요.
import TestPage from '../pages/dressroom';

const test: React.FC = () => {
  return (
    <>
      <div className='flex justify-center'>
        <div className='aspect-iphone h-screen w-screen'>
          <TestPage></TestPage>
        </div>
      </div>
    </>
  );
};

export default test;
