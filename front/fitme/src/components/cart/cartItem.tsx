import * as React from 'react';
import { IoIosCheckmarkCircle, IoIosCheckmarkCircleOutline } from 'react-icons/io';

export interface ICardItemProps {
  item: ICartItem;
}

export interface ICartItem {
  name: string;
  price: number;
  color: string;
  size: string;
  count: number;
  isChecked: boolean;
}
function priceToString(price: number) {
  return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

export default function CardItem(props: ICardItemProps) {
  const { name, price, color, size, count, isChecked } = props.item;
  return (
    <>
      <div className='flex mb-10'>
        <div>
          {isChecked ? (
            <IoIosCheckmarkCircle size='24px'></IoIosCheckmarkCircle>
          ) : (
            <IoIosCheckmarkCircleOutline size='24px'></IoIosCheckmarkCircleOutline>
          )}
        </div>
        <div className='bg-blue-200 w-[20%] pt-[26.66%]'></div>
        <div className='flex-grow text-left'>
          <p className='p-2 font-bold'>Name: {name}</p>
          <p className='pl-2'>Color: {color}</p>
          <p className='pl-2'>Size: {size}</p>
          <p className='text-right p-2'>Price: {priceToString(price * count)}원</p>
        </div>
        <div className='w-[5%] h-[5%]'>X</div>
      </div>
    </>
  );
}
