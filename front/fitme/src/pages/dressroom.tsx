import React, { useState } from 'react';
import { Tabs } from 'flowbite-react';
import DressroomNowFitting from '../components/dressroom/dressroomNowFitting';
import DressroomItemList from '../components/dressroom/dressroomItemList';
import DressroomMyFittingList from '../components/dressroom/dressroomMyFittingList';
import DressroomButton from '../components/dressroom/DressroomButton';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../store/store';
import { getFittings2 } from '../store/dressroomSlice';
type selectedTabType = '피팅 해보기' | '내 피팅 목록';
export default function Dressroom() {
  const dispatch = useDispatch<AppDispatch>();
  const result = useSelector((state: RootState) => state.dressroom.result);
  const [tab, setTabs] = useState<selectedTabType>('피팅 해보기');
  return (
    <>
      <div className='h-full max-h-screen'>
        <div className='flex text-sm'>
          <div
            className={`w-[50%] text-center shadow-sm mb-2 border p-2 ${
              tab === '피팅 해보기' ? 'bg-white' : 'bg-gray-300 text-gray-100'
            }`}
            onClick={() => setTabs('피팅 해보기')}
          >
            피팅 해보기
          </div>
          <div
            className={`w-[50%] text-center shadow-sm mb-2 border p-2 ${
              tab === '내 피팅 목록' ? 'bg-white' : 'bg-gray-300 text-gray-100'
            }`}
            onClick={() => {
              setTabs('내 피팅 목록');
              dispatch(getFittings2());
            }}
          >
            내 피팅 목록
          </div>
        </div>
        {tab === '피팅 해보기' ? (
          <div className='flex flex-col h-[50%]'>
            <div className='flex items-center justify-center h-[80%]'>
              <div className='w-[40%] p-2'>
                <DressroomNowFitting />
              </div>
              <div className='flex flex-col flex-grow p-2 h-full flex-grow'>
                <img
                  className='h-[80%] bg-gray-100 mx-auto m-2'
                  src={result.url}
                  alt='피팅결과'
                ></img>
                <DressroomButton />
              </div>
            </div>
            <DressroomItemList />
          </div>
        ) : (
          <DressroomMyFittingList />
        )}
      </div>
    </>
  );
}
