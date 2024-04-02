import React from 'react';
import { Link } from 'react-router-dom';

type ItemType = {
  id: number;
  name: string;
  url: string;
  brandName: string;
};

export default function BoughtItem({ id, name, url, brandName }: ItemType) {
  return (
    <>
      <div className='w-full h-full'>
        <Link to={`/detail/${id}`}>
          <div>
            {url ? (
              <img src={url} alt='main_image' className='aspect-[3/4] object-cover rounded-lg' />
            ) : (
              <div className='bg-gray-400 aspect-[3/4] object-cover rounded-lg' />
            )}
          </div>
          <div className='mt-1 justify-start text-start'>
            <div className='text-xs'>{brandName}</div>
            <div className='text-xs truncate'>{name}</div>
            <div className='flex justify-between'></div>
          </div>
        </Link>
        <button className='w-full rounded-md mt-2 bg-gray-200 py-1'>리뷰등록</button>
      </div>
    </>
  );
}
