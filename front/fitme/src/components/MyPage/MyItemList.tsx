import React, { useEffect, useState } from 'react';
import { api } from '../../services/api';
import BoughtItem from './boughtItem';
import Item from '../Common/Item';

interface TabProps {
  tabName: string;
}

interface Product {
  id: number;
  brandName: string;
  categoryId: number;
  name: string;
  url: string;
}

interface OrderProduct {
  product: Product;
  color: string;
  size: string;
  price: number;
  count: number;
}

interface Order {
  id: number;
  status: string;
  orderProducts: OrderProduct[];
}

type ItemType1 = {
  id: number;
  name: string;
  url: string;
  brandName: string;
};

type ImageType = {
  id: number;
  url: string;
};

type BrandType = {
  id: number;
  name: string;
};

type ItemType2 = {
  id: number;
  name: string;
  price: number;
  mainImages: ImageType[];
  brand: BrandType;
  likeCount: number;
  reviewRating: number;
  reviewCount: number;
};

const MyItemList: React.FC<TabProps> = ({ tabName }) => {
  const [boughtlist, setBoughtList] = useState<ItemType1[]>();
  const [favlist, setFavList] = useState<ItemType2[]>();

  useEffect(() => {
    if (tabName === 'bought') {
      api.get('/api/orders').then(({ data }) => {
        // setList(data.product);
        api.get('/api/orders').then(({ data }: { data: Order[] }) => {
          const productList: ItemType1[] = data.flatMap((order) =>
            order.orderProducts.map((orderProduct) => ({
              id: orderProduct.product.id,
              name: orderProduct.product.name,
              url: orderProduct.product.url,
              brandName: orderProduct.product.brandName,
            }))
          );
          setBoughtList(productList);
        });
      });
    } else if (tabName === 'fav') {
      api.get('/api/products/favorites').then(({ data }) => {
        setFavList(data);
      });
    }
  }, [tabName]);

  return (
    <>
      <div>
        {tabName === 'bought' ? (
          <div className='grid grid-flow-row-dense grid-cols-3'>
            {boughtlist &&
              boughtlist.map((item) => (
                <div className='aspect-[4/7] flex justify-center p-1' key={item.id}>
                  <BoughtItem
                    id={item.id}
                    name={item.name}
                    url={item.url}
                    brandName={item.brandName}
                  />
                </div>
              ))}
          </div>
        ) : tabName === 'fav' ? (
          <div className='flex flex-wrap flex-row mx-[2%] place-content-start gap-y-3'>
            {favlist &&
              favlist.map((item, index) => (
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
        ) : null}
      </div>
    </>
  );
};

export default MyItemList;
