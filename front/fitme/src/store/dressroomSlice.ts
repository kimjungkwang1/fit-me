import { createSlice, PayloadAction, createAsyncThunk } from '@reduxjs/toolkit';
import { api } from '../services/api';
import { getCart } from './cartSlice';
import { RootState } from './store';

// 드레스룸 아이템 인터페이스
interface dressRoomItem {
  id: number;
  name: string;
  url: string;
  category: number;
}
// 드레스룸 모데 인터페이스
interface dressRoomModel {
  id: number;
  url: string;
}

interface fitting {
  id: number;
  url: string;
}

//드레스룸 상태 인터페이스
interface DressroomState {
  cartItems: dressRoomItem[];
  orders: dressRoomItem[];
  models: dressRoomModel[];
  fittings: fitting[];
  nowTop: fitting;
  nowBottom: fitting;
  nowModel: dressRoomModel;
  result: String;
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
}

//초기상태
const initialState: DressroomState = {
  cartItems: [],
  orders: [],
  models: [],
  fittings: [],
  nowTop: { id: 0, url: 'https://fit-me.site/images/products/15298/main/mainimage_15298_2.jpg' },
  nowBottom: { id: 0, url: 'https://fit-me.site/images/products/15299/main/mainimage_15299_6.jpg' },
  nowModel: { id: 0, url: '' },
  result: '',
  status: 'idle',
};

// 드레스룸 카트 정보 가져오기
export const getCartForDressroom = createAsyncThunk(
  'dressroom/getCartForDressroom',
  async (_, { dispatch }) => {
    const result = await dispatch(getCart()).unwrap();
    const cartItems = result.map((item: any) => ({
      id: item.id,
      name: item.name,
      url: item.url,
      category: item.category,
    }));
    return cartItems;
  }
);

// 드레스룸 구매목록 가져오기
// todo api 완성되면 바꾸기
export const getOrders = createAsyncThunk('dressroom/getOrders', async () => {
  const response = await api.get<any>('/api/orders');
  return response.data.map((item: any) => ({
    id: item.id,
    name: item.product.name,
    url: item.product.mainImages[0].url,
    category: item.product.mainImages.categoryId,
  }));
});

//모델 가져오기
export const getModels = createAsyncThunk('dressroom/getModels', async () => {
  const response = await api.get<any>('/api/dressroom/models');
  return response.data.map((item: any) => ({
    id: item.id,
    url: item.url,
  }));
});

//피팅 목록 가져오기
export const getFittings = createAsyncThunk('dresroom/getFittings', async (_, { getState }) => {
  const state = getState() as RootState;
  const fittings = state.dressroom.fittings;
  const lastFittingId = fittings.length > 0 ? fittings[fittings.length - 1].id : undefined;

  const endpoint = lastFittingId ? `/api/fittings?dressRoomId=${lastFittingId}` : '/api/fittings';

  const response = await api.get<any>(endpoint);
  return response.data.map((item: any) => ({
    id: item.id,
    url: item.imageUrl,
  }));
});

//피팅 생성
export const makeFittings = createAsyncThunk('dresroom/getFittings', async (_, { getState }) => {
  const state = getState() as RootState;
  const dressroom = state.dressroom;
  const ids = {
    modelId: dressroom.nowModel.id,
    productTopId: dressroom.nowTop.id,
    productBottomId: dressroom.nowBottom.id,
  };
  const response = await api.post<any>('/api/dressroom', ids);
  return response;
});

//피팅 삭제

export const deleteFittings = createAsyncThunk('dresroom/getFittings', async (id) => {
  const response = await api.post<any>('/api/dressroom', {
    data: {
      dressRoomId: id,
    },
  });
  return response;
});

const dressroomSlice = createSlice({
  name: 'dressroom',
  initialState,
  reducers: {
    // 아이템 클릭하면 나우피팅 상의 하의 바꾸기
    setNowTop: (state, action: PayloadAction<fitting>) => {
      state.nowTop = action.payload;
    },
    setNowBottom: (state, action: PayloadAction<fitting>) => {
      state.nowBottom = action.payload;
    },
    setModel: (state, action: PayloadAction<fitting>) => {
      state.nowBottom = action.payload;
    },
  },
});

export default dressroomSlice.reducer;
