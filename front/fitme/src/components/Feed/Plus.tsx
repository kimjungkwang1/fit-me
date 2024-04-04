import React from 'react';
import { CiCirclePlus } from 'react-icons/ci';

export default function Plus() {
  return (
    <>
      <div className='m-2 my-10'>
        <p>내 피드</p>
        <div className='w-full h-[10vw] border flex justify-center items-center mt-2'>
          <CiCirclePlus size={30}></CiCirclePlus>
        </div>
      </div>
    </>
  );
}
