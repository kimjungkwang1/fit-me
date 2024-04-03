import React, { useEffect } from 'react';
import CartItem from '../components/cart/cartItem';
import CartAddress from '../components/cart/cartAddress';
import { Button } from 'flowbite-react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState, AppDispatch } from '../store/store';
import { getCart, getAddress, order } from '../store/cartSlice';
import { useDispatch } from 'react-redux';
import { TbMoodEmpty } from 'react-icons/tb';
export default function Cart() {
  const navigate = useNavigate();
  const items = useSelector((state: RootState) => state.cart.items);
  const address = useSelector((state: RootState) => state.cart.address);
  const totalPrice = items.reduce((total, item) => {
    return item.isChecked ? total + item.price : total;
  }, 0);
  const dispatch = useDispatch<AppDispatch>();

  useEffect(() => {
    dispatch(getCart());
    dispatch(getAddress());
  }, []);
  return (
    <>
      <div className='w-full flex justify-center'>
        <div className='w-full h-screen'>
          <Button color='gray' className='mb-2 w-[100%]' onClick={() => navigate('/dressroom')}>
            드레스룸 가기
          </Button>
          {items.length === 0 ? (
            <div className='flex flex-col items-center p-2'>
              <TbMoodEmpty className='text-7xl'></TbMoodEmpty>
              <div className='mx-auto'>장바구니가 비었어요</div>
              <Button color='gray' className='' onClick={() => navigate('/home')}>
                상품 담으러가기
              </Button>
            </div>
          ) : (
            items.map((item) => <CartItem key={item.id} item={item} />)
          )}
          <CartAddress address={address}></CartAddress>
          <Button
            color='gray'
            className='my-2 w-[100%]'
            onClick={() => {
              try {
                if (items.length > 0) {
                  dispatch(order());
                  navigate('/payment/complete');
                }
              } catch (error) {}
            }}
          >
            결제하기 |{totalPrice.toLocaleString()}
          </Button>
        </div>
      </div>
    </>
  );
}
