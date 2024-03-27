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

const ItemList: React.FC = () => {
  const [list, setList] = useState<ItemType[]>();

  useEffect(() => {
    axios.get(`https://fit-me.site/api/products`).then(({ data }) => {
      setList(data);
    });
  }, []);

  return (
    <div>
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
};

export default ItemList;
