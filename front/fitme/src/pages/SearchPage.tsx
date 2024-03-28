import React from 'react';
import SearchHistory from '../components/Search/SearchHistory';
import SearchResult from '../components/Search/SearchResult';
import FilterBar from '../components/Common/FilterBar';

export default function SearchPage() {
  return (
    <div>
      {/* 검색 기록 */}
      <SearchHistory />

      {/* 검색 결과 */}
      {/* <FilterBar /> */}
      <SearchResult />
    </div>
  );
}
