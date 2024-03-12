import React from 'react';

import { Button } from 'flowbite-react';

export default function DressroomItemLst() {
  return (
    <>
      <div className='flex justify-center gap-4'>
        <Button color='gray' pill>
          전체
        </Button>
        <Button color='gray' pill>
          상의
        </Button>
        <Button color='gray' pill>
          하의
        </Button>
      </div>
      <div className='flex overflow-y-auto h-[30vh] flex-wrap'>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
        <div className='w-[20%] pt-[26.66%] bg-green-500 m-2 flex-shrink-0'></div>
      </div>
    </>
  );
}
