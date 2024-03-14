import React from 'react';
import SearchHistoryKeyword from './SearchHistoryKeyword';

export default function SearchHistory() {
  return (
    <div>
      <SearchHistoryKeyword keyword='후드티' date='3.21' />
      <SearchHistoryKeyword keyword='슬랙스' date='3.21' />
      <SearchHistoryKeyword keyword='청자켓' date='3.20' />
      <SearchHistoryKeyword keyword='원피스' date='3.20' />
      <SearchHistoryKeyword keyword='우너피스' date='3.09' />
    </div>
  );
}
