import React from 'react';
import ItemImg from '../../assets/temp_item_detail2.jpg';

export default function ItemDetailImg() {
  return (
    <>
      <div>
        <img src={ItemImg} alt='제품 상세 설명 이미지' className='w-full' />
      </div>
    </>
  );
}
