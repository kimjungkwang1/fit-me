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
      <div className='flex gap-4 px-10 py-4'>
        <div className='w-10 aspect-square'>
          <svg viewBox='0 0 24 24' fill='none' xmlns='http://www.w3.org/2000/svg'>
            <path
              clipRule='evenodd'
              d='M17 11C17 9.89543 17.8954 9 19 9H21V4H15.874C15.4299 5.72523 13.8638 7 12 7C10.1362 7 8.57006 5.72523 8.12602 4H3V9H5C6.10457 9 7 9.89543 7 11V20H17V11ZM2 11C1.44772 11 1 10.5523 1 10V4C1 2.89543 1.89543 2 3 2H9.5C9.77614 2 10 2.22386 10 2.5V3C10 4.10457 10.8954 5 12 5C13.1046 5 14 4.10457 14 3V2.5C14 2.22386 14.2239 2 14.5 2H21C22.1046 2 23 2.89543 23 4V10C23 10.5523 22.5523 11 22 11H19V20C19 21.1046 18.1046 22 17 22H7C5.89543 22 5 21.1046 5 20V11H2Z'
              fill='#000000'
              fillRule='evenodd'
            />
          </svg>
        </div>
        <div className='text-3xl font-bold'>상의</div>
      </div>
      <div className='grid grid-flow-row-dense grid-cols-4'>
        {category
          .filter((category) => category.id >= 1000 && category.id < 2000)
          .map((category) => (
            <CategoryList key={category.id} category={category} onClick={handleCategoryClick} />
          ))}
      </div>
      <div className='flex gap-4 px-10 py-4'>
        <div className='w-10 aspect-square'>
          <svg viewBox='0 0 24 24' fill='none' xmlns='http://www.w3.org/2000/svg'>
            <path
              clipRule='evenodd'
              d='M9.00109 13.3551C9.03666 12.2768 9.9211 11.4211 11 11.4211H13.125C14.2167 11.4211 15.1067 12.2965 15.1247 13.3881L15.2173 19H19V9.89998C16.7178 9.43671 15 7.41896 15 5H9C9 7.41896 7.28224 9.43671 5 9.89998V19H8.81488L9.00109 13.3551ZM5 7.82929C6.16519 7.41746 7 6.30622 7 5H5V7.82929ZM19 5V7.82929C17.8348 7.41746 17 6.30622 17 5H19ZM13.125 13.4211L13.2176 19.033C13.2356 20.1245 14.1256 21 15.2173 21H19C20.1046 21 21 20.1046 21 19V5C21 3.89543 20.1046 3 19 3H5C3.89543 3 3 3.89543 3 5V19C3 20.1046 3.89543 21 5 21H8.81488C9.89379 21 10.7782 20.1443 10.8138 19.0659L11 13.4211H13.125Z'
              fill='#000000'
              fillRule='evenodd'
            />
          </svg>
        </div>
        <div className='text-3xl font-bold'>하의</div>
      </div>
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
