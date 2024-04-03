import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { api } from '../../services/api';

type ItemType = {
  memberId: number;
  id: number;
  name: string;
  url: string;
  brandName: string;
};

type Review = {
  memberId: number;
  id: number; // 리뷰 아이디
  rating: number;
  content: string;
  imageUrl: string;
  memberNickname: string; // 작성자 닉네임
  createdAt: Date;
};

export default function BoughtItem({ memberId, id, name, url, brandName }: ItemType) {
  const navigate = useNavigate();
  const [reviewCheck, setReviewCheck] = useState<boolean>(false);

  useEffect(() => {
    api.get(`/api/products/${id}/reviews`).then(({ data }) => {
      const foundReview = data.find((review: Review) => review.memberId === memberId);

      if (foundReview) {
        setReviewCheck(true);
      } else {
        setReviewCheck(false);
      }
    });
  }, [id, memberId]);

  return (
    <>
      <div className='w-full h-full flex flex-col justify-between'>
        <Link to={`/detail/${id}`}>
          <div>
            {url ? (
              <img
                src={url}
                alt='main_image'
                className='aspect-[3/4] object-cover rounded-lg border-2 border-beige'
              />
            ) : (
              <div className='bg-gray-400 aspect-[3/4] object-cover rounded-lg' />
            )}
          </div>
          <div className='mt-1 justify-start text-start'>
            <div className='text-xs'>{brandName}</div>
            <p className='text-xs truncate'>{name}</p>
            <div className='flex justify-between'></div>
          </div>
        </Link>
        {reviewCheck ? (
          <div className='flex justify-center w-full rounded-md mt-2 bg-white text-bluegray border-2 border-bluegray py-1'>
            리뷰 작성 완료
          </div>
        ) : (
          <button
            className='w-full rounded-md mt-2 bg-bluegray text-white border-2 border-bluegray py-1'
            onClick={() => navigate('/review', { state: { id, name, url, brandName } })}
          >
            리뷰등록
          </button>
        )}
      </div>
    </>
  );
}
