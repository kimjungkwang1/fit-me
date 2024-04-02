import React from 'react';
import CategoryItemList from '../components/Category/CategoryItemList';
import { useParams } from 'react-router-dom';

const CategoryDetailPage: React.FC = () => {
  const { id } = useParams();

  return (
    <>
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
