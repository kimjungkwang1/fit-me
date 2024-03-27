import React from 'react';
import SearchHistory from '../components/Search/SearchHistory';
import SearchResult from '../components/Search/SearchResult';
import FilterBar from '../components/Common/FilterBar';
import SortBar from '../components/Common/SortBar';

export default function SearchPage() {
  return (
    <div>
      {/* 검색 기록 */}
      <SearchHistory />

      {/* 검색 결과 */}
      {/* <FilterBar /> */}
      <SortBar />
      <SearchResult />
    </div>
  );
}
