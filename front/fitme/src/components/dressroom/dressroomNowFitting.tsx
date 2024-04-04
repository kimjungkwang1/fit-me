import * as React from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/store';

export default function DressroomNowFitting() {
  const top = useSelector((state: RootState) => state.dressroom.nowTop);
  const bottom = useSelector((state: RootState) => state.dressroom.nowBottom);
  return (
    <>
      <div className='flex flex-col m-2 ml-4 p-4 rounded-lg text-sm text-center max-w-[160px]'>
        <p>상의</p>
        {top.id === 0 ? (
          <div className='min-w-[60px] min-h-[80px] w-[60%] pt-[60%] object-contain mx-auto bg-gray-300 rounded-md mb-2'></div>
        ) : (
          <img
            className='min-w-[60px] min-h-[80px] w-[60%] h-auto object-cover aspect-square mx-auto rounded-md mb-2'
            src={top.url}
            alt='상의'
          ></img>
        )}
        <p>하의</p>
        {bottom.id === 0 ? (
          <div className='min-w-[60px] w-[60%] min-h-[80px] pt-[60%] object-contain mx-auto bg-gray-300 rounded-md'></div>
        ) : (
          <img
            className='min-w-[60px] w-[60%] min-h-[80px] h-auto object-cover aspect-square mx-auto rounded-md'
            src={bottom.url}
            alt='하의'
          ></img>
        )}
      </div>
    </>
  );
}
