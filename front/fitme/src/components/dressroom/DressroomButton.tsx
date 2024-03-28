import React, { useState, useRef } from 'react';
import { Modal, Button } from 'flowbite-react';
import DressroomAvatarList from './DressroomAvatarList';
// import { CiCirclePlus } from 'react-icons/ci';

export default function DressroomButton() {
  // const [openSaveModal, setOpenSaveModal] = useState<boolean>(false);
  const [openChangeModal, setOpenChangeModal] = useState<boolean>(false);
  // const [selectedTab, setSelectedTab] = useState<'sample' | 'mine'>('sample');
  const inputRef = useRef<HTMLInputElement>(null);

  const handleInput = () => {
    inputRef.current?.click();
  };

  return (
    <>
      <div className='flex justify-center'>
        {/* <Button className='w-[40%] mx-1' color='gray' onClick={() => setOpenSaveModal(true)}>
          <span className='text-xs'>피팅저장</span>
        </Button> */}
        <Button className='w-[40%] mx-1' color='gray' onClick={() => setOpenChangeModal(true)}>
          <span className='text-xs'>사진변경</span>
        </Button>
      </div>
      {/* <Modal show={openSaveModal} popup onClose={() => setOpenSaveModal(false)}>
        <Modal.Header>피팅 저장</Modal.Header>
        <Modal.Body>
          <div className='w-[50%] mx-auto'>
            <div className='w-full pb-[140%] bg-gray-100 mx-auto m-2 mb-4'></div>
            <div className='w-full mx-auto'>저장할 이름</div>
            <input type='text' className='border w-full mb-2' />
            <Button className='w-full h-8' color='success'>
              저장
            </Button>
          </div>
        </Modal.Body>
      </Modal> */}

      <Modal show={openChangeModal} popup onClose={() => setOpenChangeModal(false)}>
        <Modal.Header>사진 변경</Modal.Header>
        <Modal.Body>
          <div className='p-1'>
            <div className='flex flex-col text-center border-gray-200 shadow-sm sticky top-0 z-50'>
              <div className='flex'>
                <div
                  className='w-[100%] p-4 border-r border-gray-200 cursor-pointer bg-white'
                  // onClick={() => setSelectedTab('sample')}
                >
                  샘플사진
                </div>
                {/* <div
                  className={`w-[50%] p-4  cursor-pointer ${
                    selectedTab === 'mine' ? 'bg-gray-300' : 'bg-white'
                  }`}
                  onClick={() => setSelectedTab('mine')}
                >
                  내사진
                </div> */}
              </div>
              {/* {selectedTab === 'mine' && (
                <div
                  className='w-full h-10 text-4xl border rounded-lg flex justify-center cursor-pointer sticky bg-white'
                  onClick={handleInput}
                >
                  <CiCirclePlus />
                  <input type='file' className='hidden' ref={inputRef} />
                </div>
              )} */}
            </div>
            <DressroomAvatarList></DressroomAvatarList>
          </div>
        </Modal.Body>
      </Modal>
    </>
  );
}
