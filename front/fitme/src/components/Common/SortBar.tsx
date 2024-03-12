import { Dropdown } from 'flowbite-react';
import React, { useState } from 'react';

export default function SortBar() {
  const [sortBy, setSortBy] = useState({
    gender: '성별',
    age: '연령',
    order: '인기순',
  });

  const handleGender = (newGender: string) => {
    setSortBy({
      ...sortBy,
      gender: newGender,
    });
  };

  const handleAge = (newAge: string) => {
    setSortBy({
      ...sortBy,
      age: newAge,
    });
  };

  const handleOrder = (newOrder: string) => {
    setSortBy({
      ...sortBy,
      order: newOrder,
    });
  };

  return (
    <div className='flex flex-row mx-[3%] mt-2 mb-2 justify-between'>
      <div className='flex'>
        <Dropdown
          label=''
          placement='top'
          renderTrigger={() => <span className='text-sm w-12'>{sortBy.gender}</span>}
        >
          <Dropdown.Item onClick={() => handleGender('성별')}>전체</Dropdown.Item>
          <Dropdown.Item onClick={() => handleGender('여성')}>여성</Dropdown.Item>
          <Dropdown.Item onClick={() => handleGender('남성')}>남성</Dropdown.Item>
        </Dropdown>
        <Dropdown
          label=''
          placement='top'
          renderTrigger={() => <span className='text-sm w-20'>{sortBy.age}</span>}
        >
          <Dropdown.Item onClick={() => handleAge('연령대')}>전체</Dropdown.Item>
          <Dropdown.Item onClick={() => handleAge('19~23세')}>19~23세</Dropdown.Item>
          <Dropdown.Item onClick={() => handleAge('24~28세')}>24~28세</Dropdown.Item>
          <Dropdown.Item onClick={() => handleAge('29~33세')}>29~33세</Dropdown.Item>
          <Dropdown.Item onClick={() => handleAge('34~39세')}>34~39세</Dropdown.Item>
          <Dropdown.Item onClick={() => handleAge('40세 이상')}>40세 이상</Dropdown.Item>
        </Dropdown>
      </div>
      <div className='flex'>
        <Dropdown
          label=''
          placement='top'
          renderTrigger={() => <span className='text-sm'>{sortBy.order}</span>}
        >
          <Dropdown.Item onClick={() => handleOrder('인기순')}>인기순</Dropdown.Item>
          <Dropdown.Item onClick={() => handleOrder('최신순')}>최신순</Dropdown.Item>
        </Dropdown>
      </div>
    </div>
  );
}
