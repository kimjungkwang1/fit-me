import * as React from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/store';

export default function DressroomNowFitting() {
  const top = useSelector((state: RootState) => state.dressroom.nowTop);
  const bottom = useSelector((state: RootState) => state.dressroom.nowBottom);
  return (
    <>
      <div className='flex flex-col m-4 p-2 border border-blue-500 rounded-lg text-sm'>
        <p>상의</p>
        {top.id === 0 ? (
          <div className='w-[60%] pt-[60%] object-contain mx-auto bg-gray-300'></div>
        ) : (
          <img className='w-[60%] h-auto object-contain mx-auto ' src={top.url} alt='상의'></img>
        )}
        <p>하의</p>
        {bottom.id === 0 ? (
          <div className='w-[60%] pt-[60%] object-contain mx-auto bg-gray-300'></div>
        ) : (
          <img className='w-[60%] h-auto object-contain mx-auto ' src={bottom.url} alt='하의'></img>
        )}
      </div>
    </>
  );
}
