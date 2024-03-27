import { Button, Modal } from 'flowbite-react';
import { ChangeEvent, useEffect, useState } from 'react';
import FilterButton from './FilterButton';
import FilterSelected from './FilterSelected';
import { BiSearch } from 'react-icons/bi';

type OptionType = {
  id: number;
  name: string;
  selected: boolean;
};

type FilterBarProps = {
  brands: OptionType[];
  selectedBrands: number[];
  selectedBrandsHandler: (id: number) => void;

  categories: OptionType[];
  selectedCategories: number[];
  selectedCategoriesHandler: (id: number) => void;
};

export default function FilterBar({
  brands,
  selectedBrands,
  selectedBrandsHandler,
  categories,
  selectedCategories,
  selectedCategoriesHandler,
}: FilterBarProps) {
  const [openModal, setOpenModal] = useState(false);

  // 보여줄 브랜드 목록 구성
  const [showBrands, setShowBrands] = useState<OptionType[]>([]);
  const [searchKeyword, setSearchKeyword] = useState<string>('');
  const searchKeywordHandler = (e: ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    setSearchKeyword(input);
  };
  useEffect(() => {
    if (searchKeyword === '') {
      setShowBrands([...brands].slice(0, 10));
    } else {
      const filteredBrands = brands.filter((brand) =>
        brand.name.toLowerCase().includes(searchKeyword.toLowerCase())
      );
      setShowBrands(filteredBrands.slice(0, 10));
    }
  }, [brands, searchKeyword]);

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

      {/* 옵션 선택 모달 */}
      <Modal show={openModal} popup>
        <Modal.Body>
          {/* 선택된 옵션 리스트 */}
          <div>
            {selectedBrands.length !== 0 || selectedCategories.length !== 0 ? (
              <>
                <span className='flex font-semibold mt-2 mb-1'>선택된 옵션</span>
                <div className='flex flex-row flex-wrap gap-2 mb-2'>
                  {selectedBrands.map((selectedBrand, index) => (
                    <FilterSelected
                      key={index}
                      id={selectedBrand}
                      name={brands.find((brand) => brand.id === selectedBrand)!.name}
                      handler={selectedBrandsHandler}
                    />
                  ))}
                </div>
                <div className='flex flex-row flex-wrap gap-2'>
                  {selectedCategories.map((selectedCategory, index) => (
                    <FilterSelected
                      key={index}
                      id={selectedCategory}
                      name={categories.find((category) => category.id === selectedCategory)!.name}
                      handler={selectedCategoriesHandler}
                    />
                  ))}
                </div>
              </>
            ) : (
              <div />
            )}

            {/* 브랜드 선택 */}
            <span className='flex font-semibold mt-2 mb-1'>브랜드</span>
            <div className='bg-gray-200 rounded-lg my-2 px-2 py-1 text-sm flex flex-row items-center'>
              <BiSearch className='text-gray-500 text-lg mx-1' />
              <input
                type='text'
                placeholder='브랜드를 검색해보세요'
                className='bg-gray-200 w-full focus: outline-none ml-1'
                onChange={searchKeywordHandler}
              />
            </div>
            <div className='flex flex-row flex-wrap gap-2'>
              {showBrands &&
                showBrands.map((brand, index) => (
                  <FilterButton
                    key={index}
                    id={brand.id}
                    name={brand.name}
                    selected={brand.selected}
                    handler={selectedBrandsHandler}
                  />
                ))}
            </div>
          </div>

          {/* 카테고리 선택 */}
          <div>
            <span className='flex font-semibold mt-2 mb-1'>카테고리</span>
            <div className='flex flex-row flex-wrap gap-2'>
              {categories &&
                categories.map((category, index) => (
                  <FilterButton
                    key={index}
                    id={category.id}
                    name={category.name}
                    selected={category.selected}
                    handler={selectedCategoriesHandler}
                  />
                ))}
            </div>
          </div>

          {/* 가격 double range slider */}
          <div>
            <span className='flex font-semibold mt-2 mb-1'>가격</span>
            <div></div>
          </div>
          <Button size='sm' onClick={() => setOpenModal(false)}>
            적용
          </Button>
        </Modal.Body>
      </Modal>
    </div>
  );
}
