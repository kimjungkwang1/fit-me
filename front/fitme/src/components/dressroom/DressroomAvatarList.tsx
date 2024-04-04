import React from 'react';
import { useSelector } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { useDispatch } from 'react-redux';

export default function DressroomAvatarList() {
  const models = useSelector((state: RootState) => state.dressroom.models);
  return (
    <>
      <div className='grid grid-cols-2 mt-2'>
        {models.map((item) => (
          <img className='w-[60%] h-auto object-contain mx-auto ' src={item.url} alt='상의'></img>
        ))}
      </div>
    </>
  );
}
