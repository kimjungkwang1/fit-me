import { Button } from 'flowbite-react';
import React from 'react';

export default function FilterBar() {
  return (
    <div className='flex flex-row gap-1 mx-[3%] mt-2 mb-1'>
      <Button color='light' size='xs'>
        브랜드
      </Button>
      <Button color='light' size='xs'>
        색상
      </Button>
      <Button color='light' size='xs'>
        카테고리
      </Button>
      <Button color='light' size='xs'>
        가격
      </Button>
    </div>
  );
}
