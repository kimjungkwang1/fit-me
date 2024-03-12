import React from 'react';
import MainAdv from '../components/Main/MainAdv';
import MainMyItems from '../components/Main/MainMyItems';
import MainItemList from '../components/Main/MainItemList';

export default function MainPage() {
  return (
    <div>
      <MainAdv />
      <MainMyItems />
      <MainItemList />
    </div>
  );
}
