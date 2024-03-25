import * as React from 'react';
import CartItem from '../components/cart/cartItem';
import CartAddress from '../components/cart/cartAddress';
import { Button } from 'flowbite-react';
import { useNavigate } from 'react-router-dom';
const item1 = {
  name: '맨투맨1',
  price: 20000,
  color: 'navy',
  size: 'M',
  count: 1,
  isChecked: true,
};

const item2 = {
  name: '바지1',
  price: 25000,
  color: 'black',
  size: 'M',
  count: 2,
  isChecked: true,
};

const item3 = {
  name: '맨투맨2',
  price: 30000,
  color: 'white',
  size: 'M',
  count: 1,
  isChecked: true,
};

const address = {
  name: '배송받는사람이름',
  phoneNumber: '01012345678',
  address: '주소 뭐시기',
};

export default function Cart() {
  const navigate = useNavigate();

  return (
    <>
      <div className='flex justify-center'>
        <div className='aspect-iphone h-screen'>
          <Button color='gray' className='mb-2 w-[100%]' onClick={() => navigate('/dressroom')}>
            드레스룸 가기
          </Button>
          <CartItem item={item1}></CartItem>
          <CartItem item={item2}></CartItem>
          <CartItem item={item3}></CartItem>
          <CartItem item={item3}></CartItem>
          <CartItem item={item3}></CartItem>
          <CartItem item={item3}></CartItem>
          <CartItem item={item3}></CartItem>
          <CartAddress address={address}></CartAddress>
          <Button color='gray' className='my-2 w-[100%]'>
            결제하기 |200,000
          </Button>
        </div>
      </div>
    </>
  );
}
