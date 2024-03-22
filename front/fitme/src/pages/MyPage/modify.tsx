import React from 'react';
import axios from 'axios';
import UserInput from '../../components/user/userInput';

const Modify: React.FC = () => {
  const handleSubmit = async (formData: FormData) => {
    try {
      const response = await axios.post('여기에_서버_URL', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      console.log('서버 응답:', response.data);
    } catch (error) {
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
