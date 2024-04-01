import React, { useState } from 'react';
import { Tabs } from 'flowbite-react';
import DressroomNowFitting from '../components/dressroom/dressroomNowFitting';
import DressroomItemList from '../components/dressroom/dressroomItemList';
import DressroomMyFittingList from '../components/dressroom/dressroomMyFittingList';
import DressroomButton from '../components/dressroom/DressroomButton';
import { useSelector } from 'react-redux';
import { RootState } from '../store/store';
type selectedTabType = '피팅 해보기' | '내 피팅 목록';
export default function Dressroom() {
  const result = useSelector((state: RootState) => state.dressroom.result);
  const [tab, setTabs] = useState<selectedTabType>('피팅 해보기');
  return (
    <>
      <div className=''>
        <div className='flex'>
          <div
            className={`w-[50%] text-center shadow-sm mb-2 border p-2 ${
              tab === '피팅 해보기' ? 'bg-gray-300 ' : 'bg-white'
            }`}
            onClick={() => setTabs('피팅 해보기')}
          >
            피팅 해보기
          </div>
          <div
            className={`w-[50%] text-center shadow-sm mb-2 border p-2 ${
              tab === '내 피팅 목록' ? 'bg-gray-300' : 'bg-white'
            }`}
            onClick={() => setTabs('내 피팅 목록')}
          >
            내 피팅 목록
          </div>
        </div>
        {tab === '피팅 해보기' ? (
          <div className='flex flex-col'>
            <div className='flex items-center justify-center'>
              <div className='w-[60%] p-2'>
                <DressroomNowFitting />
              </div>
              <div className='flex flex-col flex-grow p-2'>
                <img
                  className='w-[80%] bg-gray-100 mx-auto m-2'
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
