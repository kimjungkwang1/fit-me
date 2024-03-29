import React, { useState, useEffect } from 'react';

import { Button } from 'flowbite-react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { CiCircleInfo } from 'react-icons/ci';
import {
  setNowTop,
  setNowBottom,
  makeFittings,
  getOrders,
  getCartForDressroom,
} from '../../store/dressroomSlice';

type selectedButtonType = '전체' | '상의' | '하의';
type selectedTabType = '장바구니' | '구매목록';
export default function DressroomItemLst() {
  const [selected1, setSelected1] = useState<selectedButtonType>('전체');
  const [selected2, setSelected2] = useState<selectedButtonType>('전체');

  const [tab, setTabs] = useState<selectedTabType>('장바구니');

  const dispatch = useDispatch<AppDispatch>();
  const cartItems = useSelector((state: RootState) => state.dressroom.cartItems);
  const orderItems = useSelector((state: RootState) => state.dressroom.orders);
  useEffect(() => {
    dispatch(getOrders());
    dispatch(getCartForDressroom());
  }, []);
  const select1 = (s: selectedButtonType) => {
    setSelected1(s);
  };
  const select2 = (s: selectedButtonType) => {
    setSelected2(s);
  };
  //상의 혹은 하의 변경후 피팅 생성
  const [shouldMakeFittings, setShouldMakeFittings] = useState<boolean>(false);
  const handleFitting = async (id: number, url: string, category: number) => {
    if (Math.floor(category / 1000) === 1) {
      dispatch(setNowTop({ id: id, url: url }));
      setShouldMakeFittings(true);
    }
    if (Math.floor(category / 1000) === 3) {
      dispatch(setNowBottom({ id: id, url: url }));
      setShouldMakeFittings(true);
    }
  };
  useEffect(() => {
    const makeFittingIfNeeded = async () => {
      if (shouldMakeFittings) {
        await dispatch(makeFittings());
        setShouldMakeFittings(false); // 동작 완료 후 상태 초기화
      }
    };

    makeFittingIfNeeded();
  }, [shouldMakeFittings, dispatch]);
  return (
    <>
      <div className='flex'>
        <div
          className={`w-[50%] text-center shadow-sm mb-2 border p-2 ${
            tab === '장바구니' ? 'bg-gray-300 ' : 'bg-white'
          }`}
          onClick={() => setTabs('장바구니')}
        >
          장바구니
        </div>
        <div
          className={`w-[50%] text-center shadow-sm mb-2 border p-2 ${
            tab === '구매목록' ? 'bg-gray-300' : 'bg-white'
          }`}
          onClick={() => setTabs('구매목록')}
        >
          구매목록
        </div>
      </div>
      {tab === '장바구니' ? (
        <div>
          <div className='flex justify-center gap-4 mb-2'>
            <Button
              color={selected1 === '전체' ? 'dark' : 'gary'}
              pill
              onClick={() => select1('전체')}
            >
              전체
            </Button>
            <Button
              color={selected1 === '상의' ? 'dark' : 'gary'}
              pill
              onClick={() => select1('상의')}
            >
              상의
            </Button>
            <Button
              color={selected1 === '하의' ? 'dark' : 'gary'}
              pill
              onClick={() => select1('하의')}
            >
              하의
            </Button>
          </div>
          <div className='grid grid-cols-5 gap-2 overflow-hidden hover:overflow-auto  h-[30vh]'>
            {cartItems
              .filter((item) => {
                if (selected1 === '전체') return true;
                if (selected1 === '상의') return Math.floor(item.category / 1000) === 1;
                if (selected1 === '하의') return Math.floor(item.category / 1000) === 3;
                return true;
              })
              .map((item) => (
                <div className='p-2' key={item.id}>
                  <img
                    className=''
                    src={item.url}
                    alt='상품'
                    onClick={() => handleFitting(item.productId, item.url, item.category)}
                  ></img>
                  <a href={'https://fit-me.site/detail/' + item.productId}>
                    <CiCircleInfo className='mx-auto'></CiCircleInfo>
                  </a>
                </div>
              ))}
          </div>
        </div>
      ) : (
        <div>
          <div className='flex justify-center gap-4 mb-2'>
            <Button
              color={selected2 === '전체' ? 'dark' : 'gary'}
              pill
              onClick={() => select2('전체')}
            >
              전체
            </Button>
            <Button
              color={selected2 === '상의' ? 'dark' : 'gary'}
              pill
              onClick={() => select2('상의')}
            >
              상의
            </Button>
            <Button
              color={selected2 === '하의' ? 'dark' : 'gary'}
              pill
              onClick={() => select2('하의')}
            >
              하의
            </Button>
          </div>
          <div className='grid grid-cols-5 gap-2 overflow-hidden hover:overflow-auto  h-[30vh]'>
            {orderItems
              .filter((item) => {
                if (selected2 === '전체') return true;
                if (selected2 === '상의') return Math.floor(item.category / 1000) === 1;
                if (selected2 === '하의') return Math.floor(item.category / 1000) === 3;
                return true;
              })
              .map((item) => (
                <div className='p-2' key={item.id}>
                  <img
                    src={item.url}
                    alt='상품'
                    onClick={() => handleFitting(item.productId, item.url, item.category)}
                  ></img>
                  <a href={'https://fit-me.site/detail/' + item.productId}>
                    <CiCircleInfo className='mx-auto'></CiCircleInfo>
                  </a>
                </div>
              ))}
          </div>
        </div>
      )}
    </>
  );
}
