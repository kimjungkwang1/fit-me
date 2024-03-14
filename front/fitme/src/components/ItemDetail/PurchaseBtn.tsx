import React, { useState } from 'react';

export default function PurchaseBtn() {
  const [openModal, setOpenModal] = useState(false);
  const modalHandler = () => {
    setOpenModal(!openModal);
  };

  return (
    <>
      {openModal && (
        <div>
          <div className='bg-bluegray opacity-70 w-full h-full fixed top-0 bottom-[53.6px] left-0 right-0' />
          <div className='bg-white p-4 flex bottom-[53.6px] left-0 right-0 h-[50vh] max-h-80 w-full fixed rounded-t-2xl'>
            <div>
              <span className='text-sm font-semibold'>옵션 선택</span>
            </div>
          </div>
        </div>
      )}
      <div className='bg-white p-2 flex flex-row fixed bottom-0 left-0 right-0'>
        {/* 입어보기 버튼 */}
        <div className='w-full mr-2 bg-white border border-solid border-black rounded-lg px-2 py-2 text-center text-sm font-semibold'>
          <span>입어보기(장바구니)</span>
        </div>

        {/* 구매하기 버튼 */}
        <div
          onClick={modalHandler}
          className='w-full bg-bluegray border border-solid border-black rounded-lg px-2 py-2 text-center text-white text-sm font-semibold'
        >
          <span>구매하기</span>
        </div>
      </div>
    </>
  );
}
