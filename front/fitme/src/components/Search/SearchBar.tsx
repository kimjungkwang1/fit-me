import { useState } from 'react';
import { BiSearch } from 'react-icons/bi';

type SearchBarProps = {
  searchKeyword: (k: string) => void;
};

export default function SearchBar({ searchKeyword }: SearchBarProps) {
  const [typingKeyword, setTypingKeyword] = useState<string>('');
  const onChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTypingKeyword(e.target.value);
  };
  const onKeyPressHandler = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      // 엔터 키를 눌렀을 때만 searchKeyword 호출
      searchKeyword(typingKeyword);
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
            className='bg-gray-200 w-full focus: outline-none ml-1'
            onChange={onChangeHandler}
            onKeyDown={onKeyPressHandler}
          />
        </div>
      </div>
    </>
  );
}
