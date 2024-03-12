import * as React from 'react';

export default function DressroomNowFitting() {
  return (
    <>
      <div className='flex flex-col m-4 p-2 border border-blue-500 rounded-lg text-sm'>
        <p>피팅중</p>
        <p>상의</p>
        <div className='w-[45%] pt-[60%] mx-auto bg-red-500'></div>
        <p>하의</p>
        <div className='w-[45%] pt-[60%] mx-auto bg-blue-500'></div>
      </div>
    </>
  );
}
