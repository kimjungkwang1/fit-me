import React, { useEffect, useState } from 'react';

const year = new Date().getFullYear().toString();

type UserInputProps = {
  onSubmit: (formData: FormData) => void;
};

const UserInput: React.FC<UserInputProps> = ({ onSubmit }) => {
  const [nickname, setNickname] = useState('');
  const [gender, setGender] = useState(false);
  const [profileUrl, setProfileUrl] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [birthYear, setBirthYear] = useState('');
  const [address, setAddress] = useState('');
  const [detailAddress, setDetailAddress] = useState('');

  const handleSubmit = () => {
    const formData = new FormData();
    formData.append('nickname', nickname);
    formData.append('gender', gender.toString());
    formData.append('profileUrl', profileUrl);
    formData.append('phoneNumber', phoneNumber);
    formData.append('birthYear', birthYear.toString());
    formData.append('address', address);
    formData.append('detailAddress', detailAddress);

    onSubmit(formData);
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
            className='block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-black bg-white px-5 py-2.5 text-gray-700 focus:border-black focus:outline-none focus:ring focus:ring-red-300 focus:ring-opacity-40 dark:border-black dark:bg-gray-900 dark:text-gray-300 dark:focus:border-red-300'
          />
        </div>
        <div className='w-2/3'>
          <label className='block mt-3 text-lg text-black'>성별</label>
          <div className='mt-2 inline-flex w-full rounded-md shadow-sm' role='group'>
            <button
              type='button'
              onClick={() => setGender(false)}
              className='inline-flex px-5 py-2.5 items-center w-1/2 bg-white px-4 py-2 text-sm font-medium text-gray-900 bg-transparent border border-black rounded-s-lg hover:bg-gray-600 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-gray-900 focus:text-white dark:border-white dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:bg-gray-700'
            >
              <svg
                fill='#888888'
                width='20px'
                height='20px'
                viewBox='0 0 1024 1024'
                xmlns='http://www.w3.org/2000/svg'
                transform='rotate(0)'
              >
                <g id='SVGRepo_bgCarrier' stroke-width='0'></g>
                <g id='SVGRepo_tracerCarrier' stroke-linecap='round' stroke-linejoin='round'></g>
                <g id='SVGRepo_iconCarrier'>
                  <path d='M1023.3 22.656c.144-6.48-1.378-12.29-5.586-16.433a22.058 22.058 0 0 0-16.4-6.527l-11.696.273c-.223 0-.383.08-.64.112L695.476-.944c-12.928.289-23.616 10.993-23.92 23.92l-.032 16.432c1.967 15.248 13.952 24.16 26.88 23.872l215.215.432-256.144 254.592c-69.488-58.24-159.008-93.36-256.768-93.36-220.928 0-400 179.071-400 400 0 220.911 179.072 400 400 400 220.912 0 400-179.089 400-400 0-100.113-36.864-191.569-97.664-261.713L959.938 107.92l-.944 219.152c-.304 12.928 9.952 24.176 22.897 23.888l16.416-.032c12.96-.304 23.647-8 23.92-20.928l.671-295.008c0-.24-.88-.4-.88-.624zM737.229 624.943c0 185.856-150.672 336.528-336.544 336.528-185.856 0-336.528-150.672-336.528-336.528 0-185.856 150.672-336.528 336.528-336.528 185.872-.016 336.544 150.656 336.544 336.528z'></path>
                </g>
              </svg>
              <div className='mx-auto text-lg text-gray-400'>남자</div>
            </button>
            <button
              type='button'
              onClick={() => setGender(true)}
              className='inline-flex px-5 py-2.5 items-center w-1/2 bg-white px-4 py-2 text-sm font-medium text-gray-900 bg-transparent border border-black rounded-e-lg hover:bg-gray-600 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-gray-900 focus:text-white dark:border-white dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:bg-gray-700'
            >
              <svg
                height='20px'
                width='21px'
                version='1.1'
                id='Capa_1'
                xmlns='http://www.w3.org/2000/svg'
                viewBox='0 0 167.579 167.579'
                fill='#888888'
                transform='rotate(180)'
                stroke='#888888'
                stroke-width='3.3515800000000007'
              >
                <g id='SVGRepo_bgCarrier' stroke-width='0'></g>
                <g id='SVGRepo_tracerCarrier' stroke-linecap='round' stroke-linejoin='round'></g>
                <g id='SVGRepo_iconCarrier'>
                  <path d='M147.917,25.461l18.46-18.467c1.603-1.6,1.603-4.194,0-5.794s-4.187-1.6-5.791,0l-18.46,18.467 L123.656,1.2c-1.603-1.6-4.191-1.6-5.794,0s-1.603,4.194,0,5.794l18.456,18.467l-17.909,17.916 C90.494,18.493,47.576,19.362,20.81,46.129c-27.747,27.743-27.747,72.894,0,100.637c27.747,27.75,72.89,27.75,100.637,0 c26.766-26.763,27.632-69.68,2.756-97.592l17.923-17.916l18.46,18.467c1.603,1.6,4.187,1.6,5.791,0 c1.603-1.603,1.603-4.191,0-5.794L147.917,25.461z M115.696,141.011c-24.569,24.565-64.559,24.565-89.131,0 c-24.576-24.572-24.576-64.562,0-89.138s64.562-24.576,89.131,0C140.276,76.452,140.276,116.439,115.696,141.011z'></path>{' '}
                </g>
              </svg>
              <div className='mx-auto text-lg text-gray-400'>여자</div>
            </button>
          </div>
        </div>

        <div className='w-2/3'>
          <label className='block mt-3 text-lg text-black'>출생연도</label>
          <input
            type='text'
            value={birthYear}
            onChange={(event) => setBirthYear(event.target.value)}
            placeholder={year.toString()}
            className='block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-black bg-white px-5 py-2.5 text-gray-700 focus:border-black focus:outline-none focus:ring focus:ring-red-300 focus:ring-opacity-40 dark:border-black dark:bg-gray-900 dark:text-gray-300 dark:focus:border-red-300'
          />
        </div>
        <div className='w-2/3'>
          <label className='block mt-3 text-lg text-black'>전화번호</label>
          <input
            type='text'
            value={phoneNumber}
            onChange={(event) => setPhoneNumber(event.target.value)}
            placeholder='010-0000-0000'
            className='block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-black bg-white px-5 py-2.5 text-gray-700 focus:border-black focus:outline-none focus:ring focus:ring-red-300 focus:ring-opacity-40 dark:border-black dark:bg-gray-900 dark:text-gray-300 dark:focus:border-red-300'
          />
        </div>
        <div className='w-2/3'>
          <label className='block mt-3 text-lg text-black'>주소</label>
          <input
            type='text'
            value={address}
            onChange={(event) => setAddress(event.target.value)}
            placeholder='도로명 주소'
            className='block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-black bg-white px-5 py-2.5 text-gray-700 focus:border-black focus:outline-none focus:ring focus:ring-red-300 focus:ring-opacity-40 dark:border-black dark:bg-gray-900 dark:text-gray-300 dark:focus:border-red-300'
          />
          <input
            type='text'
            value={detailAddress}
            onChange={(event) => setDetailAddress(event.target.value)}
            placeholder='상세주소'
            className='block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-black bg-white px-5 py-2.5 text-gray-700 focus:border-black focus:outline-none focus:ring focus:ring-red-300 focus:ring-opacity-40 dark:border-black dark:bg-gray-900 dark:text-gray-300 dark:focus:border-red-300'
          />
        </div>
        <button className='w-2/3 mt-10 px-5 py-2.5 rounded-lg bg-red-300' onClick={handleSubmit}>
          회원가입 완료
        </button>
      </div>
    </>
  );
};

export default UserInput;
