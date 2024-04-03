import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { api } from '../../services/api';

type ItemType = {
  id: number;
  name: string;
  url: string;
  brandName: string;
};

export default function BoughtItem({ id, name, url, brandName }: ItemType) {
  const navigate = useNavigate();

  return (
    <>
      <div className='w-full h-full flex flex-col justify-between'>
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
            <p className='text-xs truncate'>{name}</p>
            <div className='flex justify-between'></div>
          </div>
        </Link>
        <button
          className='w-full rounded-md mt-2 bg-bluegray text-white py-1'
          onClick={() => navigate('/review', { state: { id, name, url, brandName } })}
        >
          리뷰등록
        </button>
      </div>
    </>
  );
}
