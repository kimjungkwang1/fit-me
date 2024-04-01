import { useEffect, useState } from 'react';
import SearchBar from '../components/Search/SearchBar';
import SearchResult from '../components/Search/SearchResult';
import { api } from '../services/api';
import SearchHistory from '../components/Search/SearchHistory';
import FilterBar from '../components/Common/FilterBar';
import { useInView } from 'react-intersection-observer';

type ImageType = {
  id: number;
  url: string;
};

type BrandType = {
  id: number;
  name: string;
};

type ItemType = {
  id: number;
  name: string;
  price: number;
  mainImages: ImageType[];
  brand: BrandType;
  populartiyScore: number;
  likeCount: number;
  reviewRating: number;
  reviewCount: number;
};

type OptionType = {
  id: number;
  name: string;
  selected: boolean;
};

export default function SearchPage() {
  const [ref, inView] = useInView();
  const [lastId, setLastId] = useState<number>(0);
  const [lastPopularityScore, setLastPopularityScore] = useState<number>(0);

  const [keyword, setKeyword] = useState<string>('');
  const searchKeyword = (k: string) => {
    setKeyword(k);
  };

  // 브랜드 목록 구성
  const [brands, setBrands] = useState<OptionType[]>([]);
  useEffect(() => {
    api.get(`/api/brands`).then(({ data }) => {
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

  // 연령 목록 구성
  const [ages, setAges] = useState<OptionType[]>([]);
  useEffect(() => {
    setAges([
      { id: 10, name: '10대', selected: false },
      { id: 20, name: '20대', selected: false },
      { id: 30, name: '30대', selected: false },
      { id: 40, name: '40대', selected: false },
    ]);
  }, []);

  // 선택된 브랜드 목록 구성
  const [selectedBrands, setSelectedBrands] = useState<number[]>([]);
  const selectedBrandsHandler = (id: number) => {
    if (selectedBrands) {
      const selectedIndex = selectedBrands.indexOf(id);

      if (selectedIndex !== -1) {
        // 요소를 제거
        const updatedSelected = [...selectedBrands];
        updatedSelected.splice(selectedIndex, 1);
        setSelectedBrands(updatedSelected);
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

  // 선택된 연령 목록 구성
  const [selectedAges, setSelectedAges] = useState<number[]>([]);
  const selectedAgesHandler = (id: number) => {
    if (selectedAges) {
      const selectedIndex = selectedAges.indexOf(id);

      if (selectedIndex !== -1) {
        // 요소를 제거
        const updatedSelected = [...selectedAges];
        updatedSelected.splice(selectedIndex, 1);
        setSelectedAges(updatedSelected);
      } else {
        // 요소를 추가
        setSelectedAges([...selectedAges, id]);
      }
    }

    if (ages) {
      const selectedIndex = ages.findIndex((age) => age.id === id);
      const updated = [...ages];
      const temp = ages[selectedIndex];
      temp.selected = !temp.selected;
      updated.splice(selectedIndex, 1, temp);
      setAges(updated);
    }
  };

  // 가격
  const [minPrice, setMinPrice] = useState<number>(0);
  const [maxPrice, setMaxPrice] = useState<number>(1000000);
  const minPriceHandler = (min: number) => {
    setMinPrice(min);
  };
  const maxPriceHandler = (max: number) => {
    setMaxPrice(max);
  };

  // 정렬 기준
  const [sortBy, setSortBy] = useState<string>('');
  const sortByHandler = (sort: string) => {
    setSortBy(sort);
  };

  // 검색 결과
  const [list, setList] = useState<ItemType[]>([]);
  let params = {};
  const infiniteScroll = () => {
    if (lastId !== 0) {
      params = {
        ...params,
        lastId: lastId,
        lastPopularityScore: lastPopularityScore,
      };
    }

    if (keyword !== '') {
      params = {
        ...params,
        keyword: keyword,
      };
    }

    if (selectedBrands.length > 0) {
      params = {
        ...params,
        brandIds: selectedBrands.join(','),
      };
    }

    if (selectedCategories.length > 0) {
      params = {
        ...params,
        categoryIds: selectedCategories.join(','),
      };
    }

    if (selectedAges.length > 0) {
      params = {
        ...params,
        ageRanges: selectedAges.map((age) => `${age}s`).join(','),
      };
    }

    if (sortBy !== '') {
      params = {
        ...params,
        sortBy: sortBy,
      };
    }

    api
      .get(`/api/products`, {
        params: params,
      })
      .then(({ data }) => {
        if (data.length === 0) {
          return;
        }

        setList([...list, ...data]);
        setLastId(data[data.length - 1].id);
        setLastPopularityScore(data[data.length - 1].popularityScore);
      });
  };

  // 무한 스크롤 요청
  useEffect(() => {
    // 키워드가 비어있지 않고, 바닥까지 내려왔을 때 무한 스크롤 요청
    if (keyword !== '' && inView) {
      console.log(inView, '무한 스크롤 요청', lastId, lastPopularityScore);
      infiniteScroll();
    }
  }, [inView]);

  const resetSearch = () => {
    setList([]);
    setLastId(0);
    setLastPopularityScore(0);
  };

  // 검색 조건 변경
  useEffect(() => {
    const fetchData = async () => {
      if (keyword !== '') {
        // 검색 결과 목록 초기화, 무한스크롤 인자 초기화
        resetSearch();
        // 초기화 후 api 호출
        if (list.length === 0) {
          await infiniteScroll();
        }
      }
    };

    fetchData();
  }, [keyword, selectedBrands, selectedCategories, selectedAges, sortBy]);

  return (
    <>
      <SearchBar keyword={keyword} searchKeyword={searchKeyword} />
      <FilterBar
        brands={brands}
        selectedBrands={selectedBrands}
        selectedBrandsHandler={selectedBrandsHandler}
        categories={categories}
        selectedCategories={selectedCategories}
        selectedCategoriesHandler={selectedCategoriesHandler}
        ages={ages}
        selectedAges={selectedAges}
        selectedAgesHandler={selectedAgesHandler}
        minPrice={minPrice}
        maxPrice={maxPrice}
        minPriceHandler={minPriceHandler}
        maxPriceHandler={maxPriceHandler}
        sortBy={sortBy}
        sortByHandler={sortByHandler}
      />
      {keyword === '' ? (
        <SearchHistory searchKeyword={searchKeyword} />
      ) : (
        <>
          <SearchResult list={list} />
          <div ref={ref} className='h-1' />
        </>
      )}
    </>
  );
}
