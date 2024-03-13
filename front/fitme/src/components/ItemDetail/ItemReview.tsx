import React from 'react';

export default function ItemReview() {
  return (
    <div className='m-[3%]'>
      {/* 리뷰 전체 정보 */}
      <div className='flex justify-between'>
        <span className='text-sm font-semibold'>구매 후기</span>
        <div className='flex gap-2'>
          <span className='text-xs text-gray-700'>★ 4.0</span>
          <span className='text-xs text-gray-700'>전체 후기(13)</span>
        </div>
      </div>

      {/* 하이라이트 리뷰 한 개 */}
      <div className='flex flex-col my-1'>
        <div className='flex justify-between'>
          <span className='text-xs font-semibold'>Lv.3 사용자</span>
          <span className='text-xs'>구매 사이즈 : XL</span>
        </div>
        <span className='text-xs text-yellow-300'>★★★★★</span>
        <span className='text-xs'>
          리뷰 내용이 있는 칸이에요 입어보면 더 예쁜 바지입니다 다른 색도 주문했어요 얼른 도착했으면
          좋겠습니다 기다리는 중
        </span>
      </div>
    </div>
  );
}
