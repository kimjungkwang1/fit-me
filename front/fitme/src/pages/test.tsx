import React from 'react';
// TestPage에 import하고 싶은 페이지 경로를 적어주세요.
import TestPage from '../pages/login';

const test: React.FC = () => {
  return (
    <>
      <TestPage></TestPage>
    </>
  );
};

export default test;
