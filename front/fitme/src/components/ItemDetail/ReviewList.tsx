import React, { useEffect, useState } from 'react';
import ReviewOne from './ReviewOne'; // ReviewOne 컴포넌트 임포트
import { useLocation } from 'react-router-dom';

type ReviewProps = {
  id: number;
  rating: number;
  content: string;
  imageUrl: string;
  memberNickname: string;
  createdAt: Date;
};

const ReviewList = () => {
  const location = useLocation();
  const { reviews, name } = location.state;
  const [productName, setProductName] = useState(name);

  useEffect(() => {
    if (name.length > 15) {
      setProductName(name.slice(0, 15) + '...');
    }
  }, [name]);

  return (
    <div>
      <div className='flex justify-center w-full my-5 text-lg'>
        <div className='mr-3 font-bold'>{productName}</div>
        <div>리뷰</div>
      </div>
      {reviews &&
        reviews.map((review: ReviewProps) => (
          <div className='border-t-2 border-gray-300' key={review.id}>
            <ReviewOne key={review.id} review={review} />
          </div>
        ))}
    </div>
  );
};

export default ReviewList;
