import { createSlice, PayloadAction, createAsyncThunk } from '@reduxjs/toolkit';
import { api } from '../services/api';
import { getCart } from './cartSlice';
import { RootState } from './store';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';

// 드레스룸 아이템 인터페이스
interface dressRoomItem {
  id: number;
  productId: number;
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
  result: fitting;
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
}

//초기상태
const initialState: DressroomState = {
  cartItems: [],
  orders: [],
  models: [],
  fittings: [],
  nowTop: { id: 0, url: '' },
  nowBottom: { id: 0, url: '' },
  nowModel: { id: 1, url: '' },
  result: { id: 0, url: 'https://fit-me.site/images/model/3.jpg' },
  status: 'idle',
};

// 드레스룸 카트 정보 가져오기
export const getCartForDressroom = createAsyncThunk(
  'dressroom/getCartForDressroom',
  async (_, { dispatch }) => {
    const result = await dispatch(getCart()).unwrap();
    const cartItems = result.map((item: any) => ({
      id: item.id,
      productId: item.productId,
      name: item.name,
      url: item.url,
      category: item.category,
    }));
    return cartItems;
  }
);

// 드레스룸 구매목록 가져오기
export const getOrders = createAsyncThunk('dressroom/getOrders', async () => {
  const response = await api.get<any>('/api/orders');
  const orders: dressRoomItem[] = [];
  response.data.forEach((order: any) => {
    order.orderProducts.forEach((item: any) => {
      const productInfo: dressRoomItem = {
        id: item.product.id,
        productId: item.product.id,
        name: item.product.name,
        url: item.product.url,
        category: item.product.categoryId,
      };
      orders.push(productInfo); // 새 배열에 상품 정보 추가
    });
  });
  return orders;
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
  if (fittings.length > 0) {
    const lastFittingId = fittings.length > 0 ? fittings[fittings.length - 1].id : undefined;

    const endpoint = lastFittingId
      ? `/api/dressroom/list?dressRoomId=${lastFittingId}`
      : '/api/dressroom/list';

    const response = await api.get<any>(endpoint);
    return response.data.map((item: any) => ({
      id: item.id,
      url: item.imageUrl,
    }));
  }
  return [];
});
export const getFittings2 = createAsyncThunk('dresroom/getFittings2', async (_, { getState }) => {
  const response = await api.get<any>('/api/dressroom/list');
  return response.data.map((item: any) => ({
    id: item.id,
    url: item.imageUrl,
  }));
});

//피팅 생성
export const makeFittings = createAsyncThunk('dresroom/makeFittings', async (_, { getState }) => {
  const MySwal = withReactContent(Swal);
  let swalLoading;
  MySwal.fire({
    title: 'AI가 이미지 생성중입니다.',
    html: '잠시만 기다려주세요',
    allowOutsideClick: false,
    didOpen: () => {
      Swal.showLoading();
    },
  });
  try {
    const state = getState() as RootState;
    const dressroom = state.dressroom;
    const ids = {
      modelId: dressroom.nowModel.id,
      productTopId: dressroom.nowTop.id,
      productBottomId: dressroom.nowBottom.id,
    };
    let endpoint = '/api/dressroom?modelId=' + dressroom.nowModel.id;
    if (dressroom.nowTop.id !== 0) {
      endpoint += '&productTopId=' + dressroom.nowTop.id;
    }
    if (dressroom.nowBottom.id !== 0) {
      endpoint += '&productBottomId=' + dressroom.nowBottom.id;
    }
    const response = await api.post<any>(endpoint);

    MySwal.close();
    return { id: response.data.id, url: response.data.imageUrl };
  } catch (error) {
    MySwal.close();
    return { id: 0, url: '' };
  }
});

//피팅 삭제
export const deleteFittings = createAsyncThunk('dresroom/deleteFittings', async (id: number) => {
  const response = await api.delete<any>(`/api/dressroom?dressRoomId=${id}`);
  return response;
});
//프로필 변경
export const changeProfile = createAsyncThunk('dresroom/changeProfile', async (url: string) => {
  const response = await api.post<any>('/api/dressroom', {
    data: {
      // nickname: id,
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
      state.nowModel = action.payload;
    },
    setResult: (state, action: PayloadAction<fitting>) => {
      state.result = action.payload;
    },
    deleteMyFiittings: (state, action: PayloadAction<number>) => {
      const id = action.payload;
      const itemIndex = state.fittings.findIndex((item) => item.id === id);
      if (itemIndex !== -1) {
        state.fittings.splice(itemIndex, 1);
      }
    },
  },
  extraReducers: (bulider) => {
    bulider
      .addCase(getCartForDressroom.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(getCartForDressroom.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.cartItems = action.payload;
      })
      .addCase(getCartForDressroom.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(getOrders.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(getOrders.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.orders = action.payload;
      })
      .addCase(getOrders.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(getModels.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(getModels.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.models = action.payload;
      })
      .addCase(getModels.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(makeFittings.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(makeFittings.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.result = action.payload;
      })
      .addCase(makeFittings.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(deleteFittings.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(deleteFittings.fulfilled, (state, action) => {
        state.status = 'succeeded';
      })
      .addCase(deleteFittings.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(getFittings.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(getFittings.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.fittings = [...state.fittings, ...action.payload];
      })
      .addCase(getFittings.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(getFittings2.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(getFittings2.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.fittings = action.payload;
      })
      .addCase(getFittings2.rejected, (state) => {
        state.status = 'failed';
      });
  },
});

export default dressroomSlice.reducer;
export const { setNowTop, setNowBottom, setModel, setResult, deleteMyFiittings } =
  dressroomSlice.actions;
