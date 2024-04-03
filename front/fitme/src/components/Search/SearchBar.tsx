import { useEffect, useState } from 'react';
import { BiSearch } from 'react-icons/bi';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../store/store';
import { setKeyword } from '../../store/searchSlice';

export default function SearchBar() {
  const dispatch = useDispatch<AppDispatch>();
  const keyword = useSelector((state: RootState) => state.search.keyword);

  useEffect(() => {
    setTypingKeyword(keyword);
  }, [keyword]);

  const [typingKeyword, setTypingKeyword] = useState<string>(keyword);
  const onChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTypingKeyword(e.target.value);
  };
  const onKeyPressHandler = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      if (typingKeyword === '') {
        alert('검색어를 입력하세요.');
        return;
      }
      // 엔터 키를 눌렀을 때만 searchKeyword 호출
      dispatch(setKeyword(typingKeyword));
      // 최근 검색어 저장할 배열이 없으면 선언
      if (!localStorage.getItem('recent')) {
        localStorage.setItem('recent', JSON.stringify([]));
      }
      // 최근 검색어 저장
      if (typingKeyword.length > 1) {
        let recent = localStorage.getItem('recent');
        let recentJSON = JSON.parse(recent!);
        if (recentJSON.length >= 10) {
          recentJSON.splice(0, 1);
        }
        // KST 시간 받기
        const locale = new Date().toLocaleDateString('en-US', { timeZone: 'Asia/Seoul' });
        recentJSON!.push({ name: typingKeyword, date: locale });
        localStorage.setItem('recent', JSON.stringify(recentJSON));
      }
    }
  };

  return (
    <>
      <div className='relative'>
        <div className='bg-gray-200 rounded-lg m-2 px-2 py-1 text-sm flex flex-row items-center'>
          <BiSearch className='text-gray-500 text-lg mx-1' />
          <input
            type='text'
            placeholder='상품을 검색해보세요'
            value={typingKeyword}
            className='bg-gray-200 w-full focus: outline-none ml-1'
            onChange={onChangeHandler}
            onKeyDown={onKeyPressHandler}
          />
        </div>
      </div>
    </>
  );
}
