import React from 'react';
import { IoIosArrowUp } from 'react-icons/io';

export default function TopButton() {
  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  return (
    <>
      <div
        onClick={scrollToTop}
        className='w-10 h-10 fixed bottom-[65px] right-[8px] rounded-full bg-white justify-items-center border border-solid border-darkgray px-1 grid grid-rows-2'
      >
        <IoIosArrowUp className='row-span-1 text-2xl text-darkgray' />
        <span className='row-span-1 text-center text-xs font-bold text-darkgray'>TOP</span>
      </div>
    </>
  );
}
