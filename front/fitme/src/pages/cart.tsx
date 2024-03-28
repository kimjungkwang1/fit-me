import * as React from 'react';
import CartItem from '../components/cart/cartItem';
import CartAddress from '../components/cart/cartAddress';
import { Button } from 'flowbite-react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../store/store';

const address = {
  name: '배송받는사람이름',
  phoneNumber: '01012345678',
  address: '주소 뭐시기',
};

export default function Cart() {
  const navigate = useNavigate();
  const items = useSelector((state: RootState) => state.cart.items);
  const address = useSelector((state: RootState) => state.cart.address);
  return (
    <>
      <div className='flex justify-center'>
        <div className='aspect-iphone h-screen'>
          <Button color='gray' className='mb-2 w-[100%]' onClick={() => navigate('/dressroom')}>
            드레스룸 가기
          </Button>
          {items.map((item) => (
            <CartItem key={item.id} item={item} />
          ))}
          <CartAddress address={address}></CartAddress>
          <Button color='gray' className='my-2 w-[100%]'>
            결제하기 |200,000
          </Button>
        </div>
      </div>
    </>
  );
}
