import axios from 'axios';
import { Button, Modal } from 'flowbite-react';
import { useEffect, useState } from 'react';
import FilterButton from './FilterButton';

type OptionType = {
  id: number;
  name: string;
  selected: boolean;
};

export default function FilterBar() {
  const [openModal, setOpenModal] = useState(false);

  // 브랜드 목록 구성
  const [brands, setBrands] = useState<OptionType[]>();
  useEffect(() => {
    axios.get(`https://fit-me.site/api/brands`).then(({ data }) => {
      setBrands(data);
    });
  }, []);

  // 카테고리 목록 구성
  const [categories, setCategories] = useState<OptionType[]>();
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
  const selectedBrandsHandler = (id: number, index: number) => {
    if (selectedBrands) {
      const index = selectedBrands.indexOf(id);

      if (index !== -1) {
        // 요소를 제거
        const updated = [...selectedBrands];
        updated.splice(index, 1);
        setSelectedBrands(updated);
      } else {
        // 요소를 추가
        setSelectedBrands([...selectedBrands, id]);
      }
    }

    if (brands) {
      const updated = [...brands];
      const temp = brands[index];
      temp.selected = !temp.selected;
      updated.splice(index, 1, temp);
      setBrands(updated);
    }

    console.log(selectedBrands);
  };

  // 선택된 카테고리 목록 구성
  const [selectedCategories, setSelectedCategories] = useState<number[]>([]);
  const selectedCategoriesHandler = (id: number, index: number) => {
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
      const updated = [...categories];
      const temp = categories[index];
      temp.selected = !temp.selected;
      updated.splice(index, 1, temp);
      setCategories(updated);
    }

    console.log(selectedCategories);
  };

  return (
    <div className='flex flex-row gap-1 mx-[3%] mt-2 mb-1'>
      <Button color='light' size='xs' onClick={() => setOpenModal(true)}>
        브랜드
      </Button>
      <Button color='light' size='xs' onClick={() => setOpenModal(true)}>
        카테고리
      </Button>
      <Button color='light' size='xs' onClick={() => setOpenModal(true)}>
        가격
      </Button>
      <Modal show={openModal} popup>
        <Modal.Body>
          <div>
            <span className='flex font-semibold mt-2 mb-1'>브랜드</span>
            <div className='flex flex-row flex-wrap gap-2'>
              {brands &&
                brands.map((brand, index) => (
                  <FilterButton
                    key={index}
                    index={index}
                    id={brand.id}
                    name={brand.name}
                    selected={brand.selected}
                    handler={selectedBrandsHandler}
                  />
                ))}
            </div>
          </div>
          <div>
            <span className='flex font-semibold mt-2 mb-1'>카테고리</span>
            <div className='flex flex-row flex-wrap gap-2'>
              {categories &&
                categories.map((category, index) => (
                  <FilterButton
                    key={index}
                    index={index}
                    id={category.id}
                    name={category.name}
                    selected={category.selected}
                    handler={selectedCategoriesHandler}
                  />
                ))}
            </div>
          </div>
          <div>
            <span className='flex font-semibold mt-2 mb-1'>가격</span>
          </div>
          <Button size='sm' onClick={() => setOpenModal(false)}>
            적용
          </Button>
        </Modal.Body>
      </Modal>
    </div>
  );
}
