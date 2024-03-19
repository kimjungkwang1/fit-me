import React from 'react';
import Item from '../Common/Item';

export default function RecommendedItems() {
  return (
    <div className='m-[3%]'>
      <span className='text-sm font-semibold'>함께 보면 좋은 상품</span>
      <div className='flex flex-row justify-between mt-1'>
        {/* <Item />
        <Item />
        <Item /> */}
      </div>
    </div>
  );
}
