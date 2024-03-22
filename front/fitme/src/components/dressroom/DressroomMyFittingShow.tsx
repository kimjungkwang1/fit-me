import React from 'react';
import DressroomMyfitting from './DressroomMyfitting';
export default function DressroomMyfittingShow() {
  return (
    <>
      <div className='relative my-2'>
        <DressroomMyfitting></DressroomMyfitting>
        <button className='absolute top-4 right-[-10px] p-2 bg-red-500 text-white opacity-100 rounded-full w-10 h-10 z-10'>
          X
        </button>
      </div>
    </>
  );
}
