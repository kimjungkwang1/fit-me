import { Dropdown } from 'flowbite-react';
import React from 'react';

export default function SortBar() {
  const sortBy = {
    gender: '여자',
    age: '20대',
    order: '인기순',
  };

  return (
    <div className='flex flex-row mx-[3%] mt-2 mb-2 justify-between'>
      <div className='flex gap-3'>
        <Dropdown
          label=''
          placement='top'
          dismissOnClick={false}
          renderTrigger={() => <span className='text-sm'>{sortBy.gender}</span>}
        >
          <Dropdown.Item>여성</Dropdown.Item>
          <Dropdown.Item>남성</Dropdown.Item>
        </Dropdown>
        <Dropdown
          label=''
          placement='top'
          dismissOnClick={false}
          renderTrigger={() => <span className='text-sm'>{sortBy.age}</span>}
        >
          <Dropdown.Item>10대</Dropdown.Item>
          <Dropdown.Item>20대</Dropdown.Item>
          <Dropdown.Item>30대</Dropdown.Item>
        </Dropdown>
      </div>
      <div className='flex'>
        <Dropdown
          label=''
          placement='top'
          dismissOnClick={false}
          renderTrigger={() => <span className='text-sm'>{sortBy.order}</span>}
        >
          <Dropdown.Item>인기순</Dropdown.Item>
          <Dropdown.Item>최신순</Dropdown.Item>
        </Dropdown>
      </div>
    </div>
  );
}
