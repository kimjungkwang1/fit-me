import React from 'react';

type OptionType = {
  idColor: number;
  color: string;
  idSize: number;
  size: string;
  quantity: number;
};

export default function ItemOptionSelected({ idColor, color, idSize, size, quantity }: OptionType) {
  return (
    <>
      <div className='bg-gray-100 mt-2 rounded-lg px-3 py-2 text-sm w-full'>
        <div className='flex justify-between'>
          <span>
            {color} / {size}
          </span>
          <span>x</span>
        </div>
        <div className='flex justify-between'>
          <span className='text-lg font-semibold'>- {quantity} +</span>
          <span className='text-lg font-semibold'>10,100원</span>
        </div>
      </div>
    </>
  );
}
