import React, { useState } from 'react';
import FeedListMagazine from './FeedListMagazine';
import FeedListUser from './FeedListUser';
export default function FeedTab() {
  type selectedTabType = '매거진' | '유저피드';
  const [tab, setTabs] = useState<selectedTabType>('매거진');
  return (
    <>
      <div className='flex'>
        <div
          className={`w-[50%] text-center shadow-sm mb-2 border p-2 ${
            tab === '매거진' ? 'bg-white' : 'bg-gray-300 text-gray-100'
          }`}
          onClick={() => setTabs('매거진')}
        >
          매거진
        </div>
        <div
          className={`w-[50%] text-center shadow-sm mb-2 border p-2 ${
            tab === '유저피드' ? 'bg-white' : 'bg-gray-300 text-gray-100'
          }`}
          onClick={() => setTabs('유저피드')}
        >
          유저피드
        </div>
      </div>
      {tab === '매거진' ? <FeedListMagazine /> : <FeedListUser />}
    </>
  );
}
