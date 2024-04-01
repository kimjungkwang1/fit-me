import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

type ApiDataType = {
  id: number;
  nickname: string;
  gender: boolean;
  profileUrl: string;
  phoneNumber: string;
  birthYear: number;
  address: string;
};

type UserInputProps = {
  onSubmit: (apiData: ApiDataType) => void;
  apiData: ApiDataType;
};

const UserInput: React.FC<UserInputProps> = ({ onSubmit, apiData }) => {
  const thisYear = new Date().getFullYear().toString();
  const location = useLocation();
  const id = apiData.id;
  const [nickname, setNickname] = useState(apiData.nickname);
  const [gender, setGender] = useState(apiData.gender);
  const profileUrl = apiData.profileUrl;
  const [phoneNumber, setPhoneNumber] = useState(apiData.phoneNumber);
  const [year, setYear] = useState(apiData.birthYear ? apiData.birthYear.toString() : thisYear);
  const [address, setAddress] = useState(apiData.address);
  const [roadAddress, setRoadAddress] = useState('');
  const [detailAddress, setDetailAddress] = useState('');

  useEffect(() => {
    if (apiData.address) {
      const parts = apiData.address.split(',');
      setRoadAddress(parts[0].trim());
      setDetailAddress(parts[1]?.trim() || '');
    }
  }, [apiData.address]);

  const handleSubmit = () => {
    if (
      id == null ||
      nickname == null ||
      nickname === '' ||
      gender == null ||
      phoneNumber == null ||
      phoneNumber === '' ||
      year == null ||
      year === '' ||
      address == null ||
      address === ''
    ) {
      alert('모든 항목을 채워주세요.');
    } else if (phoneNumber.length < 10) {
      alert('전화번호를 확인해주세요.');
    } else {
      const updatedApiData: ApiDataType = {
        id: id ?? 0,
        nickname,
        gender,
        profileUrl,
        phoneNumber,
        birthYear: Number(year),
        address,
      };

      // 생성된 객체를 onSubmit 함수에 넘겨줍니다.
      onSubmit(updatedApiData);
    }
  };

  return (
    <>
      <div className='flex flex-col items-center'>
        <div className='w-2/3'>
          <label className='block mt-3 text-lg text-black'>닉네임</label>
          <input
            type='text'
            placeholder='닉네임'
            value={nickname}
            onChange={(event) => setNickname(event.target.value)}
            className='block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-black bg-white px-5 py-2.5 text-gray-700 focus:border-black focus:outline-none focus:ring focus:ring-red-300 focus:ring-opacity-40 dark:border-black dark:bg-darkgray dark:text-gray-300 dark:focus:border-red-300'
          />
        </div>
        <div className='w-2/3'>
          <label className='block mt-3 text-lg text-black'>성별</label>
          <div className='mt-2 inline-flex w-full rounded-md shadow-sm' role='group'>
            <button
              type='button'
              onClick={() => setGender(false)}
              // className='inline-flex px-5 py-2.5 items-center w-1/2 bg-white px-4 py-2 text-sm font-medium text-darkgray bg-transparent border border-black rounded-s-lg hover:bg-gray-600 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-darkgray focus:text-white dark:border-white dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:bg-gray-700'
              className={`inline-flex px-5 py-2.5 items-center w-1/2 px-4 py-2 text-sm font-medium border border-black rounded-s-lg ${
                gender === false
                  ? 'text-white bg-darkgray'
                  : 'text-darkgray bg-white hover:bg-bluegray'
              }`}
            >
              <svg
                fill={`${gender === false ? '#ffffff' : '#000000'}`}
                width='20px'
                height='20px'
                viewBox='0 0 1024 1024'
                xmlns='http://www.w3.org/2000/svg'
                transform='rotate(0)'
              >
                <g id='SVGRepo_bgCarrier' strokeWidth='0'></g>
                <g id='SVGRepo_tracerCarrier' strokeLinecap='round' strokeLinejoin='round'></g>
                <g id='SVGRepo_iconCarrier'>
                  <path d='M1023.3 22.656c.144-6.48-1.378-12.29-5.586-16.433a22.058 22.058 0 0 0-16.4-6.527l-11.696.273c-.223 0-.383.08-.64.112L695.476-.944c-12.928.289-23.616 10.993-23.92 23.92l-.032 16.432c1.967 15.248 13.952 24.16 26.88 23.872l215.215.432-256.144 254.592c-69.488-58.24-159.008-93.36-256.768-93.36-220.928 0-400 179.071-400 400 0 220.911 179.072 400 400 400 220.912 0 400-179.089 400-400 0-100.113-36.864-191.569-97.664-261.713L959.938 107.92l-.944 219.152c-.304 12.928 9.952 24.176 22.897 23.888l16.416-.032c12.96-.304 23.647-8 23.92-20.928l.671-295.008c0-.24-.88-.4-.88-.624zM737.229 624.943c0 185.856-150.672 336.528-336.544 336.528-185.856 0-336.528-150.672-336.528-336.528 0-185.856 150.672-336.528 336.528-336.528 185.872-.016 336.544 150.656 336.544 336.528z'></path>
                </g>
              </svg>
              <div className='mx-auto text-lg'>남자</div>
            </button>
            <button
              type='button'
              onClick={() => setGender(true)}
              // className='inline-flex px-5 py-2.5 items-center w-1/2 bg-white px-4 py-2 text-sm font-medium text-darkgray bg-transparent border border-black rounded-e-lg hover:bg-gray-600 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-darkgray focus:text-white dark:border-white dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:bg-gray-700'
              className={`inline-flex px-5 py-2.5 items-center w-1/2 px-4 py-2 text-sm font-medium border border-black rounded-e-lg ${
                gender === true
                  ? 'text-white bg-darkgray'
                  : 'text-darkgray bg-white hover:bg-bluegray'
              }`}
            >
              <svg
                height='20px'
                width='21px'
                version='1.1'
                id='Capa_1'
                xmlns='http://www.w3.org/2000/svg'
                viewBox='0 0 167.579 167.579'
                fill={`${gender === true ? '#ffffff' : '#000000'}`}
                transform='rotate(180)'
              >
                <g id='SVGRepo_bgCarrier' strokeWidth='0'></g>
                <g id='SVGRepo_tracerCarrier' strokeLinecap='round' strokeLinejoin='round'></g>
                <g id='SVGRepo_iconCarrier'>
                  <path d='M147.917,25.461l18.46-18.467c1.603-1.6,1.603-4.194,0-5.794s-4.187-1.6-5.791,0l-18.46,18.467 L123.656,1.2c-1.603-1.6-4.191-1.6-5.794,0s-1.603,4.194,0,5.794l18.456,18.467l-17.909,17.916 C90.494,18.493,47.576,19.362,20.81,46.129c-27.747,27.743-27.747,72.894,0,100.637c27.747,27.75,72.89,27.75,100.637,0 c26.766-26.763,27.632-69.68,2.756-97.592l17.923-17.916l18.46,18.467c1.603,1.6,4.187,1.6,5.791,0 c1.603-1.603,1.603-4.191,0-5.794L147.917,25.461z M115.696,141.011c-24.569,24.565-64.559,24.565-89.131,0 c-24.576-24.572-24.576-64.562,0-89.138s64.562-24.576,89.131,0C140.276,76.452,140.276,116.439,115.696,141.011z'></path>{' '}
                </g>
              </svg>
              <div className='mx-auto text-lg'>여자</div>
            </button>
          </div>
        </div>

        <div className='w-2/3'>
          <label className='block mt-3 text-lg text-black'>출생연도</label>
          <input
            type='text'
            value={year}
            onChange={(event) => {
              const value = event.target.value;
              // 입력값이 숫자인지 판별
              const isNumeric = /^[0-9]+$/.test(value);
              // 숫자라면 값을 설정, 숫자가 아니라면 null로 설정
              if (isNumeric) {
                setYear(value);
              } else {
                setYear('');
              }
            }}
            placeholder={year}
            className='block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-black bg-white px-5 py-2.5 text-gray-700 focus:border-black focus:outline-none focus:ring focus:ring-red-300 focus:ring-opacity-40 dark:border-black dark:bg-darkgray dark:text-gray-300 dark:focus:border-red-300'
          />
        </div>
        <div className='w-2/3'>
          <label className='block mt-3 text-lg text-black'>전화번호</label>
          <input
            type='text'
            value={phoneNumber}
            onChange={(event) => {
              const value = event.target.value;
              console.log(value);
              // 입력값이 숫자로만 구성되어 있는지 확인하는 정규식
              const isNumeric = /^[0-9]*$/.test(value);
              const isValidStart =
                value.length > 3 ||
                (value.length === 1 && value[0] === '0') ||
                (value.length === 2 && value[1] === '1') ||
                (value.length === 3 && value[2] === '0');

              // 숫자로만 구성되었고, 길이가 11자 이하인 경우에만 값을 설정
              if (isValidStart && isNumeric && value.length <= 11) {
                setPhoneNumber(value);
              } else {
                // 조건을 만족하지 않는 경우, 마지막 입력값을 지움
                setPhoneNumber(value.slice(0, -1));
              }
            }}
            placeholder='01012345678'
            className='block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-black bg-white px-5 py-2.5 text-gray-700 focus:border-black focus:outline-none focus:ring focus:ring-red-300 focus:ring-opacity-40 dark:border-black dark:bg-darkgray dark:text-gray-300 dark:focus:border-red-300'
          />
        </div>
        <div className='w-2/3'>
          <label className='block mt-3 text-lg text-black'>주소</label>
          <input
            type='text'
            value={roadAddress}
            onChange={(event) => {
              const updateRoadAddress = event.target.value;
              setRoadAddress(updateRoadAddress);
              // roadAddress 변경 시, 새로운 address 값을 설정합니다.
              setAddress(updateRoadAddress + ',' + detailAddress);
            }}
            placeholder='도로명 주소'
            className='block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-black bg-white px-5 py-2.5 text-gray-700 focus:border-black focus:outline-none focus:ring focus:ring-red-300 focus:ring-opacity-40 dark:border-black dark:bg-darkgray dark:text-gray-300 dark:focus:border-red-300'
          />
          <input
            type='text'
            value={detailAddress}
            onChange={(event) => {
              const updateDetailAddress = event.target.value;
              setDetailAddress(updateDetailAddress);
              // detailAddress 변경 시, 새로운 address 값을 설정합니다.
              setAddress(roadAddress + ',' + updateDetailAddress);
            }}
            placeholder='상세주소'
            className='block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-black bg-white px-5 py-2.5 text-gray-700 focus:border-black focus:outline-none focus:ring focus:ring-red-300 focus:ring-opacity-40 dark:border-black dark:bg-darkgray dark:text-gray-300 dark:focus:border-red-300'
          />
        </div>
        <button className='w-2/3 mt-10 px-5 py-2.5 rounded-lg bg-red-300' onClick={handleSubmit}>
          {location.pathname === '/signup' ? '회원가입 완료' : '수정하기'}
        </button>
      </div>
    </>
  );
};

export default UserInput;
