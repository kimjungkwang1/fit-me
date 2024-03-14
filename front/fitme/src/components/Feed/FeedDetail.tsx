import React from 'react';
import User from './User';
import { AiOutlineLike } from 'react-icons/ai';
export default function FeedDetail() {
  return (
    <>
      <div>
        <div className='w-[100vw] h-[120vw] bg-beige'></div>
        <div className='flex justify-between'>
          <User></User>
          <AiOutlineLike size={30} className='m-2'></AiOutlineLike>
        </div>
        <div className='m-2 font-bold'>좋아요 1개 댓글 1개</div>
        <div className='m-2'>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Expedita rem voluptatibus, itaque
          dolores, qui aliquam repudiandae adipisci atque eveniet ad similique. Suscipit a possimus
          libero sed. Aspernatur, sequi odio? Possimus!
        </div>
      </div>
    </>
  );
}
