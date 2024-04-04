import React from 'react';

type OptionType = {
  index: number;
  color: string;
  size: string;
  quantity: number;
  updateOption: (idx: number, update: number) => void;
  deleteOption: (idx: number) => void;
  price: number;
};

export default function ItemOptionSelected({
  index,
  color,
  size,
  quantity,
  updateOption,
  deleteOption,
  price,
}: OptionType) {
  return (
    <>
      <div className='bg-gray-100 mt-2 rounded-lg px-3 py-2 text-sm w-full'>
        <div className='flex justify-between'>
          <span>
            {/* 수량, 사이즈 */}
            {color} / {size}
          </span>
          {/* 목록에서 삭제 버튼 */}
          <span onClick={() => deleteOption(index)}>x</span>
        </div>
        <div className='flex justify-between'>
          <span className='flex flex-row text-lg font-semibold'>
            {/* '- 버튼' : 1에서 더 줄어들지 않게 처리함 */}
            {quantity === 1 ? (
              <span className='text-gray-400'>-</span>
            ) : (
              <span onClick={() => updateOption(index, -1)}>-</span>
            )}
            {/* '+ 버튼' : 100에서 더 늘어나지 않게 처리함 */}
            <div className='w-10 flex justify-center'>{quantity}</div>
            {quantity === 100 ? (
              <span className='text-gray-400'>+</span>
            ) : (
              <span onClick={() => updateOption(index, +1)}>+</span>
            )}
          </span>
          {/* 가격 */}
          <span className='text-lg font-semibold'>{(quantity * price).toLocaleString()}원</span>
        </div>
      </div>
    </>
  );
}
