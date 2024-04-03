import React, { useEffect, useState } from 'react';
import Item from '../Common/Item';
import { useInView } from 'react-intersection-observer';
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
  likeCount: number;
  reviewRating: number;
  reviewCount: number;
};

type CategoryItemListProps = {
  selectedBrands: number[];
  selectedCategories: number[];
  selectedAges: number[];
  sortBy: string;
};

type ParamsType = {
  lastId?: number | null;
  brandIds?: string;
  categoryIds?: string;
  ageRanges?: string;
  sortBy?: string;
};

export default function CategoryItemList({
  selectedBrands,
  selectedCategories,
  selectedAges,
}: CategoryItemListProps) {
  const [list, setList] = useState<ItemType[]>([]);
  const [lastId, setLastId] = useState<number | null>(null);
  // 무한스크롤 구현
  const [ref, inView] = useInView();

  const infiniteScroll = () => {
    let params: ParamsType = {
      sortBy: 'latest',
      lastId: lastId,
    };

    if (selectedCategories.length > 0) {
      params = {
        ...params,
        categoryIds: selectedCategories.join(','),
      };
    }

    api
      .get(`/api/products`, {
        params: params,
      })
      .then(({ data }) => {
        if (data.length > 0) {
          setLastId(data[data.length - 1].id);
        }
        setList((prevList) => [...prevList, ...data]);
      });
  };

  useEffect(() => {
    if (inView) {
      // console.log(inView, '무한 스크롤 요청');

      infiniteScroll();
    }
  }, [inView]);

  return (
    <div>
      <div className='flex flex-wrap flex-row mx-[2%] place-content-start gap-y-3'>
        {list &&
          list.map((item, index) => (
            <Item
              key={index}
              id={item.id}
              name={item.name}
              price={item.price}
              mainImages={item.mainImages}
              brand={item.brand}
              likeCount={item.likeCount}
              reviewRating={item.reviewRating}
              reviewCount={item.reviewCount}
            />
          ))}
      </div>
      <div ref={ref} className='h-1' />
    </div>
  );
}
