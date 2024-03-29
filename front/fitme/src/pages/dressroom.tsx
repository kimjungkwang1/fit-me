import React from 'react';
import { Tabs } from 'flowbite-react';
import DressroomNowFitting from '../components/dressroom/dressroomNowFitting';
import DressroomItemList from '../components/dressroom/dressroomItemList';
import DressroomMyFittingList from '../components/dressroom/dressroomMyFittingList';
import DressroomButton from '../components/dressroom/DressroomButton';
import { useSelector } from 'react-redux';
import { RootState } from '../store/store';
export default function Dressroom() {
  const result = useSelector((state: RootState) => state.dressroom.result);
  return (
    <>
      <div className=''>
        <Tabs style='fullWidth'>
          <Tabs.Item active title='피팅해보기'>
            <div className='flex flex-col'>
              <div className='flex items-center justify-center'>
                <div className='w-[35%] p-2'>
                  <DressroomNowFitting />
                </div>
                <div className='flex flex-col flex-grow p-2'>
                  <img
                    className='w-[70%] bg-gray-100 mx-auto m-2'
                    src={result.url}
                    alt='피팅결과'
                  ></img>
                  <DressroomButton />
                </div>
              </div>
              <DressroomItemList />
            </div>
          </Tabs.Item>
          <Tabs.Item title='내 피팅목록'>
            <DressroomMyFittingList />
          </Tabs.Item>
        </Tabs>
      </div>
    </>
  );
}
