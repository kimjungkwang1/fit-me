import React from 'react';
import Item from '../Common/Item';

export default function MainMyItems() {
  return (
    <>
      <p className='text-start mx-[3%] mt-2 mb-1'>사용자 맞춤 추천</p>
      <div className='overflow-x-scroll flex items-start mx-[3%] space-x-[1.4%]'>
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
      </div>
    </>
  );
}
