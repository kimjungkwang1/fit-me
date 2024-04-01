import React, { useEffect, useState } from 'react';
import { api } from '../services/api';
import UserInput from '../components/user/userInput';
import { useLocation, useNavigate } from 'react-router-dom';

type ApiDataType = {
  id: number;
  nickname: string;
  gender: boolean;
  profileUrl: string;
  phoneNumber: string;
  birthYear: number;
  address: string;
};

const Signup: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [apiData, setApiData] = useState<ApiDataType | null>(null);

  useEffect(() => {
    const state = location.state as { userData: ApiDataType } | undefined;
    if (state?.userData) {
      setApiData(state.userData);
    } else {
      // userData가 없는 경우 홈으로 리다이렉트
      navigate('/');
    }
  }, [location, navigate]);

  const handleSubmit = async (apiData: ApiDataType) => {
    try {
      api.post('/api/members', apiData).then(() => {
        alert('회원 가입 완료');
        navigate('/');
      });
    } catch (error) {
      console.error('서버로부터 에러 응답:', error);
    }
  };

  return <>{apiData && <UserInput onSubmit={handleSubmit} apiData={apiData} />}</>;
};

export default Signup;
