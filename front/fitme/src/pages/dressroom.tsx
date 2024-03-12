import React from 'react';
import { Tabs } from 'flowbite-react';
import { Button } from 'flowbite-react';
import DressroomNowFitting from '../components/dressroom/dressroomNowFitting';
import DressroomItemList from '../components/dressroom/dressroomItemList';
import DressroomMyFittingList from '../components/dressroom/dressroomMyFittingList';

export default function Dressroom() {
  return (
    <>
      <div className='flex justify-center'>
        <div className='aspect-iphone h-screen'>
          <div className=''>
            <Tabs style='fullWidth'>
              <Tabs.Item active title='피팅해보기'>
                <div className='flex flex-col'>
                  <div className='flex items-center justify-center'>
                    <div className='w-[35%] p-2'>
                      <DressroomNowFitting></DressroomNowFitting>
                    </div>
                    <div className='flex flex-col flex-grow p-2'>
                      <div className='w-[50%] pb-[69.33%] bg-gray-100 mx-auto m-2'></div>
                      <Button className='w-[50%] mx-auto' color='gray'>
                        피팅저장
                      </Button>
                    </div>
                  </div>
                  <Tabs style='fullWidth'>
                    <Tabs.Item active title='장바구니'>
                      <DressroomItemList></DressroomItemList>
                    </Tabs.Item>
                    <Tabs.Item title='구매 목록'>
                      <DressroomItemList></DressroomItemList>
                    </Tabs.Item>
                  </Tabs>
                </div>
              </Tabs.Item>
              <Tabs.Item title='내 피팅목록'>
                <DressroomMyFittingList></DressroomMyFittingList>
              </Tabs.Item>
            </Tabs>
          </div>
        </div>
      </div>
    </>
  );
}
