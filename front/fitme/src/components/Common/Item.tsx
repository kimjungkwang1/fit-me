import React from 'react';
import { Link } from 'react-router-dom';

type ImageType = {
  id: number;
  url: string;
};

type BrandType = {
  id: number;
  name: string;
};

type ItemType = {
  id: number;
  name: string;
  price: number;
  mainImages: ImageType[];
  brand: BrandType;
  likeCount: number;
  reviewRating: number;
  reviewCount: number;
};

export default function Item({
  id,
  name,
  price,
  mainImages,
  brand,
  likeCount,
  reviewRating,
  reviewCount,
}: ItemType) {
  return (
    <>
      <div className='flex-col w-[32%]'>
        <Link to={`/detail/${id}`}>
          <div>
            <img
              src={mainImages[0].url}
              alt='main_image'
              className='aspect-[3/4] object-cover rounded-lg'
            />
          </div>
          <div className='justify-start text-start'>
            <p className='text-xs'>{brand.name}</p>
            <p className='text-xs truncate'>{name}</p>
            <p className='text-sm font-semibold'>{price.toLocaleString()}원</p>
            <div className='flex justify-between'>
              <p className='text-xs'>{likeCount}</p>
              <p className='text-xs'>
                <span className='text-yellow-300'>★</span> {reviewRating.toFixed(1)} (
                {reviewCount.toLocaleString()}개)
              </p>
            </div>
          </div>
        </Link>
      </div>
    </>
  );
}
