import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { api } from '../../services/api';
import BoughtItem from './boughtItem';

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

type ItemType = {
  id: number;
  name: string;
  url: string;
  brandName: string;
};

const MyItemList: React.FC<TabProps> = ({ tabName }) => {
  const [list, setList] = useState<ItemType[]>();

  useEffect(() => {
    if (tabName === 'bought') {
      api.get('/api/orders').then(({ data }) => {
        // setList(data.product);
        api.get('/api/orders').then(({ data }: { data: Order[] }) => {
          const productList: ItemType[] = data.flatMap((order) =>
            order.orderProducts.map((orderProduct) => ({
              id: orderProduct.product.id,
              name: orderProduct.product.name,
              url: orderProduct.product.url,
              brandName: orderProduct.product.brandName,
            }))
          );
          setList(productList);
        });
      });
    } else if (tabName === 'fav') {
      api.get('/api/products/favorites').then(({ data }) => {
        console.log(data);
      });
    }
  }, []);

  return (
    <>
      <div>
        <div className='grid grid-flow-row-dense grid-cols-3 auto-fill'>
          {list &&
            list.map((item, index) => (
              <div className='aspect-[3/5] flex justify-center p-1'>
                <BoughtItem
                  key={index}
                  id={item.id}
                  name={item.name}
                  url={item.url}
                  brandName={item.brandName}
                />
              </div>
            ))}
        </div>
      </div>
    </>
  );
};

export default MyItemList;
