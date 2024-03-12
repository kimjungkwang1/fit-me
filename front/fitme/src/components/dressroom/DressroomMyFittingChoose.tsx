import React from 'react';
import DressroomMyfitting from './DressroomMyfitting';
import { FaCheck } from 'react-icons/fa';

export default function DressroomMyFittingChoose() {
  return (
    <div>
      <div className='relative my-2'>
        <DressroomMyfitting></DressroomMyfitting>
        <FaCheck className='absolute top-4 right-[-10px] p-2 bg-green-500 text-white opacity-100 rounded-full w-10 h-10'>
          X
        </FaCheck>
      </div>
    </div>
  );
}
