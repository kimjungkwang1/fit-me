import React from 'react';
import SearchHistoryKeyword from './SearchHistoryKeyword';

type SearchType = {
  name: string;
  date: string;
};

type SearchHistoryProps = {
  searchKeyword: (k: string) => void;
};

export default function SearchHistory({ searchKeyword }: SearchHistoryProps) {
  console.log(JSON.parse(localStorage.getItem('recent')!));
  return (
    <div>
      {localStorage.getItem('recent') &&
        JSON.parse(localStorage.getItem('recent')!)
          .reverse()
          .map((search: SearchType, index: number) => (
            <SearchHistoryKeyword
              key={index}
              keyword={search.name}
              date={search.date}
              searchKeyword={searchKeyword}
            />
          ))}
    </div>
  );
}
