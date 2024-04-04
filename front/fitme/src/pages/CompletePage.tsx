import { Button } from 'flowbite-react';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import completeImg from '../assets/images/complete.png';
export default function CompletePage() {
  const navigate = useNavigate();

  return (
    <>
      <div className='items-center'>
        <div className='flex flex-col items-center'>
          <img src={completeImg} alt='' className='w-[40%] min-w-[200px]' />
          <div> 주문이 완료되었습니다.</div>
          <Button className='w-[50%] m-2  ' color='gray' onClick={() => navigate('/')}>
            홈으로 가기
          </Button>
        </div>
      </div>
    </>
  );
}
