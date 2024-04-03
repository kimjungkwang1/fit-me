import { useEffect, useState } from 'react';
import SearchBar from '../components/Search/SearchBar';
import SearchResult from '../components/Search/SearchResult';
import { api } from '../services/api';
import SearchHistory from '../components/Search/SearchHistory';
import FilterBar from '../components/Common/FilterBar';
import { useInView } from 'react-intersection-observer';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../store/store';
import { updateList, resetList, checkWaitReset } from '../store/searchSlice';

export default function SearchPage() {
  const dispatch = useDispatch<AppDispatch>();
  const [ref, inView] = useInView();

  // 데이터 로딩중일 때 true로 바뀜
  const [loading, setLoading] = useState(false);
  const startLoading = () => {
    setLoading(true);
  };

  // 선택된 필터 목록 구성
  const keyword = useSelector((state: RootState) => state.search.keyword, shallowEqual);
  const selectedBrands = useSelector((state: RootState) => state.search.selectedBrands);
  const selectedCategories = useSelector((state: RootState) => state.search.selectedCategories);
  const selectedAges = useSelector((state: RootState) => state.search.selectedAges);
  const sortBy = useSelector((state: RootState) => state.search.sortBy);
  const list = useSelector((state: RootState) => state.search.list, shallowEqual);
  const lastId = useSelector((state: RootState) => state.search.lastId, shallowEqual);
  const lastPopularityScore = useSelector((state: RootState) => state.search.lastPopularityScore);
  const lastPrice = useSelector((state: RootState) => state.search.lastPrice);
  const waitReset = useSelector((state: RootState) => state.search.waitReset);

  // 검색 결과
  const infiniteScroll = () => {
    let params = {};

    if (lastId !== 0) {
      params = {
        ...params,
        lastId: lastId,
      };
    }

    if (sortBy === '' && lastId !== 0) {
      params = {
        ...params,
        lastPopularityScore: lastPopularityScore,
      };
    }

    if ((sortBy === 'priceAsc' || sortBy === 'priceDesc') && lastId !== 0) {
      params = {
        ...params,
        lastPrice: lastPrice,
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

    setLoading(true);
    api
      .get(`/api/products`, {
        params: params,
      })
      .then(({ data }) => {
        setLoading(false);
        if (data.length === 0) {
          return;
        }

        dispatch(updateList(data));
      })
      .catch((error) => {
        setLoading(false);
      });
  };

  // 무한 스크롤 요청
  useEffect(() => {
    // 키워드가 비어있지 않고, 바닥까지 내려왔을 때 무한 스크롤 요청
    if (keyword !== '' && inView) {
      infiniteScroll();
    }
  }, [inView]);

  // 검색 조건 변경
  useEffect(() => {
    const fetchData = () => {
      if (keyword !== '') {
        // 검색 결과 목록 초기화, 무한스크롤 인자 초기화
        dispatch(resetList());
      }
    };

    fetchData();
  }, [keyword, selectedBrands, selectedCategories, selectedAges, sortBy]);

  // waitReset == true일 때에는 아직 리셋이 진행중인 것
  // false로 바뀌면 데이터를 받아온다
  useEffect(() => {
    const fetchData = () => {
      if (waitReset) {
        infiniteScroll();
        dispatch(checkWaitReset());
      }
    };

    fetchData();
  }, [waitReset]);

  return (
    <>
      <SearchBar startLoading={startLoading} />
      <FilterBar />
      {list.length === 0 ? (
        keyword === '' ? (
          <SearchHistory />
        ) : loading ? (
          <div></div>
        ) : (
          <div className='flex py-14'>
            <span className='w-full text-center text-xl'>검색 결과가 없습니다.</span>
          </div>
        )
      ) : (
        <>
          <SearchResult list={list} />
          <div ref={ref} className='h-1' />
        </>
      )}
    </>
  );
}
