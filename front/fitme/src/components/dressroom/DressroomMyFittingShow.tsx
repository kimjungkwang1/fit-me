import React from 'react';
import DressroomMyfitting from './DressroomMyfitting';
import { FaXmark } from 'react-icons/fa6';
export default function DressroomMyfittingShow() {
  return (
    <>
      <div className='relative my-2'>
        <DressroomMyfitting></DressroomMyfitting>
        <button className='absolute top-4 right-[-4px] p-2 bg-red-500 text-white opacity-100 rounded-full w-8 h-8 z-10'>
          <FaXmark></FaXmark>
        </button>
      </div>
    </>
  );
}
