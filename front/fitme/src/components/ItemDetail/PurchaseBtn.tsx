import React, { useState } from 'react';

export default function PurchaseBtn() {
  const [openModal, setOpenModal] = useState(false);
  const modalHandler = () => {
    setOpenModal(!openModal);
  };

  return (
    <>
      <div className='flex justify-center'>
        {/* modal */}
        {openModal && (
          <div className='flex-grow'>
            <div className='bg-black opacity-30 fixed top-0 bottom-0 w-[100vw] max-w-[600px]' />
            <div className='bg-white p-4 flex bottom-[53.6px] h-[50vh] max-h-80 w-full max-w-[600px] fixed rounded-t-2xl'>
              <div>
                <span className='text-sm font-semibold'>옵션 선택</span>
              </div>
            </div>
          </div>
        )}

        {/* 하단 버튼 */}
        <div className='bg-white p-2 flex flex-row fixed bottom-0 w-full max-w-[600px]'>
          {/* 입어보기 버튼 */}
          <div className='w-full mr-2 bg-white border border-solid border-black rounded-lg px-2 py-3 text-center text-sm font-semibold'>
            <span>입어보기(장바구니)</span>
          </div>

          {/* 구매하기 버튼 */}
          <div
            onClick={modalHandler}
            className='w-full bg-bluegray border border-solid border-black rounded-lg px-2 py-3 text-center text-white text-sm font-semibold'
          >
            <span>구매하기</span>
          </div>
        </div>
      </div>
    </>
  );
}
