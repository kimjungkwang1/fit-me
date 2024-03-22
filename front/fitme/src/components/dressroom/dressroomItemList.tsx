import React, { useState } from 'react';

import { Button } from 'flowbite-react';

type selectedButtonType = '전체' | '상의' | '하의';
export default function DressroomItemLst() {
  const [selected, setSelected] = useState<selectedButtonType>('전체');
  const select = (s: selectedButtonType) => {
    setSelected(s);
  };
  return (
    <>
      <div className='flex justify-center gap-4 mb-2'>
        <Button color={selected === '전체' ? 'dark' : 'gary'} pill onClick={() => select('전체')}>
          전체
        </Button>
        <Button color={selected === '상의' ? 'dark' : 'gary'} pill onClick={() => select('상의')}>
          상의
        </Button>
        <Button color={selected === '하의' ? 'dark' : 'gary'} pill onClick={() => select('하의')}>
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
