import { LuHistory } from 'react-icons/lu';
import { RxCross2 } from 'react-icons/rx';

type Keyword = {
  keyword: string;
  date: string;
};

export default function SearchHistoryKeyword({ keyword, date }: Keyword) {
  return (
    <>
      <div className='flex flex-row m-3'>
        {/* 아이콘 */}
        <div className='w-8 h-8 rounded-full bg-gray-300 p-1'>
          <LuHistory className='w-full h-full text-gray-600' />
        </div>

        {/* 검색어 기록 */}
        <div>
          <span className='align-middle text-sm mx-3 truncate'>{keyword}</span>
        </div>

        {/* 검색일 및 저장된 검색어 삭제 */}
        <div className='flex flex-row flex-grow justify-end items-center'>
          <div>
            <span className='flex flex-grow align-middle text-xs'>{date}</span>
          </div>
          <div>
            <RxCross2 className='flex flex-grow align-middle text-xs m-1' />
          </div>
        </div>
      </div>
    </>
  );
}
