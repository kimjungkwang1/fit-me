import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { api } from '../../services/api';
import UserInput from '../../components/user/userInput';

type ApiDataType = {
  id: number;
  nickname: string;
  gender: boolean;
  profileUrl: string;
  phoneNumber: string;
  birthYear: number;
  address: string;
};

const Modify: React.FC = () => {
  const [apiData, setApiData] = useState<ApiDataType>();

  useEffect(() => {
    api
      .get('https://fit-me.site/api/members')
      .then((res) => {
        setApiData(res.data);
      })
      .catch((error) => {
        console.error('서버로부터 에러 응답:', error);
      });
  });

  const handleSubmit = async (apiData: ApiDataType) => {
    try {
      const response = await axios.post('https://fit-me.site/api/members////', apiData);
      console.log('서버 응답:', response.data);
    } catch (error) {
      console.error('서버로부터 에러 응답:', error);
    }
  };

  return <>{apiData && <UserInput onSubmit={handleSubmit} apiData={apiData} />}</>;
};

export default Modify;
