import React from 'react';
import axios from 'axios';
import UserInput from '../../components/user/userInput';

const Modify: React.FC = () => {
  const handleSubmit = async (formData: FormData) => {
    try {
      // 서버로 formData를 POST 요청
      const response = await axios.post('여기에_서버_URL', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      // 요청 성공 시 로직 처리
      console.log('서버 응답:', response.data);
    } catch (error) {
      // 오류 처리
      console.error('서버로부터 에러 응답:', error);
    }
  };
  return (
    <>
      <UserInput onSubmit={handleSubmit} />
    </>
  );
};

export default Modify;
