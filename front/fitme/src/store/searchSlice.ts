import { PayloadAction, createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { api } from '../services/api';

interface Image {
  id: number;
  url: string;
}

interface Option {
  id: number;
  name: string;
  selected: boolean;
}

interface Item {
  id: number;
  name: string;
  price: number;
  mainImages: Image[];
  brand: Option;
  popularityScore: number;
  likeCount: number;
  reviewRating: number;
  reviewCount: number;
}

interface SearchState {
  lastId: number;
  lastPopularityScore: number;
  lastPrice: number;
  keyword: string;
  brands: Option[];
  categories: Option[];
  ages: Option[];
  selectedBrands: number[];
  selectedCategories: number[];
  selectedAges: number[];
  sortBy: string;
  list: Item[];
  params: {};
  waitReset: boolean;
}

// initial state
const initialState: SearchState = {
  lastId: 0,
  lastPopularityScore: 0,
  lastPrice: 0,
  keyword: '',
  brands: [],
  categories: [
    {
      id: 1005,
      name: '맨투맨/스웨트셔츠',
      selected: false,
    },
    {
      id: 1006,
      name: '니트/스웨터',
      selected: false,
    },
    {
      id: 1010,
      name: '셔츠/블라우스',
      selected: false,
    },
    {
      id: 1001,
      name: '반소매 티셔츠',
      selected: false,
    },
    {
      id: 1004,
      name: '후드 티셔츠',
      selected: false,
    },
    {
      id: 1003,
      name: '피케/카라 티셔츠',
      selected: false,
    },
    {
      id: 1008,
      name: '기타상의',
      selected: false,
    },
    {
      id: 1013,
      name: '스포츠상의',
      selected: false,
    },
    {
      id: 1011,
      name: '민소매상의',
      selected: false,
    },
    {
      id: 3002,
      name: '데님 팬츠',
      selected: false,
    },
    {
      id: 3007,
      name: '코튼 팬츠',
      selected: false,
    },
    {
      id: 3004,
      name: '트레이닝/조거팬츠',
      selected: false,
    },
    {
      id: 3008,
      name: '슈트팬츠/슬랙스',
      selected: false,
    },
    {
      id: 3006,
      name: '기타바지',
      selected: false,
    },
    {
      id: 3011,
      name: '스포츠하의',
      selected: false,
    },
    {
      id: 3009,
      name: '숏팬츠',
      selected: false,
    },
    {
      id: 3005,
      name: '레깅스',
      selected: false,
    },
  ],
  ages: [
    { id: 10, name: '10대', selected: false },
    { id: 20, name: '20대', selected: false },
    { id: 30, name: '30대', selected: false },
    { id: 40, name: '40대', selected: false },
  ],
  selectedBrands: [],
  selectedCategories: [],
  selectedAges: [],
  sortBy: '',
  list: [],
  params: {},
  waitReset: false,
}; // initial state (finished)

// 브랜드 목록 정보 받아오기
export const getBrands = createAsyncThunk('search/getBrands', async () => {
  const response = await api.get<Option[]>('/api/brands');
  return response.data.map((brand: Option) => ({
    id: brand.id,
    name: brand.name,
    selected: false,
  }));
});

export const searchSlice = createSlice({
  name: 'search',
  initialState,
  reducers: {
    // 초기화
    resetState: (state) => {
      state.lastId = 0;
      state.lastPopularityScore = 0;
      state.lastPrice = 0;
      state.keyword = '';
      state.brands = [];
      state.categories = [
        {
          id: 1005,
          name: '맨투맨/스웨트셔츠',
          selected: false,
        },
        {
          id: 1006,
          name: '니트/스웨터',
          selected: false,
        },
        {
          id: 1010,
          name: '셔츠/블라우스',
          selected: false,
        },
        {
          id: 1001,
          name: '반소매 티셔츠',
          selected: false,
        },
        {
          id: 1004,
          name: '후드 티셔츠',
          selected: false,
        },
        {
          id: 1003,
          name: '피케/카라 티셔츠',
          selected: false,
        },
        {
          id: 1008,
          name: '기타상의',
          selected: false,
        },
        {
          id: 1013,
          name: '스포츠상의',
          selected: false,
        },
        {
          id: 1011,
          name: '민소매상의',
          selected: false,
        },
        {
          id: 3002,
          name: '데님 팬츠',
          selected: false,
        },
        {
          id: 3007,
          name: '코튼 팬츠',
          selected: false,
        },
        {
          id: 3004,
          name: '트레이닝/조거팬츠',
          selected: false,
        },
        {
          id: 3008,
          name: '슈트팬츠/슬랙스',
          selected: false,
        },
        {
          id: 3006,
          name: '기타바지',
          selected: false,
        },
        {
          id: 3011,
          name: '스포츠하의',
          selected: false,
        },
        {
          id: 3009,
          name: '숏팬츠',
          selected: false,
        },
        {
          id: 3005,
          name: '레깅스',
          selected: false,
        },
      ];
      state.ages = [
        { id: 10, name: '10대', selected: false },
        { id: 20, name: '20대', selected: false },
        { id: 30, name: '30대', selected: false },
        { id: 40, name: '40대', selected: false },
      ];
      state.selectedBrands = [];
      state.selectedCategories = [];
      state.selectedAges = [];
      state.sortBy = '';
      state.list = [];
      state.params = {};
      state.waitReset = false;
    },

    // 검색어 저장
    setKeyword: (state, action: PayloadAction<string>) => {
      state.keyword = action.payload;
    },

    // 브랜드 선택
    toggleBrand: (state, action: PayloadAction<number>) => {
      const index = state.brands.findIndex((brand) => brand.id === action.payload);
      if (index !== -1) {
        state.brands[index].selected = !state.brands[index].selected;
      }

      const selIndex = state.selectedBrands.findIndex((id) => id === action.payload);
      if (selIndex !== -1) {
        state.selectedBrands.splice(selIndex, 1);
      } else {
        state.selectedBrands.push(action.payload);
      }
    },

    // 카테고리 선택
    toggleCategory: (state, action: PayloadAction<number>) => {
      const index = state.categories.findIndex((category) => category.id === action.payload);
      if (index !== -1) {
        state.categories[index].selected = !state.categories[index].selected;
      }

      const selIndex = state.selectedCategories.findIndex((id) => id === action.payload);
      if (selIndex !== -1) {
        state.selectedCategories.splice(selIndex, 1);
      } else {
        state.selectedCategories.push(action.payload);
      }
    },

    // 연령대 선택
    toggleAge: (state, action: PayloadAction<number>) => {
      const index = state.ages.findIndex((age) => age.id === action.payload);
      if (index !== -1) {
        state.ages[index].selected = !state.ages[index].selected;
      }

      const selIndex = state.selectedAges.findIndex((id) => id === action.payload);
      if (selIndex !== -1) {
        state.selectedAges.splice(selIndex, 1);
      } else {
        state.selectedAges.push(action.payload);
      }
    },

    // 정렬 기준 선택
    setSortBy: (state, action: PayloadAction<string>) => {
      state.sortBy = action.payload;
    },

    // 검색 결과 저장
    updateList: (state, action: PayloadAction<Item[]>) => {
      const newList = action.payload;
      state.list = [...state.list, ...newList];
      state.lastId = newList[newList.length - 1].id;
      if (state.sortBy === '') {
        // 인기순
        state.lastPopularityScore = newList[newList.length - 1].popularityScore;
      } else if (state.sortBy === 'priceAsc' || state.sortBy === 'priceDesc') {
        // 가격순
        state.lastPrice = newList[newList.length - 1].price;
      }
    },

    resetList: (state) => {
      state.list = [];
      state.lastId = 0;
      state.lastPopularityScore = 0;
      state.lastPrice = 0;
      state.waitReset = true;
    },

    checkWaitReset: (state) => {
      state.waitReset = false;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(getBrands.fulfilled, (state, action) => {
      if (state.brands.length === 0) {
        state.brands = action.payload;
      }
    });
  },
});

export default searchSlice.reducer;
export const {
  resetState,
  setKeyword,
  toggleBrand,
  toggleCategory,
  toggleAge,
  setSortBy,
  updateList,
  resetList,
  checkWaitReset,
} = searchSlice.actions;
