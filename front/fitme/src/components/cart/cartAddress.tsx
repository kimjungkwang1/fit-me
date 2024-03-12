import * as React from 'react';

export interface ICartAddressProps {
  address: ICartAddress;
}

export interface ICartAddress {
  name: String;
  phoneNumber: String;
  address: string;
}

export default function CartAddress(props: ICartAddressProps) {
  const { name, phoneNumber, address } = props.address;
  return (
    <>
      <div className='text-left'>
        <div className='p-2 flex justify-between'>
          <p>배송지</p>
          <p className='cursor-pointer text-blue-400 hover:text-blue-600 underline '>배송지변경</p>
        </div>
        <hr className='border-1 border-black' />
        <div className='p-2'>
          <div className='p-1'>{name}</div>
          <div className='p-1'>{phoneNumber}</div>
          <div className='p-1'>{address}</div>
        </div>
        <hr className='border-1 border-black' />
      </div>
    </>
  );
}
