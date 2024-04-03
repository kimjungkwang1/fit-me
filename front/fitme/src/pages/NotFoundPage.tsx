import React from 'react';
import { useNavigate } from 'react-router-dom';
import NotFound from '../assets/images/notfound.png';

const NotFoundPage: React.FC = () => {
  const navigate = useNavigate();
  return (
    <div className='flex flex-col justify-center items-center h-full gap-8'>
      <img src={NotFound} alt='notfound' className='w-[90%]' />
      <div className='text-3xl'>
        <div>잘못된 경로 혹은 접근입니다.</div>
      </div>
      <div className='w-1/3 h-10 flex justify-around'>
        <button
          onClick={() => {
            navigate('/');
          }}
          className='bg-black text-white w-1/3 rounded-lg'
        >
          홈으로
        </button>
      </div>
    </div>
  );
};

export default NotFoundPage;
