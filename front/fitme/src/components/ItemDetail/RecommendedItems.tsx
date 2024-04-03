import React, { useEffect, useState } from 'react';
import Item from '../Common/Item';
import { api } from '../../services/api';

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

  return (
    <div className='m-[3%]'>
      {recomList.length !== 0 && <span className='text-sm font-semibold'>함께 보면 좋은 상품</span>}
      <div className='flex mx-[2%] place-content-start gap-y-3'>
        {recomList &&
          recomList
            .slice(0, 3)
            .map((recom, index) => (
              <Item
                key={index}
                id={recom.id}
                name={recom.name}
                price={recom.price}
                mainImages={recom.mainImages}
                brand={recom.brand}
                likeCount={recom.likeCount}
                reviewRating={recom.reviewRating}
                reviewCount={recom.reviewCount}
              />
            ))}
      </div>
    </div>
  );
}
