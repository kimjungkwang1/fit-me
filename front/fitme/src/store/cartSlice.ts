import { createSlice, PayloadAction, createAsyncThunk } from '@reduxjs/toolkit';
import { api } from '../services/api ';
//장바구니 아이템
interface CartItem {
  id: number;
  productId: number;
  name: string;
  price: number;
  color: string;
  size: string;
  url: string;
  quantity: number;
  stockQuantity: number;
  isChecked: boolean;
  category: number;
}
//장바구니 상태
interface CartState {
  items: CartItem[];
  totalProductCount: number;
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  address: address;
  totalPrice: number;
}
interface address {
  name: String;
  phoneNumber: String;
  address: string;
}
interface option {
  proudctOptionId: number;
  productSizeId: number;
  quantity: number;
}
//초기상태
const initialState: CartState = {
  items: [],
  totalProductCount: 0,
  status: 'idle',
  totalPrice: 0,
  address: {
    name: 'String',
    phoneNumber: 'String',
    address: 'string',
  },
};

// 카트 정보 가져오기
export const getCart = createAsyncThunk('cart/getCart', async () => {
  const response = await api.get<any>('/api/cart');
  return response.data.map((item: any) => ({
    id: item.id,
    productId: item.product.id,
    name: item.product.name,
    price: item.productTotalPrice,
    color: item.productOption.color,
    size: item.productOption.size,
    url: item.product.mainImages[0].url,
    quantity: item.product.productOption.quantity,
    stockQuantity: item.product.productOption.stockQuantity,
    isChecked: true,
    category: item.product.categoryId,
  }));
});
// 카트에 아이템 추가
export const addCartItem = createAsyncThunk(
  'cart/addCartItem',
  async ({ id, options }: { id: number; options: option[] }) => {
    const response = await api.post<any>('/api/cart/' + id, options);
  }
);
// 카트에서 아이템 삭제
export const deleteCartItem = createAsyncThunk('cart/deleteCartItem', async (ids: number[]) => {
  const response = await api.delete<any>('/api/cart', { data: ids });
});
// 카트에서 수량 수정
export const updateQuantity = createAsyncThunk(
  'cart/deleteCartItem',
  async ({ id, quantity }: { id: number; quantity: number }) => {
    const response = await api.patch<any>('/api/cart/products/quantity', {
      data: {
        id,
        quantity,
      },
    });
  }
);

export const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    // 체크 변경
    toggleItemChecked: (state, action: PayloadAction<number>) => {
      const index = state.items.findIndex((item) => item.id === action.payload);
      if (index !== -1) {
        state.items[index].isChecked = !state.items[index].isChecked;
      }
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(getCart.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(getCart.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.items = action.payload;
        state.totalProductCount = action.payload.length;
        state.totalPrice = action.payload.reduce(
          (total: number, item: CartItem) => total + item.price,
          0
        );
      })
      .addCase(getCart.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(addCartItem.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(addCartItem.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(deleteCartItem.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(deleteCartItem.rejected, (state) => {
        state.status = 'failed';
      });
  },
});
export default cartSlice.reducer;
export const { toggleItemChecked } = cartSlice.actions;
