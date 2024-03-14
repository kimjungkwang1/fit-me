import React from 'react';
import Item from '../Common/Item';

export default function CategoryItemList() {
  return (
    <div>
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
