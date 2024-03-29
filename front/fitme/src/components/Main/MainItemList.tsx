import React, { useEffect, useState } from 'react';
import Item from '../Common/Item';
import axios from 'axios';
import { useInView } from 'react-intersection-observer';

type ImageType = {
  id: number;
  url: string;
};

type BrandType = {
  id: number;
  name: string;
};

type ItemType = {
  rank: number;
  id: number;
  name: string;
  price: number;
  mainImages: ImageType[];
  brand: BrandType;
  likeCount: number;
  reviewRating: number;
  reviewCount: number;
};

export default function MainItemList() {
  // 무한스크롤 구현
  const [ref, inView] = useInView();
  const [lastRank, setLastRank] = useState<number>(0);

  // 리스트 불러오기
  const [list, setList] = useState<ItemType[]>([]);
  const [updatedTime, setUpdatedTime] = useState<string>('');

  let params = {};

  const infiniteScroll = () => {
    if (lastRank !== 0) {
      params = {
        lastRank: lastRank,
      };
    }

    axios
      .get(`https://fit-me.site/api/products/rankings`, {
        params: params,
      })
      .then(({ data }) => {
        if (data.rankings.length === 0) {
          return;
        }
        setList([...list, ...data.rankings]);
        setUpdatedTime(data.updatedTime);
        setLastRank(data.rankings[data.rankings.length - 1].rank);
      });
  };

  useEffect(() => {
    if (inView) {
      console.log(inView, '무한 스크롤 요청');

      infiniteScroll();
    }
  }, [inView]);

  return (
    <div>
      <div className='flex flex-row justify-between'>
        <p className='text-sm font-semibold text-start mx-[3%] mt-2 mb-1'>실시간 랭킹</p>
        <p className='text-sm text-gray-500 text-start mx-[3%] mt-2 mb-1'>{updatedTime} 업데이트</p>
      </div>
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
      {/* 아래 div가 화면 안에 들어올 때마다 무한 스크롤 요청 */}
      <div ref={ref} className='h-1' />
    </div>
  );
}
