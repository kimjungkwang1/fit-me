import React, { useEffect, useState } from 'react';
import Item from '../Common/Item';
import { api } from '../../services/api';
import { Link, useLocation } from 'react-router-dom';
import { TbThumbUp } from 'react-icons/tb';

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
  popularityScore: number;
  likeCount: number;
  reviewRating: number;
  reviewCount: number;
};

type RecommendedItemsProps = {
  id: number;
};

export default function RecommendedItems({ id }: RecommendedItemsProps) {
  const [recomList, setRecomList] = useState<ItemType[]>([]);
  useEffect(() => {
    api.get(`/api/products/${id}/recommendations`).then(({ data }) => {
      setRecomList(data);
    });
  }, []);

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const tab = queryParams.get('tab');
  const isFavTab = location.pathname === '/mypage' && tab === 'fav';
  return (
    <div className='m-[3%]'>
      {recomList.length !== 0 && <span className='text-sm font-semibold'>함께 보면 좋은 상품</span>}
      <div className='flex overflow-auto hide-scrollbar mx-[2%] place-content-start gap-y-3'>
        {recomList &&
          recomList.map((recom, index) => (
            <div className={`flex-col mb-2 ${isFavTab ? 'w-full' : 'w-[31.5%] mx-[0.916%]'}`}>
              <Link to={`/detail/${recom.id}`}>
                <div>
                  {recom.mainImages[0] ? (
                    <img
                      src={recom.mainImages[0].url}
                      alt='main_image'
                      className='aspect-[3/4] object-cover rounded-lg border-beige border-2'
                    />
                  ) : (
                    <div className='bg-gray-400 aspect-[3/4] object-cover rounded-lg' />
                  )}
                </div>
                <div className='mt-1 justify-start text-start'>
                  <p className='text-xs truncate'>{recom.brand.name}</p>
                  <p className='text-xs truncate'>{recom.name}</p>
                  <p className='font-bold'>{recom.price.toLocaleString()}원</p>
                  <div className='flex justify-between'>
                    <p className='text-xs flex flex-row items-baseline'>
                      <TbThumbUp className='font-light mr-[2px]' />
                      {recom.likeCount}
                    </p>
                    <p className='text-xs'>
                      <span className='text-yellow-300'>★</span> {recom.reviewRating.toFixed(1)} (
                      {recom.reviewCount.toLocaleString()}개)
                    </p>
                  </div>
                </div>
              </Link>
            </div>
            // <Item
            //   key={index}
            //   id={recom.id}
            //   name={recom.name}
            //   price={recom.price}
            //   mainImages={recom.mainImages}
            //   brand={recom.brand}
            //   likeCount={recom.likeCount}
            //   reviewRating={recom.reviewRating}
            //   reviewCount={recom.reviewCount}
            // />
          ))}
      </div>
    </div>
  );
}
