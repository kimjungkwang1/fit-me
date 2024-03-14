import React, { useState } from 'react';
import ItemImg from '../../assets/temp_item_detail.png';
import { TbThumbUpFilled } from 'react-icons/tb';
import Tags from '../Common/Tags';

export default function ItemInfo() {
  const [like, setLike] = useState(false);
  const likeHandler = () => {
    setLike(!like);
  };

  return (
    <div>
      {/* 상품 이미지 */}
      <div className='flex w-full aspect-square bg-orange-100 justify-center relative'>
        <img src={ItemImg} alt='item_detail' className='h-full' />
        {like ? (
          <TbThumbUpFilled
            onClick={likeHandler}
            className='absolute bottom-[5px] right-[5px] w-10 h-10 text-brown bg-lightbrown rounded-full p-1'
          />
        ) : (
          <TbThumbUpFilled
            onClick={likeHandler}
            className='absolute bottom-[5px] right-[5px] w-10 h-10 text-white bg-lightbrown rounded-full p-1'
          />
        )}
      </div>

      {/* 상품 정보 */}
      <div className='m-[3%]'>
        <div>
          <span className='text-sm font-semibold'>쇼핑몰 이름</span>
        </div>
        <div>
          <span className='text-lg font-semibold'>
            상품이름이아주길고길고긴데정말길어도잘리지않고아랫줄로내려가요
          </span>
        </div>
        <div>
          <span className='text-2xl font-bold'>23,900원</span>
        </div>
        <div>
          <Tags tag='하의' />
          <Tags tag='슬랙스' />
          <Tags tag='중광이집' />
        </div>
      </div>
    </div>
  );
}
