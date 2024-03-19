import React, { useEffect, useState } from 'react';
import Item from '../Common/Item';
import axios from 'axios';

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

export default function MainItemList() {
  const [list, setList] = useState<ItemType[]>();

  useEffect(() => {
    axios.get(`http://j10a306.p.ssafy.io:8080/api/products`).then(({ data }) => {
      setList(data);
    });
  }, []);

  return (
    <div>
      <p className='text-start mx-[3%] mt-2 mb-1'>실시간 랭킹</p>
      <div className='flex flex-wrap flex-row mx-[2%] place-content-around gap-y-3'>
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
    </div>
  );
}
