import { LuHistory } from 'react-icons/lu';
import { RxCross2 } from 'react-icons/rx';
import { useDispatch } from 'react-redux';
import { AppDispatch } from '../../store/store';
import { setKeyword } from '../../store/searchSlice';

type Keyword = {
  keyword: string;
  date: string;
};

export default function SearchHistoryKeyword({ keyword, date }: Keyword) {
  const dispatch = useDispatch<AppDispatch>();
  const searchKeyword = (k: string) => {
    dispatch(setKeyword(k));
  };

  return (
    <>
      <div className='flex flex-row m-3'>
        {/* 아이콘 */}
        <div className='w-8 h-8 rounded-full bg-gray-300 p-1'>
          <LuHistory className='w-full h-full text-gray-600' />
        </div>

        {/* 검색어 기록 */}
        <div className='flex flex-row items-center' onClick={() => searchKeyword(keyword)}>
          <span className='align-middle text-sm mx-3 truncate'>{keyword}</span>
        </div>

        {/* 검색일 및 저장된 검색어 삭제 */}
        <div className='flex flex-row flex-grow justify-end items-center'>
          <div>
            <span className='flex flex-grow align-middle text-xs'>{date}</span>
          </div>
        </div>
      </div>
    </>
  );
}
