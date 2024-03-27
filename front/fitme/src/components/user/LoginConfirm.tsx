import React from 'react';

interface LoginConfirmProps {
  onConfirm: () => void;
  onClose: () => void;
}

const LoginConfirmModal: React.FC<LoginConfirmProps> = ({ onConfirm, onClose }) => {
  return (
    <div className='flex flex-col justify-center items-center h-full gap-8'>
      <div className='text-3xl'>
        <div>로그인이 필요한 기능입니다.</div>
        <div>로그인 페이지로 이동하시겠습니까?</div>
      </div>
      <div className='w-2/3 h-10 flex justify-around'>
        <button onClick={onConfirm} className='bg-black text-white w-1/3 rounded-lg'>
          로그인하기
        </button>
        <button onClick={onClose} className='bg-black text-white w-1/3 rounded-lg'>
          돌아가기
        </button>
      </div>
    </div>
  );
};

export default LoginConfirmModal;
