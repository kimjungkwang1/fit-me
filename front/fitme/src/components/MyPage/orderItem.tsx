import React from 'react';

const OrderList: React.FC = () => {
  return (
    <>
      <div className='w-full flex justify-center py-6'>
        <div className='bg-white w-5/6 h-60 rounded-xl border-4 border-black '>
          <div className='flex justify-between items-center h-1/4 border-b-2 border-black px-5 text-lg'>
            <div>결제 완료</div>
            <div>2024.04.04 결제</div>
          </div>
          <div className='flex justify-around p-5 gap-4'>
            <div className='w-28 h-32 border-black border-2 rounded-md'>
              <img
                className='w-fit h-full rounded-md'
                src={require('../../assets/images/cloth.jpg')}
                alt=''
              />
            </div>
            <div className='flex flex-col justify-around w-80'>
              <div className='text-lg font-semibold'>브랜드</div>
              <div>상품이름뭐시기뭐시기싸고좋은티셔츠길어도안잘리고두번째줄로내려가요</div>
              <div className='flex gap-2'>
                <div className='flex'>
                  <button className='border-black border-2 rounded-md px-2 py-1'>배송정보</button>
                </div>
                <div className='flex'>
                  <button className='border-black border-2 rounded-md px-2 py-1'>문의하기</button>
                </div>
                <div className='flex'>
                  <button className='border-black border-2 rounded-md px-2 py-1'>결제취소</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default OrderList;
