import * as React from 'react';
import { IoIosCheckmarkCircle, IoIosCheckmarkCircleOutline } from 'react-icons/io';
import { useDispatch } from 'react-redux';
import { GrPrevious, GrNext } from 'react-icons/gr';
import { toggleItemChecked, deleteCartItem, updateQuantity, getCart } from '../../store/cartSlice';
import { AppDispatch } from '../../store/store';
export interface ICardItemProps {
  item: ICartItem;
}

export interface ICartItem {
  id: number;
  productId: number;
  name: string;
  price: number;
  color: string;
  size: string;
  url: string;
  quantity: number;
  stockQuantity: number;
  isChecked: boolean;
  category: number;
}
const priceToString = (price: number) => {
  return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
};

export default function CardItem(props: ICardItemProps) {
  const { id, productId, name, price, color, size, quantity, isChecked, url, stockQuantity } =
    props.item;

  const dispatch = useDispatch<AppDispatch>();

  const togleIsChecked = (id: number) => {
    dispatch(toggleItemChecked(id));
  };
  const dleteItem = async (ids: number[]) => {
    await dispatch(deleteCartItem(ids));
    dispatch(getCart());
  };
  const updateCount = ({ id, quantity }: { id: number; quantity: number }) => {
    dispatch(updateQuantity({ id, quantity }));
  };

  return (
    <>
      <div className='flex mb-10'>
        <div onClick={() => togleIsChecked(id)}>
          {isChecked ? (
            <IoIosCheckmarkCircle size='24px'></IoIosCheckmarkCircle>
          ) : (
            <IoIosCheckmarkCircleOutline size='24px'></IoIosCheckmarkCircleOutline>
          )}
        </div>
        <img className='w-[25%] h-auto object-contain' src={url} alt='' />
        <div className='flex-grow text-left'>
          <p className='p-2 font-bold'>{name}</p>
          <p className='pl-2'>
            {color}/{size}
          </p>
          <div className='flex items-center'>
            {quantity === 1 ? (
              <GrPrevious color='gray'></GrPrevious>
            ) : (
              <GrPrevious
                onClick={() => updateCount({ id: id, quantity: quantity - 1 })}
              ></GrPrevious>
            )}
            <p>{quantity}</p>
            {quantity === stockQuantity ? (
              <GrNext color='gray'></GrNext>
            ) : (
              <GrNext onClick={() => updateCount({ id: id, quantity: quantity + 1 })}></GrNext>
            )}
          </div>
          <p className='text-right p-2'>Price: {priceToString(price)}원</p>
        </div>
        <div className='w-[5%] h-[5%]' onClick={() => dleteItem([id])}>
          X
        </div>
      </div>
    </>
  );
}
