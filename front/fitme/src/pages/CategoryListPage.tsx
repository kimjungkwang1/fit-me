import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CategoryList from '../components/Category/CategoryList';

type OptionType = {
  id: number;
  name: string;
};

const CategoryListPage: React.FC = () => {
  const navigate = useNavigate(); // useNavigate 사용

  // 카테고리 목록 구성
  const [category, setCategory] = useState<OptionType[]>([]);

  useEffect(() => {
    function createCategory(id: number, name: string): OptionType {
      return {
        id,
        name,
      };
    }

    const initialCategories: OptionType[] = [
      createCategory(1005, '맨투맨/스웨트셔츠'),
      createCategory(1006, '니트/스웨터'),
      createCategory(1010, '셔츠/블라우스'),
      createCategory(1001, '반소매 티셔츠'),
      createCategory(1004, '후드 티셔츠'),
      createCategory(1003, '피케/카라 티셔츠'),
      createCategory(1013, '스포츠상의'),
      createCategory(1011, '민소매상의'),
      createCategory(1008, '기타상의'),
      createCategory(3002, '데님 팬츠'),
      createCategory(3007, '코튼 팬츠'),
      createCategory(3004, '트레이닝/조거팬츠'),
      createCategory(3008, '슈트팬츠/슬랙스'),
      createCategory(3011, '스포츠하의'),
      createCategory(3009, '숏팬츠'),
      createCategory(3005, '레깅스'),
      createCategory(3006, '기타바지'),
    ];

    setCategory(initialCategories);
  }, []);

  const handleCategoryClick = (category: OptionType) => {
    navigate(`/category/${category.id}`);
  };

  return (
    <>
      <div className='px-10 py-4 text-3xl font-bold'>상의</div>
      <div className='grid grid-flow-row-dense grid-cols-4'>
        {category
          .filter((category) => category.id >= 1000 && category.id < 2000)
          .map((category) => (
            <CategoryList key={category.id} category={category} onClick={handleCategoryClick} />
          ))}
      </div>
      <div className='px-10 py-4 text-3xl font-bold'>하의</div>
      <div className='grid grid-flow-row-dense grid-cols-4'>
        {category
          .filter((category) => category.id >= 3000 && category.id < 4000)
          .map((category) => (
            <CategoryList key={category.id} category={category} onClick={handleCategoryClick} />
          ))}
      </div>
    </>
  );
};

export default CategoryListPage;
