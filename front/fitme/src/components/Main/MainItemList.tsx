import React from 'react';
import Item from '../Common/Item';

export default function MainItemList() {
  return (
    <div>
      <p className='text-start mx-[3%] mt-2 mb-1'>실시간 랭킹</p>
      <div className='flex flex-wrap flex-row mx-[2%] place-content-around gap-y-3'>
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
      </div>
    </div>
  );
}
