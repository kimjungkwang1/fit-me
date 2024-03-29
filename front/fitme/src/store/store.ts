import { configureStore } from '@reduxjs/toolkit';
import dressroomReducer from './dressroomSlice';
import cartReducer from './cartSlice';

export const store = configureStore({
  reducer: {
    dressroom: dressroomReducer,
    cart: cartReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredPaths: ['payload.headers'],
      },
    }),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export default store;
