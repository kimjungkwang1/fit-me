import { useEffect, useState } from 'react';
import { api } from '../../services/api';
import Noimage from '../../assets/images/noimage.png';

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
  reviewCount: number;
  reviewRating: number;
};

export default function ItemReview({ id, reviewCount, reviewRating }: ItemReviewProps) {
  const [reviews, setReviews] = useState<Review[]>([]);
  useEffect(() => {
    api.get(`/api/products/${id}/reviews`).then(({ data }) => {
      setReviews(data);
      console.log(data);
    });
  }, []);

  return (
    <div className='m-[3%]'>
      {/* 리뷰 전체 정보 */}
      <div className='flex justify-between'>
        <span className='text-sm font-semibold'>구매 후기</span>
        <div className='flex gap-2'>
          <span className='text-xs text-gray-700'>★{reviewRating.toFixed(1)}</span>
          <span className='text-xs text-gray-700'>전체 후기({reviewCount})</span>
        </div>
      </div>

      {reviews.length !== 0 ? (
        <div className='flex flex-col my-1'>
          <div className='flex justify-between'>
            <span className='text-xs'>
              작성자 :{' '}
              <span className='font-semibold'>{reviews[reviews.length - 1].memberNickname}</span>
            </span>
            <span className='text-xs'>
              평점 :{' '}
              <span className='text-yellow-300'>
                {'★'.repeat(reviews[reviews.length - 1].rating)}
              </span>
            </span>
          </div>
          <div className='flex mt-2'>
            <img
              className='aspect-[3/4] w-20 border-beige border-2'
              src={
                reviews[reviews.length - 1].imageUrl
                  ? reviews[reviews.length - 1].imageUrl.replace('./', 'https://fit-me.site/')
                  : Noimage
              }
              alt=''
            />
            <div className='ml-4'>
              <div className='text-xs mb-2'>
                {reviews[reviews.length - 1].createdAt.toString().replace('T', ' ')}
              </div>
              <div className='text-sm'>{reviews[reviews.length - 1].content}</div>
            </div>
          </div>
        </div>
      ) : (
        <div className='h-24 p-8 text-center'>등록된 리뷰가 없습니다.</div>
      )}
      {/* 하이라이트 리뷰 한 개 */}
    </div>
  );
}
