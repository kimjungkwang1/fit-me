export const isAuthenticated = (): boolean => {
  const token = localStorage.getItem('accessToken');
  return !!token; // token이 있으면 true, 없으면 false 반환
};
