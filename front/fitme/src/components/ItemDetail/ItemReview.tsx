import { useEffect, useState } from 'react';
import { api } from '../../services/api';
import ReviewOne from './ReviewOne';
import { useNavigate } from 'react-router-dom';

type ReviewProp = {
  id: number; // 리뷰 아이디
  rating: number;
  content: string;
  imageUrl: string;
  memberNickname: string; // 작성자 닉네임
  createdAt: Date;
};

type ItemReviewProps = {
  name: string;
  id: number;
  reviewCount: number;
  reviewRating: number;
};

export default function ItemReview({ name, id, reviewCount, reviewRating }: ItemReviewProps) {
  const navigate = useNavigate();
  const [reviews, setReviews] = useState<ReviewProp[]>([]);
  useEffect(() => {
    api.get(`/api/products/${id}/reviews`).then(({ data }) => {
      setReviews(data);
    });
  }, []);

  return (
    <div className='m-[3%]'>
      {/* 리뷰 전체 정보 */}
      <div className='flex justify-between'>
        <span className='text-sm font-semibold'>구매 후기</span>
        <div className='flex gap-2'>
          <span className='text-xs text-gray-700'>★{reviewRating.toFixed(1)}</span>
          <span className='text-xs text-gray-700'></span>
          <button
            className='text-xs text-gray-700'
            onClick={() => navigate(`/review/${id}`, { state: { reviews: reviews, name: name } })}
          >
            전체 후기({reviewCount})
          </button>
        </div>
      </div>

      {reviews.length !== 0 ? (
        <ReviewOne review={reviews[reviews.length - 1]} />
      ) : (
        <div className='h-24 p-8 text-center'>등록된 리뷰가 없습니다.</div>
      )}
      {/* 하이라이트 리뷰 한 개 */}
    </div>
  );
}
