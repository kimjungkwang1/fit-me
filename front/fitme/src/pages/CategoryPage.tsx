import axios from 'axios';
import FilterBar from '../components/Common/FilterBar';
import CategoryItemList from '../components/Category/CategoryItemList';
import SortBar from '../components/Common/SortBar';
import { useState, useEffect } from 'react';

type OptionType = {
  id: number;
  name: string;
  selected: boolean;
};

export default function CategoryPage() {
  // 브랜드 목록 구성
  const [brands, setBrands] = useState<OptionType[]>([]);
  useEffect(() => {
    axios.get(`https://fit-me.site/api/brands`).then(({ data }) => {
      setBrands(data);
    });
  }, []);

  // 카테고리 목록 구성
  const [categories, setCategories] = useState<OptionType[]>([]);
  useEffect(() => {
    function createCategory(id: number, name: string): OptionType {
      return {
        id,
        name,
        selected: false,
      };
    }

    const initialCategories: OptionType[] = [
      createCategory(1005, '맨투맨/스웨트셔츠'),
      createCategory(1006, '니트/스웨터'),
      createCategory(1010, '셔츠/블라우스'),
      createCategory(1001, '반소매 티셔츠'),
      createCategory(1004, '후드 티셔츠'),
      createCategory(1003, '피케/카라 티셔츠'),
      createCategory(1008, '기타상의'),
      createCategory(1013, '스포츠상의'),
      createCategory(1011, '민소매상의'),
      createCategory(3002, '데님 팬츠'),
      createCategory(3007, '코튼 팬츠'),
      createCategory(3004, '트레이닝/조거팬츠'),
      createCategory(3008, '슈트팬츠/슬랙스'),
      createCategory(3006, '기타바지'),
      createCategory(3011, '스포츠하의'),
      createCategory(3009, '숏팬츠'),
      createCategory(3005, '레깅스'),
    ];

    setCategories(initialCategories);
  }, []);

  // 선택된 브랜드 목록 구성
  const [selectedBrands, setSelectedBrands] = useState<number[]>([]);
  const selectedBrandsHandler = (id: number) => {
    if (selectedBrands) {
      const selectedIndex = selectedBrands.indexOf(id);

      if (selectedIndex !== -1) {
        // 요소를 제거
        const updated = [...selectedBrands];
        updated.splice(selectedIndex, 1);
        setSelectedBrands(updated);
      } else {
        // 요소를 추가
        setSelectedBrands([...selectedBrands, id]);
      }
    }

    if (brands) {
      const selectedIndex = brands.findIndex((brand) => brand.id === id);
      const updated = [...brands];
      const temp = brands[selectedIndex];
      temp.selected = !temp.selected;
      updated.splice(selectedIndex, 1, temp);
      setBrands(updated);
    }
  };

  // 선택된 카테고리 목록 구성
  const [selectedCategories, setSelectedCategories] = useState<number[]>([]);
  const selectedCategoriesHandler = (id: number) => {
    if (selectedCategories) {
      const selectedIndex = selectedCategories.indexOf(id);

      if (selectedIndex !== -1) {
        // 요소를 제거
        const updatedSelected = [...selectedCategories];
        updatedSelected.splice(selectedIndex, 1);
        setSelectedCategories(updatedSelected);
      } else {
        // 요소를 추가
        setSelectedCategories([...selectedCategories, id]);
      }
    }

    if (categories) {
      const selectedIndex = categories.findIndex((category) => category.id === id);
      const updated = [...categories];
      const temp = categories[selectedIndex];
      temp.selected = !temp.selected;
      updated.splice(selectedIndex, 1, temp);
      setCategories(updated);
    }
  };

  return (
    <div>
      <FilterBar
        brands={brands}
        selectedBrands={selectedBrands}
        selectedBrandsHandler={selectedBrandsHandler}
        categories={categories}
        selectedCategories={selectedCategories}
        selectedCategoriesHandler={selectedCategoriesHandler}
      />
      <SortBar />
      <CategoryItemList selectedBrands={selectedBrands} selectedCategories={selectedCategories} />
    </div>
  );
}
