import React, { useState } from 'react';
import { api } from '../../services/api';
import UserInput from '../../components/user/userInput';
import { useNavigate } from 'react-router-dom';

type ApiDataType = {
  id: number;
  nickname: string;
  gender: boolean;
  profileUrl: string;
  phoneNumber: string;
  birthYear: number;
  address: string;
};

type InfoProps = {
  userInfo: ApiDataType;
};

const Modify: React.FC<InfoProps> = ({ userInfo }) => {
  const [apiData, setApiData] = useState<ApiDataType>(userInfo);
  const navigate = useNavigate();

  const handleSubmit = async (apiData: ApiDataType) => {
    try {
      api.post('/api/members', apiData).then(() => {
        alert('회원 정보 수정 완료');
        navigate(0);
      });
    } catch (error) {
      console.error('서버로부터 에러 응답:', error);
    }
  };

  return <>{apiData && <UserInput onSubmit={handleSubmit} apiData={apiData} />}</>;
};

export default Modify;
