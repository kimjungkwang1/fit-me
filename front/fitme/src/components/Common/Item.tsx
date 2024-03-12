import React from 'react';

export default function Item() {
  return (
    <>
      <div className='flex-col w-[32%]'>
        <div className='bg-gray-500 pb-[130%] rounded-lg' />
        <div className='justify-start text-start'>
          <p className='text-xs'>중광이네</p>
          <p className='text-xs truncate'>
            위아래로쭉쭉늘어나고신축성이좋은티셔츠입니다꼭사야할걸요
          </p>
          <p className='text-sm font-semibold'>20,000원</p>
          <div className='flex justify-between'>
            <p className='text-xs'>35</p>
            <p className='text-xs'>
              <span className='text-yellow-300'>★</span> 4.0 (13개)
            </p>
          </div>
        </div>
      </div>
    </>
  );
}
