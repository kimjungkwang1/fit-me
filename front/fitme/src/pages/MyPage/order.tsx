import React, { useEffect } from 'react';
import OrderList from '../../components/MyPage/orderList';
import Rating from '../../components/MyPage/rating';

const Order = () => {
  return (
    <>
      <OrderList />
      <Rating />
      <div className='flex flex-col gap-3 items-center justify-center'>
        <div className='mt-5 text-2xl'>리뷰를 남겨주세요.</div>
        <textarea
          placeholder='10자 이상 입력해주세요.'
          className='resize-none w-4/5 h-32 bg-gray-300 rounded-sm p-3 my-10'
        ></textarea>
        <button className='w-4/5 h-10 bg-white rounded-lg flex justify-center items-center border-2 border-gray-400 hover:shadow-lg hover-shadow-darkgray focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none'>
          <svg
            xmlns='http://www.w3.org/2000/svg'
            fill='none'
            viewBox='0 0 24 24'
            strokeWidth={1.5}
            stroke='currentColor'
            className='w-6 h-6'
          >
            <path
              strokeLinecap='round'
              strokeLinejoin='round'
              d='m2.25 15.75 5.159-5.159a2.25 2.25 0 0 1 3.182 0l5.159 5.159m-1.5-1.5 1.409-1.409a2.25 2.25 0 0 1 3.182 0l2.909 2.909m-18 3.75h16.5a1.5 1.5 0 0 0 1.5-1.5V6a1.5 1.5 0 0 0-1.5-1.5H3.75A1.5 1.5 0 0 0 2.25 6v12a1.5 1.5 0 0 0 1.5 1.5Zm10.5-11.25h.008v.008h-.008V8.25Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z'
            />
          </svg>
          사진 첨부
        </button>
        <button
          data-twe-ripple-init
          data-twe-ripple-color='black'
          className='w-4/5 h-10 bg-bluegray rounded-lg text-white hover:shadow-lg focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none'
        >
          리뷰 등록
        </button>
      </div>
    </>
  );
};

export default Order;
