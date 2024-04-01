import React, { useEffect, useState } from 'react';
import CategoryItemList from '../components/Category/CategoryItemList';
import { useParams } from 'react-router-dom';
import axios from 'axios';

type OptionType = {
  id: number;
  name: string;
};

const CategoryDetailPage: React.FC = () => {
  const { id } = useParams();
  const [brands, setBrands] = useState<OptionType[]>([]);
  const [ages, setAges] = useState<OptionType[]>([]);

  useEffect(() => {
    axios.get(`https://fit-me.site/api/brands`).then(({ data }) => {
      setBrands(data);
    });
  }, []);

  useEffect(() => {
    setAges([
      { id: 10, name: '10대' },
      { id: 20, name: '20대' },
      { id: 30, name: '30대' },
      { id: 40, name: '40대' },
    ]);
  }, []);

  return (
    <>
      <CategoryItemList
        selectedBrands={brands.map((brand) => brand.id)}
        selectedCategories={[Number(id)]}
        selectedAges={ages.map((age) => age.id)}
        sortBy={''}
      />
    </>
  );
};

export default CategoryDetailPage;
