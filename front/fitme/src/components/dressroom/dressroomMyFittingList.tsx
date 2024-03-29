import React, { useState, useEffect } from 'react';
import { Button } from 'flowbite-react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { FaCheck } from 'react-icons/fa';
import { FaXmark } from 'react-icons/fa6';
import { deleteFittings, getFittings } from '../../store/dressroomSlice';
import { IoPerson } from 'react-icons/io5';
export default function DressroomMyFittingList() {
  const dispatch = useDispatch<AppDispatch>();
  const myfittings = useSelector((state: RootState) => state.dressroom.fittings);
  const [changemode, setChangemode] = useState<boolean>(false);

  useEffect(() => {
    dispatch(getFittings());
  }, []);
  return (
    <>
      <Button className='w-[50%] m-2 ml-auto' color='gray' onClick={() => setChangemode(true)}>
        프로필로 사용
      </Button>
      <div className='grid grid-cols-2 p-4'>
        {myfittings.map((item) => (
          <div className='relative my-2 p-2' key={item.id}>
            <div>
              <a href={item.url} className='w-[90%]'>
                <img src={item.url} alt='' />
              </a>
              {changemode ? (
                <IoPerson
                  className='absolute top-0 right-[-4px] p-2 bg-green-500 text-white opacity-100 rounded-full w-8 h-8 z-10 cursor-pointer'
                  onClick={() => {
                    console.log('변경완료');
                    setChangemode(false);
                  }}
                ></IoPerson>
              ) : (
                <button
                  className='absolute top-0 right-[-4px] p-2 bg-red-500 text-white opacity-100 rounded-full w-8 h-8 z-10'
                  onClick={() => dispatch(deleteFittings(item.id))}
                >
                  <FaXmark></FaXmark>
                </button>
              )}
            </div>
          </div>
        ))}
      </div>
    </>
  );
}
