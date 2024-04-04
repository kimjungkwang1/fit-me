import React from 'react';
import CategoryItemList from '../components/Category/CategoryItemList';
import { useParams } from 'react-router-dom';

const CategoryDetailPage: React.FC = () => {
  const { id } = useParams();

  return (
    <>
      <div className='w-full mb-3'></div>
      <CategoryItemList
        selectedBrands={[]}
        selectedCategories={[Number(id)]}
        selectedAges={[]}
        sortBy={''}
      />
    </>
  );
};

export default CategoryDetailPage;
