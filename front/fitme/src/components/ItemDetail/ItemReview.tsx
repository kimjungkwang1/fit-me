import React, { useEffect, useState } from 'react';
import { api } from '../../services/api';

type Review = {
  id: number; // 리뷰 아이디
  rating: number;
  content: string;
  imageUrl: string;
  memberNickname: string; // 작성자 닉네임
  createdAt: Date;
};

type ItemReviewProps = {
  id: number;
};

export default function ItemReview({ id }: ItemReviewProps) {
  const [reviews, setReviews] = useState<Review[]>([]);
  useEffect(() => {
    api.get(`/products/${id}/reviews`).then(({ data }) => {
      setReviews(data);
    });
  });

  return (
    <div className='m-[3%]'>
      {/* 리뷰 전체 정보 */}
      <div className='flex justify-between'>
        <span className='text-sm font-semibold'>구매 후기</span>
        <div className='flex gap-2'>
          <span className='text-xs text-gray-700'>★ 4.0</span>
          <span className='text-xs text-gray-700'>전체 후기({reviews.length})</span>
        </div>
      </div>

      {reviews.length !== 0 && (
        <div className='flex flex-col my-1'>
          <div className='flex justify-between'>
            <span className='text-xs font-semibold'>{reviews[0].memberNickname}</span>
            <span className='text-xs'>구매 사이즈 : XL</span>
          </div>
          <span className='text-xs text-yellow-300'>{'★'.repeat(reviews[0].rating)}</span>
          <span className='text-xs'>{reviews[0].content}</span>
        </div>
      )}
      {/* 하이라이트 리뷰 한 개 */}
    </div>
  );
}
