import React from 'react';

export default function PurchaseBtn() {
  return (
    <>
      <div className='bg-white p-2 flex flex-row fixed bottom-0 left-0 right-0'>
        <div className='w-full mr-2 bg-gray-200 border border-solid border-black rounded-lg px-2 py-2 text-center text-sm font-semibold'>
          <span>입어보기(장바구니)</span>
        </div>
        <div className='w-full bg-gray-500 border border-solid border-black rounded-lg px-2 py-2 text-center text-white text-sm font-semibold'>
          <span>구매하기</span>
        </div>
      </div>
    </>
  );
}
