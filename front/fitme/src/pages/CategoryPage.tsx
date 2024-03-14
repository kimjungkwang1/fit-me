import React from 'react';
import FilterBar from '../components/Common/FilterBar';
import CategoryItemList from '../components/Category/CategoryItemList';
import SortBar from '../components/Common/SortBar';

export default function CategoryPage() {
  return (
    <div>
      <FilterBar />
      <SortBar />
      <CategoryItemList />
    </div>
  );
}
