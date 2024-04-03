import { Dropdown, Modal } from 'flowbite-react';
import { ChangeEvent, useEffect, useState } from 'react';
import FilterButton from './FilterButton';
import FilterSelected from './FilterSelected';
import { BiSearch } from 'react-icons/bi';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../store/store';
import {
  getBrands,
  toggleBrand,
  toggleCategory,
  toggleAge,
  setSortBy,
} from '../../store/searchSlice';

type OptionType = {
  id: number;
  name: string;
  selected: boolean;
};

export default function FilterBar() {
  const dispatch = useDispatch<AppDispatch>();

  const [openModal, setOpenModal] = useState(false);

  // 브랜드
  // 브랜드 목록 api호출
  useEffect(() => {
    dispatch(getBrands());
  }, []);
  const brands = useSelector((state: RootState) => state.search.brands);
  const selectedBrands = useSelector((state: RootState) => state.search.selectedBrands);
  const selectedBrandsHandler = (id: number) => {
    dispatch(toggleBrand(id));
  };
  // 브랜드 목록은 너무 많기 때문에 앞 10개만 보여줄 것
  const [showBrands, setShowBrands] = useState<OptionType[]>([...brands].slice(0, 10));
  const [searchBrand, setSearchBrand] = useState<string>('');
  const searchBrandHandler = (e: ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    setSearchBrand(input);
  };
  useEffect(() => {
    if (searchBrand === '') {
      setShowBrands([...brands].slice(0, 10));
    } else {
      const filteredBrands = brands.filter((brand) =>
        brand.name.toLowerCase().includes(searchBrand.toLowerCase())
      );
      setShowBrands(filteredBrands.slice(0, 10));
    }
  }, [brands, searchBrand]);

  // 카테고리
  const categories = useSelector((state: RootState) => state.search.categories);
  const selectedCategories = useSelector((state: RootState) => state.search.selectedCategories);
  const selectedCategoriesHandler = (id: number) => {
    dispatch(toggleCategory(id));
  };

  // 연령대
  const ages = useSelector((state: RootState) => state.search.ages);
  const selectedAges = useSelector((state: RootState) => state.search.selectedAges);
  const selectedAgesHandler = (id: number) => {
    dispatch(toggleAge(id));
  };

  // 정렬
  const sortBy = useSelector((state: RootState) => state.search.sortBy);

  return (
    <>
      <div className='flex flex-row gap-1 mx-[3%] my-2 justify-between items-baseline'>
        <div>
          <div
            className='mr-[3px] inline-block px-3 py-1 text-xs text-center border border-solid border-gray-400 rounded-lg'
            onClick={() => setOpenModal(true)}
          >
            {selectedBrands.length > 0
              ? selectedBrands.length === 1
                ? brands.find((brand) => brand.id === selectedBrands[0])?.name
                : `${selectedBrands.length}개 브랜드 필터`
              : `브랜드`}
          </div>
          <div
            className='mr-[3px] inline-block px-3 py-1 text-xs text-center border border-solid border-gray-400 rounded-lg'
            onClick={() => setOpenModal(true)}
          >
            {selectedCategories.length > 0
              ? selectedCategories.length === 1
                ? categories.find((category) => category.id === selectedCategories[0])?.name
                : `${selectedCategories.length}개 카테고리 필터`
              : `카테고리`}
          </div>
          <div
            className='mr-[3px] inline-block px-3 py-1 text-xs text-center border border-solid border-gray-400 rounded-lg'
            onClick={() => setOpenModal(true)}
          >
            {selectedAges.length > 0
              ? selectedAges.length === 1
                ? ages.find((age) => age.id === selectedAges[0])?.name
                : `${selectedAges.length}개 연령대 필터`
              : `연령대`}
          </div>
        </div>
        <Dropdown
          label=''
          placement='bottom'
          renderTrigger={() => (
            <span className='text-xs'>
              {sortBy === ''
                ? '인기순'
                : sortBy === 'latest'
                ? '최신순'
                : sortBy === 'priceAsc'
                ? '가격 낮은 순'
                : '가격 높은 순'}
            </span>
          )}
        >
          <Dropdown.Item className='text-xs' onClick={() => dispatch(setSortBy(''))}>
            인기순
          </Dropdown.Item>
          <Dropdown.Item className='text-xs' onClick={() => dispatch(setSortBy('latest'))}>
            최신순
          </Dropdown.Item>
          <Dropdown.Item className='text-xs' onClick={() => dispatch(setSortBy('priceAsc'))}>
            가격 낮은 순
          </Dropdown.Item>
          <Dropdown.Item className='text-xs' onClick={() => dispatch(setSortBy('priceDesc'))}>
            가격 높은 순
          </Dropdown.Item>
        </Dropdown>

        {/* 옵션 선택 모달 */}
        <Modal dismissible onClose={() => setOpenModal(false)} show={openModal}>
          <Modal.Body>
            {/* 선택된 옵션 리스트 */}
            <div>
              {selectedBrands.length !== 0 ||
              selectedCategories.length !== 0 ||
              selectedAges.length !== 0 ? (
                <div>
                  <span className='flex font-semibold mt-2 mb-2'>선택된 옵션</span>
                  <div className='flex flex-row flex-wrap gap-2 mb-2'>
                    {selectedBrands.map((selectedBrand, index) => (
                      <FilterSelected
                        key={index}
                        id={selectedBrand}
                        name={brands.find((brand) => brand.id === selectedBrand)!.name}
                        handler={selectedBrandsHandler}
                      />
                    ))}
                    {selectedCategories.map((selectedCategory, index) => (
                      <FilterSelected
                        key={index}
                        id={selectedCategory}
                        name={categories.find((category) => category.id === selectedCategory)!.name}
                        handler={selectedCategoriesHandler}
                      />
                    ))}
                    {selectedAges.map((selectedAge, index) => (
                      <FilterSelected
                        key={index}
                        id={selectedAge}
                        name={ages.find((age) => age.id === selectedAge)!.name}
                        handler={selectedAgesHandler}
                      />
                    ))}
                  </div>
                </div>
              ) : (
                <div />
              )}

              {/* 브랜드 선택 */}
              <span className='flex font-semibold mt-5 mb-2'>브랜드</span>
              <div className='bg-gray-200 rounded-lg mt-1 mb-2 px-2 py-1 text-sm flex flex-row items-center'>
                <BiSearch className='text-gray-500 text-lg mx-1' />
                <input
                  type='text'
                  placeholder='브랜드를 검색해보세요'
                  className='bg-gray-200 w-full focus: outline-none ml-1'
                  onChange={searchBrandHandler}
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
              <span className='flex font-semibold mt-5 mb-2'>카테고리</span>
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

            {/* 연령대 선택 */}
            <div>
              <span className='flex font-semibold mt-5 mb-2'>연령대</span>
              <div className='flex flex-row flex-wrap gap-2'>
                {ages &&
                  ages.map((age, index) => (
                    <FilterButton
                      key={index}
                      id={age.id}
                      name={age.name}
                      selected={age.selected}
                      handler={selectedAgesHandler}
                    />
                  ))}
              </div>
            </div>

            {/* 닫는 버튼 */}
            <div>
              <div
                className='bg-gray-200 rounded-xl w-full h-8 mt-5 text-center'
                onClick={() => setOpenModal(false)}
              >
                <span className='font-bold align-middle'>닫기</span>
              </div>
            </div>
          </Modal.Body>
        </Modal>
      </div>
    </>
  );
}
